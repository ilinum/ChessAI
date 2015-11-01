name := "ChessAI"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "me.ilinskiy.Chess" % "me.ilinskiy" % "1.0" from "https://github.com/ilinum/Chess/releases/download/1.0/Chess-1.0.jar" changing()

scalacOptions += "-Xexperimental"
