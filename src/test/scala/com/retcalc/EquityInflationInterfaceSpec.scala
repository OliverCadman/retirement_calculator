package com.retcalc

import com.retirementcalc.equity_data.{EquityData, InflationData}
import com.retirementcalc.returns.EquityInflationInterface
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class EquityInflationInterfaceSpec extends AnyWordSpec with Matchers with TypeCheckedTripleEquals {
  "EquityInflationInterface.read" should {
    "return a collection of EquityData class instances containing data from associated TSV" in {
      val testInterface = new EquityInflationInterface("sp500_2017.tsv")
      val actual = testInterface.read(() => EquityData.fromResource(testInterface.path))

      val expected = (
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

      actual should === (expected)
    }

    "return a collection of InflationData class instances containing data from associated TSV" in {
      val testInterface = new EquityInflationInterface("cpi_2017.tsv")
      val actual = testInterface.read(() => InflationData.fromResource(testInterface.path))

      val expected = (
        Vector(
          InflationData("2016.09", 241.428),
          InflationData("2016.10", 241.729),
          InflationData("2016.11", 241.353),
          InflationData("2016.12", 241.432),
          InflationData("2017.01", 242.839),
          InflationData("2017.02", 243.603),
          InflationData("2017.03", 243.801),
          InflationData("2017.04", 244.524),
          InflationData("2017.05", 244.733),
          InflationData("2017.06", 244.955),
          InflationData("2017.07", 244.786),
          InflationData("2017.08", 245.519),
          InflationData("2017.09", 246.819)
        )
        )

      actual should === (expected)
    }
  }
}
