package com.retcalc

import com.retirementcalc.equity_data.EquityData
import org.scalactic.{Equality, TolerantNumerics, TypeCheckedTripleEquals}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class EquityDataSpec extends AnyWordSpec with Matchers {

  "EquityData.getResource" should {
    "Retrieve the correct collection of EquityData" in {
      val path = "sp500_2017.tsv"
      val data = EquityData.fromResource(path)
      data should === (
        Vector(
          EquityData("2016.09",	2157.69,	45.03),
          EquityData("2016.10",	2143.02,	45.25),
          EquityData("2016.11",	2164.99,	45.48),
          EquityData("2016.12",	2246.63,	45.7),
          EquityData("2017.01",	2275.12,	45.93),
          EquityData("2017.02",	2329.91,	46.15),
          EquityData("2017.03",	2366.82,	46.38),
          EquityData("2017.04",	2359.31,	46.66),
          EquityData("2017.05",	2395.35,	46.94),
          EquityData("2017.06",	2433.99,	47.22),
          EquityData("2017.07",	2454.10,	47.54),
          EquityData("2017.08",	2456.22,	47.85),
          EquityData("2017.09",	2492.84,	48.17)
        )
      )
    }
  }
}
