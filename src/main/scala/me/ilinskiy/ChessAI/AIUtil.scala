package me.ilinskiy.ChessAI

import me.ilinskiy.chess.chessBoard.{BoardWrapper, Coordinates}
import me.ilinskiy.chess.game.Move

import scala.language.implicitConversions
import scala.util.Random

/**
*  Author: Svyatoslav Ilinskiy
*  Date: 8/10/2015.
*/
object AIUtil {
  def randomElement[T](list: Seq[T]): T = list(new Random().nextInt(list.size))

  def makeMoveAndEvaluate[T](board: BoardWrapper, move: Move, boardOp: (BoardWrapper) => T): T = {
    board.setPieceAccordingToMove(move)
    val res = boardOp(board)
    board.setPieceAccordingToMove(move.inverse())
    res
  }

  implicit class InvertibleMove(underlying: Move) {
    def inverse(): Move = new Move(underlying.getNewPosition, underlying.getInitialPosition)
  }

  implicit class Time(underlying: Int) {
    def secondsToMillis: Long = underlying * 1000L
  }

  implicit def tuple2Coordinates(tuple: (Int, Int)): Coordinates = new Coordinates(tuple._1, tuple._2)
}
