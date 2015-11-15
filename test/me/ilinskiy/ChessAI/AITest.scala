package me.ilinskiy.ChessAI

import junit.framework.TestCase
import me.ilinskiy.ChessAI.AIUtil.tuple2Coordinates
import me.ilinskiy.chess.chessBoard._
import me.ilinskiy.chess.game.Move
import org.junit.Assert

/**
  * Created by ilinum on 08.11.15.
  */
class AITest extends TestCase {
  def testEatPawn(): Unit = {
    val board = new BoardWrapper()
    board.setPieceAt((0, 3), Piece.createPiece(PieceColor.White, PieceType.Pawn))
    board.setPieceAt((1, 2), Piece.createPiece(PieceColor.Black, PieceType.Pawn))
    doTest(board.getInner, new Move((0, 3), (1, 2)))
  }

  def doTest(board: Board, expected: Move): Unit = {
    val ai = new AI(board.whoseTurnIsIt())
    Assert.assertEquals(expected, ai.getMove(board))
  }
}
