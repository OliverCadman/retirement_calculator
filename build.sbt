ThisBuild / version := "0.1.0"

ThisBuild / scalaVersion := "2.13.14"

lazy val root = (project in file("."))
  .settings(
    name := "retirement_calculator"
  )

lazy val app = (project in file("retirement_calculator"))
  .settings(
    assembly / mainClass := Some("com.retirementcalc.SimulatePlanApp"),
    assembly / test := (Test / test).value,
    assembly / assemblyJarName := "retirementcalc.jar"
  )

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test"
