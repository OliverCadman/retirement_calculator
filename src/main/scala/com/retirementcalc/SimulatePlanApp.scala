package com.retirementcalc

import com.retirementcalc.calculator.{RetCalc, RetCalcParams}
import com.retirementcalc.equity_data.{EquityData, InflationData}
import com.retirementcalc.returns.Returns

object SimulatePlanApp extends App {

  def strMain(args: Array[String]): String = {
    val (from +: until +: Nil) = args(0).split(":").toList

    val netIncome = args(1).toDouble
    val expenses = args(2).toDouble
    val nYearsinRetirement = args(3).toInt
    val nYearsSaving = args(4).toInt
    val initialCapital = args(5).toDouble

    val inflations = InflationData.fromResource("cpi.tsv")
    val equities = EquityData.fromResource("sp500.tsv")

    val returns = Returns.fromEquityAndInflationData(equities, inflations)
    val returnWindow = returns.fromUntil(from, until)

    val (capitalAfterRetirement, capitalAfterDeath) = RetCalc.simulatePlan(
      returns = returnWindow,
      params = RetCalcParams(
        nbMonthsInRetirement = nYearsinRetirement * 12,
        income = netIncome,
        expenses = expenses,
        initialCapital = initialCapital
      ),
      nMonthsSaving = nYearsSaving * 12
    )

    s"""
       |Capital after retirement: ${capitalAfterRetirement.round}
       |Capital after death: ${capitalAfterDeath.round}
       |""".stripMargin
  }
}
