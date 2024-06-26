package com.retirementcalc.equity_data

case class InflationData(monthId: String, index: Double) extends EquityInflationData

object InflationData {
  def fromResource(path: String): Vector[InflationData] = {
    scala.io.Source.fromResource(path).getLines().drop(1).map {
      row =>
        val fields = row.split("\t")
        val month = fields(0)
        val cpi = fields(1)

        InflationData(month, cpi.toDouble)
    }
  }.toVector
}