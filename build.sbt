name := "ChessAI"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "me.ilinskiy.Chess" % "me.ilinskiy" % "1.0" from "https://github.com/ilinum/Chess/raw/master/Chess-1.0.jar" changing()

scalacOptions += "-Xexperimental"

val collUtils = "com.twitter" %% "util-collection" % "6.25.0"
