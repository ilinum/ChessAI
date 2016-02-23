package me.ilinskiy.ChessAI

import me.ilinskiy.chess.chessboard.PieceColor
import me.ilinskiy.chess.game.GameRunner
import me.ilinskiy.chess.ui.{JSwingChessPainter, JSwingUserPlayer}

/**
 * Author: Svyatoslav Ilinskiy
 * Date: 8/8/15.
 */
object Main {
  def main(args: Array[String]) {
    val runner = new GameRunner(new JSwingChessPainter)
    runner.askTimeOut()
    do {
      runner.runGame(new AI(PieceColor.Black), new JSwingUserPlayer(PieceColor.White))
    } while (runner.askToPlayAgain())
    runner.dispose()
  }
}
