package me.ilinskiy.ChessAI

import me.ilinskiy.chess.chessBoard.PieceColor
import me.ilinskiy.chess.game.GameRunner
import me.ilinskiy.chess.ui.JSwingUserPlayer

/**
 * Author: Svyatoslav Ilinskiy
 * Date: 8/8/15.
 */
object Main {
  def main(args: Array[String]) {
    GameRunner.askTimeOut()
    do {
      GameRunner.runGame(AI(PieceColor.Black), new JSwingUserPlayer(PieceColor.White))
    } while (GameRunner.askToPlayAgain())
  }
}
