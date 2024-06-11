package com.retcalc

import com.retirementcalc.returns.{FixedReturn, Returns, VariableReturn, VariableReturns, OffsetReturns}
import org.scalactic.{Equality, TolerantNumerics, TypeCheckedTripleEquals}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ReturnSpec extends AnyWordSpec with Matchers with TypeCheckedTripleEquals{

  implicit val doubleEquality: Equality[Double] =
    TolerantNumerics.tolerantDoubleEquality(0.0001)

  "Returns.monthlyRate" should {
    "Return a fixed rate for a Fixed Return" in {
      Returns.monthlyRate(FixedReturn(0.04), 0) should ===(0.04 / 12)
      Returns.monthlyRate(FixedReturn(0.04), 1) should ===(0.04 / 12)
    }

    val variableReturns = VariableReturns(
      Vector(
        VariableReturn("2000.01", 0.1),
        VariableReturn("2000.02", 0.2)
      )
    )

    "Return the nth rate for variable return" in {
      Returns.monthlyRate(variableReturns, 0) should === (0.1)
      Returns.monthlyRate(variableReturns, 1) should === (0.2)
    }

    "Rollover from the first rate if n > length" in {
      Returns.monthlyRate(variableReturns, 2) should === (0.1)
      Returns.monthlyRate(variableReturns, 3) should === (0.2)
      Returns.monthlyRate(variableReturns, 4) should === (0.1)
    }

    "Return the n+offseth'th rate for OffsetReturn" in {
      val returns = OffsetReturns(variableReturns, 1)
      Returns.monthlyRate(returns, 0) should === (0.2)
    }
  }
}
