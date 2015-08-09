package me.ilinskiy.ChessAI

import me.ilinskiy.chess.chessBoard.PieceType._
import me.ilinskiy.chess.chessBoard._
import me.ilinskiy.chess.game.{GameUtil, Move}

/**
 * Author: Svyatoslav Ilinskiy
 * Date: 8/9/2015.
 */
object PositionEvaluator {
  val PAWN_STRENGTH = 1
  val KNIGHT_STRENGTH_AT_THE_BEGINNING = 3
  val KNIGHT_STRENGTH_AT_THE_END = 2
  val BISHOP_STRENGTH_AT_THE_BEGINNING = 2
  val BISHOP_STRENGTH_AT_THE_END = 3
  val ROOK_STRENGTH = 5
  val QUEEN_STRENGTH = 10

  def evaluatePositionAfterMove(board: BoardWrapper, move: Move): Int = {
    board.setPieceAccordingToMove(move)
    val strength: Int = evaluateCurrentPosition(board.getInner)
    board.setPieceAccordingToMove(move.inverse()) //roll back
    strength
    //todo: use this: ChessBoardUtil.makeMoveAndEvaluate(board, move, (b) => evaluateCurrentPosition(b))
  }

  def evaluateCurrentPosition(board: Board): Int = {
    val opponentColor = board.whoseTurnIsIt()
    val ourColor = ChessBoardUtil.inverse(opponentColor)

    strengthForColor(ourColor, board) - strengthForColor(opponentColor, board)

  }

  def strengthForColor(color: PieceColor, board: Board): Int = {
    import scala.collection.JavaConversions._

    GameUtil.getAllPieces(color, board).foldLeft(0) { (acc: Int, pos: Coordinates) =>
      val pieceStrength = board.getPieceAt(pos).getType match {
        case Pawn => PAWN_STRENGTH
        case Knight => KNIGHT_STRENGTH_AT_THE_BEGINNING //todo: use knight at beginning and end
        case Bishop => BISHOP_STRENGTH_AT_THE_BEGINNING
        case Rook => ROOK_STRENGTH
        case Queen => QUEEN_STRENGTH
        case _ => 0
      }
      //todo: what if game is over and there are no available moves?
      pieceStrength + acc
    }
  }
}
