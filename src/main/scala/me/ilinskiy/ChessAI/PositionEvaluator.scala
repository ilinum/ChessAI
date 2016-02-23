package me.ilinskiy.ChessAI

import me.ilinskiy.chess.chessboard._
import me.ilinskiy.chess.game.GameUtil
import me.ilinskiy.chess.game.moves.Move
import me.ilinskiy.chess.chessboard.PieceType._

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
    ChessBoardUtil.makeMoveAndEvaluate(board.getInner, move, (b) => evaluateCurrentPosition(b))
  }

  def evaluateCurrentPosition(board: Board): Int = {
    val ourColor = board.whoseTurnIsIt()
    val opponentColor = ourColor.inverse()
    val difference = strengthForColor(ourColor, board) - strengthForColor(opponentColor, board)

    (GameUtil.getAvailableMoves(opponentColor, board).size(), GameUtil.getAvailableMoves(ourColor, board).size()) match {
      case (0, i) if i > 0 => Int.MaxValue //we win!
      case (0, 0) if difference < 0 => Int.MaxValue / 2 //we accept the draw because we're losing
      case _ => difference
    }
  }

  def strengthForColor(color: PieceColor, board: Board): Int = {
    import scala.collection.JavaConversions._

    GameUtil.getAllPieces(color, board).foldLeft(0) { (acc: Int, pos: Coordinates) =>
      val pieceStrength = board.getPieceAt(pos).getType match {
        case Pawn => PAWN_STRENGTH
        case Knight if AI.movesMade < (AI.averageGameLength / 2) => KNIGHT_STRENGTH_AT_THE_BEGINNING
        case Knight =>  KNIGHT_STRENGTH_AT_THE_END
        case Bishop if AI.movesMade < (AI.averageGameLength / 2) => BISHOP_STRENGTH_AT_THE_BEGINNING
        case Bishop => BISHOP_STRENGTH_AT_THE_END
        case Rook => ROOK_STRENGTH
        case Queen => QUEEN_STRENGTH
        case _ => 0
      }
      pieceStrength + acc
    }
  }
}
