package com.retirementcalc.equity_data

import scala.io.Source

case class InflationData(monthId: String, index: Double)

object InflationData {
  def fromResource(path: String): Vector[InflationData] = {
    Source.fromResource(path).getLines().drop(1).map {
      line => {
        val row = line.split("\t")
        val monthId: String = row(0)
        val index: Double = row(1).toDouble

        InflationData(monthId, index)
      }
    }
  }.toVector
}
