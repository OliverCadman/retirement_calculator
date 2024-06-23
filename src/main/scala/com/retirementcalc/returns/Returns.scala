package com.retirementcalc.returns

import com.retirementcalc.equity_data.{EquityData, InflationData}

import scala.annotation.tailrec

sealed trait Returns
case class FixedReturn(annualInterest: Double) extends Returns
case class VariableReturn(monthId: String, monthlyInterest: Double)
case class VariableReturns(returns: Vector[VariableReturn]) extends Returns {
  def fromUntil(from: String, until: String): VariableReturns = {
    VariableReturns(returns.dropWhile(_.monthId != from).takeWhile(_.monthId != until))
  }
}

case class OffsetReturns(orig: Returns, month: Int) extends Returns

object Returns {

  @tailrec
  def monthlyRate(returns: Returns, month: Int): Double = returns match {
    case FixedReturn(rn) => rn / 12
    case VariableReturns(rn) => rn(month % rn.length).monthlyInterest
    case OffsetReturns(rn, offset) => {
      monthlyRate(rn, month + offset)
    }
  }

  def fromEquityAndInflationData(equities: Vector[EquityData],
                                 inflations: Vector[InflationData]): VariableReturns = {
    VariableReturns(equities.zip(inflations).sliding(2).collect {
      case (prevEquity, prevInflation) +: (currEquity, currInflation) +: Vector() => {
        val realInflation = currInflation.index / prevInflation.index
        val realReturn = (currEquity.sp500 + currEquity.dividend / 12) / prevEquity.sp500 - realInflation
        VariableReturn(
          currEquity.month,
          realReturn
        )
      }
    }.toVector)
  }
}

