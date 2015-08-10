package me.ilinskiy.ChessAI

import scala.util.Random

/**
*  Author: Svyatoslav Ilinskiy
*  Date: 8/10/2015.
*/
object AIUtil {
  def randomElement[T](list: Seq[T]): T = list(new Random().nextInt(list.size))
}
