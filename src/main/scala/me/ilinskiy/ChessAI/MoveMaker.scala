package me.ilinskiy.ChessAI

import me.ilinskiy.chess.chessBoard.{BoardWrapper, PieceColor}
import me.ilinskiy.chess.game.{GameUtil, Move}

import scala.util.Random

/**
 * Author: Svyatoslav Ilinskiy
 * Date: 8/9/2015.
 */
object MoveMaker {
  def getMove(board: BoardWrapper, color: PieceColor): Move = {
    import scala.collection.JavaConversions._
    val availableMoves: Seq[Move] = GameUtil.getAvailableMoves(color, board.getInner)
    assert(availableMoves.nonEmpty)
    val evaluatedPositions: Seq[(Move, Int)] = availableMoves.map { move: Move =>
      (move, PositionEvaluator.evaluatePositionAfterMove(board, move))
    }

    val maxScore = evaluatedPositions.maxBy(_._2)._2
    val allGoodMoves = evaluatedPositions.collect {
      case (m, `maxScore`) => m
    }

    allGoodMoves(new Random().nextInt(allGoodMoves.size))
  }
}
