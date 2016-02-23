name := "ChessAI"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "me.ilinskiy.Chess" % "me.ilinskiy" % "1.2" from "https://github.com/ilinum/Chess/releases/download/1.2/Chess.jar"

libraryDependencies += "junit" % "junit" % "4.11" % "test"

scalacOptions += "-Xexperimental"
