package me.ilinskiy.ChessAI

import me.ilinskiy.chess.chessBoard.{BoardWrapper, PieceColor}
import me.ilinskiy.chess.game.{GameUtil, Move}

/**
 * Author: Svyatoslav Ilinskiy
 * Date: 8/9/2015.
 */
object MoveMaker {
  def getMove(board: BoardWrapper, color: PieceColor): Move = {
    import scala.collection.JavaConversions._
    val availableMoves: Seq[Move] = GameUtil.getAvailableMoves(color, board.getInner)
    val evaluatedPositions = availableMoves.map { move: Move =>
      (move, PositionEvaluator.evaluatePositionAfterMove(board, move))
    }
    evaluatedPositions.maxBy {
      case (_, strength) => strength
    } match {
      case (move, _) => move
    }
  }
}
