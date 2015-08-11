package me.ilinskiy.ChessAI

import me.ilinskiy.chess.chessBoard.BoardWrapper
import me.ilinskiy.chess.game.Move

import scala.util.Random

/**
*  Author: Svyatoslav Ilinskiy
*  Date: 8/10/2015.
*/
object AIUtil {
  def randomElement[T](list: Seq[T]): T = list(new Random().nextInt(list.size))

  def makeMoveAndEvaluate[T](board: BoardWrapper, move: Move, boardOp: (BoardWrapper) => T): T = {
    board.setPieceAccordingToMove(move)
    val res = boardOp(board)
    board.setPieceAccordingToMove(move.inverse())
    res
  }
}
