package me.ilinskiy.ChessAI

import me.ilinskiy.chess.chessBoard.PieceColor
import me.ilinskiy.chess.game.{GameRunner, UserPlayer}

/**
 * Author: Svyatoslav Ilinskiy
 * Date: 8/8/15.
 */
object Main {
  def main(args: Array[String]) {
    GameRunner.askTimeOut()
    do {
      GameRunner.runGame(new UserPlayer(PieceColor.Black), new UserPlayer(PieceColor.White))
    } while (GameRunner.askToPlayAgain() == 0)
    GameRunner.dispose()
  }
}
