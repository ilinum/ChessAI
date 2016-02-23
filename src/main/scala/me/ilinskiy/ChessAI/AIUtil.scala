package me.ilinskiy.ChessAI

import me.ilinskiy.chess.chessboard._
import me.ilinskiy.chess.game.moves.Move

import scala.language.implicitConversions
import scala.util.Random

/**
*  Author: Svyatoslav Ilinskiy
*  Date: 8/10/2015.
*/
object AIUtil {
  def randomElement[T](list: Seq[T]): T = list(new Random().nextInt(list.size))

  implicit class Time(underlying: Int) {
    def secondsToMillis: Long = underlying * 1000L
  }

  implicit def tuple2Coordinates(tuple: (Int, Int)): Coordinates = new Coordinates(tuple._1, tuple._2)
}
