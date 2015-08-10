package me.ilinskiy.ChessAI

import me.ilinskiy.chess.chessBoard.{BoardWrapper, PieceColor}
import me.ilinskiy.chess.game.{GameRunner, GameUtil, Move}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps
import scala.util.Random
/**
 * Author: Svyatoslav Ilinskiy
 * Date: 8/9/2015.
 */
object MoveMaker {
  def getMove(board: BoardWrapper, color: PieceColor, secondsToDecide: Int): Move = {
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
      allGoodMoves(new Random().nextInt(allGoodMoves.size))
    }

    val availableMoves: Seq[Move] = GameUtil.getAvailableMoves(color, board.getInner)

    if (GameRunner.TIMEOUT_IN_SECONDS > 0) {
      val future: Future[Move] = Future {
        pickMove(availableMoves)
      }
      try {
        Await.result(future, secondsToDecide seconds)
      } catch {
        case e: Exception => AIUtil.randomElement(availableMoves) //it probably timeout but could've been something else
      }
    } else {
      pickMove(availableMoves)
    }
  }
}
