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
    import scala.collection.JavaConversions._

    val evaluatedPositions: Seq[(Move, Int)] = availableMoves.map { move: Move =>
      (move, PositionEvaluator.evaluatePositionAfterMove(board, move))
    }

    val maxScore = evaluatedPositions.maxBy(_._2)._2
    val allGoodMoves = evaluatedPositions.collect {
      case (m, `maxScore`) => m
    }
    assert(allGoodMoves.nonEmpty)

    if (depth > maxRecursionDepth) allGoodMoves
    else {
      val ourColor = board.getPieceAt(allGoodMoves.head.getInitialPosition).getColor
      assert(ourColor != PieceColor.Empty)
      val opponentMoves = allGoodMoves.map { (m: Move) =>
        val opponentMoves: Seq[Move] = AIUtil.makeMoveAndEvaluate(board, m, (board: BoardWrapper) => {
          pickGoodMoves(GameUtil.getAvailableMoves(ourColor, board.getInner), board, depth + 1, color)
        })
        val sum = opponentMoves.map(PositionEvaluator.evaluatePositionAfterMove(board, _)).sum
        if (color != ourColor) (m, sum * -1)
        else (m, sum)
      }
      val maxOpponentMoves = opponentMoves.maxBy(_._2)._2
      opponentMoves.collect {
        case (m, `maxOpponentMoves`) => m
      }
    }
    /*
    for (m: Move <- allGoodMoves) {

      opponentMoves
//      .makeMoveAndEvaluate(board.getInner, m, new BoardOperation[Seq[Move]] {
//        pickGoodMoves(GameUtil.getAvailableMovesForPiece(ourColor), board)
//      })

    }
    allGoodMoves*/
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
          case e: Exception => AIUtil.randomElement(availableMoves) //probably timeout but could've been something else
        }
      case _ => AIUtil.randomElement(pickGoodMoves(availableMoves, board, 0, color))
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
