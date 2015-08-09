package me.ilinskiy.ChessAI

import me.ilinskiy.chess.chessBoard.{Board, ChessBoardUtil, PieceColor, PieceType}
import me.ilinskiy.chess.game.{Move, Player}

/**
 * Author: Svyatoslav Ilinskiy
 * Date: 8/8/15.
 */
class AI(myColor: PieceColor) extends Player {
  override def copy(): Player = AI(myColor)

  override def getPlayerColor: PieceColor = myColor

  override def getMove(board: Board): Move = {
    MoveMaker.getMove(ChessBoardUtil.getBoardWrapperCopy(board), myColor)
  }

  override def getPieceTypeForPromotedPawn: PieceType = PieceType.Queen
}

object AI {
  def apply(pieceColor: PieceColor) = new AI(pieceColor)
}
