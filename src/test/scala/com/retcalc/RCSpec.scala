package com.retcalc

import com.retirementcalc.calculator.RetCalc
import com.retirementcalc.calculator.RetCalcParams
import com.retirementcalc.returns.{FixedReturn, VariableReturn, VariableReturns}
import org.scalactic.{Equality, TolerantNumerics, TypeCheckedTripleEquals}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class RCSpec extends AnyWordSpec with Matchers with TypeCheckedTripleEquals  {

  implicit val doubleEquality: Equality[Double] =
    TolerantNumerics.tolerantDoubleEquality(0.0001)


  "RetCalc.futureCapital" should {
    "Calculate the amount of savings I have in n months" in {
      val actual = RetCalc.futureCapital(
        returns=FixedReturn(0.04),
        income=4000, expenses=3000,
        nMonths=25 * 12, initialCapital=10000
      )
      val expected = 541267.1990
      actual should === (expected)
    }
    "Calculate the amount of savings after living off pension for n months" in {
      val actual = RetCalc.futureCapital(
        returns = FixedReturn(0.04),
        income = 0,
        expenses = 2000,
        nMonths = 40 * 12,
        initialCapital = 541267.1990
      )
      val expected = 309867.5318
      actual should ===(expected)
    }
  }

  val params: RetCalcParams = RetCalcParams(
    nbMonthsInRetirement = 40 * 12,
    income = 3000,
    expenses = 2000,
    initialCapital = 10000
  )

  "RetCalc.simulatePlan" should {
    "Calculate the capital at retirement and after death" in {
      val (capitalAfterRetirement, capitalAtDeath) = RetCalc.simulatePlan(
        returns = FixedReturn(0.04),
        nMonthsSaving = 25 * 12,
        params
      )
      capitalAfterRetirement should === (541267.1990)
      capitalAtDeath should === (309867.5316)
    }

    "use different returns for capitalisation and drawdown" in {
      val nbMonthsSaving = 25 * 12
      val returns = VariableReturns(
        Vector.tabulate(nbMonthsSaving + params.nbMonthsInRetirement)(x => {
          if (x < nbMonthsSaving) VariableReturn(x.toString, 0.04 / 12)
          else VariableReturn(x.toString, 0.03 / 12)
        })
      )

      val (capitalAfterRetirement, capitalAfterDeath) = RetCalc.simulatePlan(
        returns = returns,
        nMonthsSaving = nbMonthsSaving,
        params
      )

      capitalAfterRetirement should === (541267.1990)
      capitalAfterDeath should === (-57737.7227)
    }

    "Calculate the age I will need to retire to accrue the desired capital with fixed return" in {
      val actual = RetCalc.nbMonthsOfSaving(
        returns=FixedReturn(0.04),
        params
      )

      val expected = (23 * 12 + 1).toDouble
      actual should === (Some(expected))
    }
  }

  "RetCalc.nbMonthsSaving" should {

    val tailRecParams = RetCalcParams(
      nbMonthsInRetirement = 40 * 12,
      income = 3000,
      expenses = 2999,
      initialCapital = 0
    )
    "Not crash if the resulting number of months is very high" in {
      val actual = RetCalc.nbMonthsOfSaving(
        returns = FixedReturn(0.01),
        tailRecParams
      )

      val expected = 8280
      actual should === (Some(expected.toDouble))
    }

    "Not loop forever if bad arguments provided" in {
      val badParams = RetCalcParams(
        nbMonthsInRetirement = 40 * 12,
        income = 3000,
        expenses = 4000,
        initialCapital = 10000
      )
      val actual = RetCalc.nbMonthsOfSaving(
        returns = FixedReturn(0.04),
        badParams
      )

      actual should === (None)
    }
  }

  "VariableReturns.fromUntil" should {
    "Return a sub-set of variable returns between two given 'to' and 'from' months" in {
      val variableReturns = VariableReturns(Vector.tabulate(12)(
        x => {
          val d = x + 1.toDouble
          VariableReturn(f"2017.$d%02.0f", d)
        }
      ))

      variableReturns.fromUntil("2017.07", "2017.09") should === (
        VariableReturns(
          Vector(
            VariableReturn("2017.07", 7.0),
            VariableReturn("2017.08", 8.0)
          )
        )
      )

      variableReturns.fromUntil("2017.10", "2018.01") should === (
        VariableReturns(
          Vector(
            VariableReturn("2017.10", 10.0),
            VariableReturn("2017.11", 11.0),
            VariableReturn("2017.12", 12.0)
          )
        )
      )
    }
  }
}
