package com.retcalc

import com.retirementcalc.SimulatePlanApp
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class SimulatePlanAppIT extends AnyWordSpec with Matchers with TypeCheckedTripleEquals {

  "SimulatePlanApp.strMain" should {
    "return a string detailing the amount of capital after retirement, and after death" in {
      val actual = SimulatePlanApp.strMain(
        Array("1997.09:2017.09", "3000", "2000", "40", "25", "10000")
      )

      val expected =
        """
          |Capital after retirement: 499923
          |Capital after death: 586435
          |""".stripMargin


      actual should === (expected)
    }
  }

  }
