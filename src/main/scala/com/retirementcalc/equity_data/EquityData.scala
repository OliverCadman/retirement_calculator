package com.retirementcalc.equity_data

import scala.io.Source

case class EquityData(month: String, sp500: Double, dividend: Double)

object EquityData {

  def fromResource(path: String): Vector[EquityData] = {
    Source.fromResource(path).getLines().drop(1).map {
      line => {
        val values = line.split("\t")
        val month = values(0)
        val sp500 = values(1).toDouble
        val dividend = values(2).toDouble

        EquityData(month, sp500, dividend)
      }
    }.toVector
  }
}
