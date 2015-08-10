package me.ilinskiy.ChessAI

import me.ilinskiy.chess.chessBoard.{BoardWrapper, PieceColor}
import me.ilinskiy.chess.game.{GameUtil, Move}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

/**
 * Author: Svyatoslav Ilinskiy
 * Date: 8/9/2015.
 */
object MoveMaker {
  def getMove(board: BoardWrapper, color: PieceColor): Move = {
    import scala.collection.JavaConversions._

    def pickMove(availableMoves: Seq[Move]): Move = {
      val evaluatedPositions: Seq[(Move, Int)] = availableMoves.map { move: Move =>
        (move, PositionEvaluator.evaluatePositionAfterMove(board, move))
      }

      val maxScore = evaluatedPositions.maxBy(_._2)._2
      val allGoodMoves = evaluatedPositions.collect {
        case (m, `maxScore`) => m
      }
      assert(allGoodMoves.nonEmpty)
      while (true) {}
      AIUtil.randomElement(allGoodMoves)
    }

    val availableMoves: Seq[Move] = GameUtil.getAvailableMoves(color, board.getInner)

    Time.moveNeededBy match {
      case Some(moveNeededByMillis) =>
        val future: Future[Move] = Future {
          pickMove(availableMoves)
        }
        val milliSecondsToDecide = moveNeededByMillis - System.currentTimeMillis()
        try {
          Await.result(future, milliSecondsToDecide millisecond)
        } catch {
          case e: Exception => AIUtil.randomElement(availableMoves) //probably timeout but could've been something else
        }
      case _ => pickMove(availableMoves)
    }
    /*if (GameRunner.TIMEOUT_IN_SECONDS > 0) {
      val future: Future[Move] = Future {
        pickMove(availableMoves)
      }
      val secondsToDecide = Time.moveNeededBy
      try {
        Await.result(future, secondsToDecide seconds)
      } catch {
        case e: Exception => AIUtil.randomElement(availableMoves) //probably timeout but could've been something else
      }
    } else {
      pickMove(availableMoves)
    }*/
  }
}
