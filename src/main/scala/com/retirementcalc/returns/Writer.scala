package com.retirementcalc.returns

import com.retirementcalc.equity_data.EquityInflationData


trait CSVWriter[T] {
  def read(f: () => Vector[T]): Vector[T]
}

class EquityInflationInterface(val path: String) extends CSVWriter[EquityInflationData] {
  def read(f: () => Vector[EquityInflationData]): Vector[EquityInflationData] = f()
}
