package me.ilinskiy.ChessAI

import me.ilinskiy.chess.chessBoard.{BoardWrapper, PieceColor}
import me.ilinskiy.chess.game.{GameRunner, GameUtil, Move}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

/**
 * Author: Svyatoslav Ilinskiy
 * Date: 8/9/2015.
 */
object MoveMaker {
  val maxRecursionDepth =
    if (GameRunner.TIMEOUT_IN_SECONDS < 1) Int.MaxValue
    else GameRunner.TIMEOUT_IN_SECONDS

  def pickGoodMoves(availableMoves: Seq[Move], board: BoardWrapper, depth: Int, color: PieceColor): Seq[Move] = {

    val evaluatedPositions: Seq[(Move, Int)] = availableMoves.map { move: Move =>
      (move, PositionEvaluator.evaluatePositionAfterMove(board, move))
    }

    val maxScore = evaluatedPositions.unzip._2.max
    evaluatedPositions.collect {
      case (m, `maxScore`) => m
    }
  }

  def getMove(board: BoardWrapper, color: PieceColor, moveNeededBy: Option[Long]): Move = {
    import scala.collection.JavaConversions._
    val availableMoves: Seq[Move] = GameUtil.getAvailableMoves(color, board.getInner)

    moveNeededBy match {
      case Some(moveNeededByMillis) =>
        val future: Future[Move] = Future {
          AIUtil.randomElement(pickGoodMoves(availableMoves, board, 0, color))
        }
        val milliSecondsToDecide = moveNeededByMillis - System.currentTimeMillis()
        try {
          Await.result(future, milliSecondsToDecide.millisecond)
        } catch {
          case e: Exception =>
            AIUtil.randomElement(availableMoves) //probably timeout but could've been something else
        }
      case _ => AIUtil.randomElement(pickGoodMoves(availableMoves, board, 0, color))
    }
  }
}
