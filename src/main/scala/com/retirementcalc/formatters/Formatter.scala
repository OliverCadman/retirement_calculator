package com.retirementcalc.formatters

object Formatter {

  def curried(c: String)(f: Double): Double = c.format(f).toDouble
}
