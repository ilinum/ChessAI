package me.ilinskiy.ChessAI

import me.ilinskiy.chess.chessBoard.{BoardWrapper, ChessBoardUtil, PieceColor}
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
    import scala.collection.JavaConversions._

    val evaluatedPositions: Seq[(Move, Int)] = availableMoves.map { move: Move =>
      (move, PositionEvaluator.evaluatePositionAfterMove(board, move))
    }

    val maxScore = evaluatedPositions.unzip._2.max
    val allGoodMoves = evaluatedPositions.collect {
      case (m, `maxScore`) => m
    }
    assert(allGoodMoves.nonEmpty)

    if (depth > maxRecursionDepth) allGoodMoves
    else {
      val ourColor = board.getPieceAt(allGoodMoves.head.getInitialPosition).getColor
      val opponentColor = ChessBoardUtil.inverse(ourColor)

      val goodAccordingOpponentMove = allGoodMoves.map { (m: Move) =>
        val opponentMoves: Seq[Move] = AIUtil.makeMoveAndEvaluate(board, m, (board: BoardWrapper) => {
          pickGoodMoves(GameUtil.getAvailableMoves(opponentColor, board.getInner), board, depth + 1, color)
        })

        val sum = opponentMoves.map(PositionEvaluator.evaluatePositionAfterMove(board, _) * -1).sum
        (m, sum)
      }

      val max = goodAccordingOpponentMove.unzip._2.max

      goodAccordingOpponentMove.collect {
        case (m , `max`) => m
      }
    }
  }

  def getMove(board: BoardWrapper, color: PieceColor): Move = {
    import scala.collection.JavaConversions._
    val availableMoves: Seq[Move] = GameUtil.getAvailableMoves(color, board.getInner)

    Time.moveNeededBy match {
      case Some(moveNeededByMillis) =>
        val future: Future[Move] = Future {
          AIUtil.randomElement(pickGoodMoves(availableMoves, board, 0, color))
        }
        val milliSecondsToDecide = moveNeededByMillis - System.currentTimeMillis()
        try {
          Await.result(future, milliSecondsToDecide millisecond)
        } catch {
          case e: Exception =>

            AIUtil.randomElement(availableMoves) //probably timeout but could've been something else
        }
      case _ => AIUtil.randomElement(pickGoodMoves(availableMoves, board, 0, color))
    }
  }
}
