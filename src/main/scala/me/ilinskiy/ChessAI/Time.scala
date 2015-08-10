package me.ilinskiy.ChessAI

/**
 * Author: Svyatoslav Ilinskiy
 * Date: 8/10/2015.
 */
object Time {
  @volatile
  var moveNeededBy: Option[Long] = None
}
