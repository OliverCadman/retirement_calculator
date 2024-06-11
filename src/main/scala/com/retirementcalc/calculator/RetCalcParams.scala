package com.retirementcalc.calculator

case class RetCalcParams(
                          nbMonthsInRetirement: Int,
                          income: Double,
                          expenses: Double,
                          initialCapital: Double
                        )
