package me.ilinskiy.ChessAI

import junit.framework.TestCase
import me.ilinskiy.chess.chessboard._
import me.ilinskiy.chess.game.moves.{RegularMove, Move}
import me.ilinskiy.ChessAI.AIUtil.tuple2Coordinates
import org.junit.Assert


/**
  * Created by ilinum on 08.11.15.
  */
class AITest extends TestCase {
  def testEatPawn(): Unit = {
    val board = new BoardWrapper()
    board.setPieceAt((0, 3), Piece.createPiece(PieceColor.White, PieceType.Pawn))
    board.setPieceAt((1, 2), Piece.createPiece(PieceColor.Black, PieceType.Pawn))
    doTest(board.getInner, new RegularMove((0, 3), (1, 2)))
  }

  def doTest(board: Board, expected: Move): Unit = {
    val ai = new AI(board.whoseTurnIsIt())
    Assert.assertEquals(expected, ai.getMove(board))
  }
}
