package me.ilinskiy.ChessAI

import me.ilinskiy.ChessAI.AIUtil.Time
import me.ilinskiy.chess.chessboard._
import me.ilinskiy.chess.game.moves.Move
import me.ilinskiy.chess.game.{GameRunner, GameUtil, Player}

/**
 * Author: Svyatoslav Ilinskiy
 * Date: 8/8/15.
 */
class AI(myColor: PieceColor) extends Player {
  override def getPlayerColor: PieceColor = myColor

  override def getMove(board: Board): Move = {
    assert(!GameUtil.getAvailableMoves(myColor, board).isEmpty)
    AI.movesMade += 1
    val timeout: Long = GameRunner.TIMEOUT_IN_SECONDS.secondsToMillis
    val moveNeededBy =
      if (timeout > 0) Some(System.currentTimeMillis() + timeout) //yay for magic numbers!
      else None
    val boardWrapper: BoardWrapper = BoardWrapper.getCopy(board)
    MoveMaker.getMove(boardWrapper, myColor, moveNeededBy)
  }

  override def getPieceTypeForPromotedPawn: PieceType = PieceType.Queen
}

object AI {
  var movesMade: Int = 0
  val averageGameLength = 40
}
