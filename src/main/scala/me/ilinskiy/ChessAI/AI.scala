package me.ilinskiy.ChessAI

import me.ilinskiy.chess.annotations.NotNull
import me.ilinskiy.chess.chessBoard.{Board, PieceColor, PieceType}
import me.ilinskiy.chess.game.{GameUtil, Move, Player}

/**
 * Author: Svyatoslav Ilinskiy
 * Date: 8/8/15.
 */
class AI(myColor: PieceColor) extends Player {
  override def copy(): Player = AI(myColor)

  override def getPlayerColor: PieceColor = myColor

  override def getMove(@NotNull board: Board): Move = {
    GameUtil.getAvailableMoves(myColor, board).get(0)
  }

  override def getPieceTypeForPromotedPawn: PieceType = PieceType.Queen
}

object AI {
  def apply(pieceColor: PieceColor) = new AI(pieceColor)
}
