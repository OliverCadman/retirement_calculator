package com.retirementcalc.calculator

import com.retirementcalc.returns._
import scala.annotation.tailrec

object RetCalc {
  def futureCapital(
                     returns: Returns,
                     income: Double,
                     expenses: Double,
                     nMonths: Int,
                     initialCapital: Double
                   ): Double = {
    val amountSaved = income - expenses

    (0 until nMonths).foldLeft(initialCapital)((acc, month) => {
      println(s"Adding $acc for month $month")
      acc * (1 + Returns.monthlyRate(returns, month))+ amountSaved
    }
    )
  }

  def simulatePlan(
                    returns: Returns,
                    nMonthsSaving: Int,
                    params: RetCalcParams
                  ): (Double, Double) = {

    import params._
    val capitalAfterRetirement = futureCapital(
      returns,
      income,
      expenses,
      nMonthsSaving,
      initialCapital
    )

    val capitalAfterDeath = futureCapital(
      returns = OffsetReturns(returns, nMonthsSaving),
      0,
      expenses,
      nbMonthsInRetirement,
      capitalAfterRetirement
    )

    (capitalAfterRetirement, capitalAfterDeath)
  }

  def nbMonthsOfSaving(
                        returns: Returns,
                        params: RetCalcParams
                      ): Option[Double] = {

    import params._

    @tailrec
    def go(nMonths: Int): Double = {
      val (_, capitalAfterDeath) = simulatePlan(
        returns,
        nMonths,
        params
      )

      if (capitalAfterDeath > 0.0) nMonths else go(nMonths + 1)
    }

    if (income < expenses) None else Some(go(0))
  }
}
