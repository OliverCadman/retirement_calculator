package com.retirementcalc.equity_data

case class EquityData(month: String, sp500: Double, dividend: Double) extends EquityInflationData

object EquityData {
  def fromResource(path: String): Vector[EquityData] = {
    scala.io.Source.fromResource(path).getLines().drop(1).map {
      row =>
        val fields = row.split("\t")
        val month = fields(0)
        val sp500 = fields(1)
        val dividends = fields(2)

        EquityData(month, sp500.toDouble, dividends.toDouble)
    }
  }.toVector
}
