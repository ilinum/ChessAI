package me.ilinskiy.ChessAI

import me.ilinskiy.chess.chessBoard.{Board, ChessBoardUtil, PieceColor, PieceType}
import me.ilinskiy.chess.game.{GameRunner, GameUtil, Move, Player}

/**
 * Author: Svyatoslav Ilinskiy
 * Date: 8/8/15.
 */
class AI(myColor: PieceColor) extends Player {
  override def getPlayerColor: PieceColor = myColor

  override def getMove(board: Board): Move = {
    assert(!GameUtil.getAvailableMoves(myColor, board).isEmpty)
    AI.movesMade += 1
    Time.moveNeededBy = Some(System.currentTimeMillis() + (GameRunner.TIMEOUT_IN_SECONDS - 1) * 1000) //yay for magic numbers!
    MoveMaker.getMove(ChessBoardUtil.getBoardWrapperCopy(board), myColor)
  }

  override def getPieceTypeForPromotedPawn: PieceType = PieceType.Queen
}

object AI {
  var movesMade: Int = 0
  val averageGameLength = 40

  def apply(pieceColor: PieceColor) = new AI(pieceColor)
}
