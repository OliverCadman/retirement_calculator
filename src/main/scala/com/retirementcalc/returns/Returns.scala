package com.retirementcalc.returns

import scala.annotation.tailrec

sealed trait Returns
case class FixedReturn(annualInterest: Double) extends Returns
case class VariableReturn(monthId: String, monthlyInterest: Double)
case class VariableReturns(returns: Vector[VariableReturn]) extends Returns {
  def fromUntil(from: String, until: String): Vector[VariableReturn] = {
    returns.dropWhile(_.monthId != from).takeWhile(_.monthId != until)
  }
}

case class OffsetReturns(orig: Returns, month: Int) extends Returns

object Returns {

  @tailrec
  def monthlyRate(returns: Returns, month: Int): Double = returns match {
    case FixedReturn(rn) => rn / 12
    case VariableReturns(rn) => rn(month % rn.length).monthlyInterest
    case OffsetReturns(rn, offset) => {
      println(s"Calling from offset...: Return: R$rn: \n Month: $month \n Offset: $offset")
      monthlyRate(rn, month + offset)
    }
  }
}

