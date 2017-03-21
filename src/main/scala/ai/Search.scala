package ai

import core.{Board, Player}

object Search {

  /*
    TODO (wikipedia defintion for negamax):
    02     if depth = 0 or node is a terminal node
    03         return color * the heuristic value of node

    04     bestValue := −∞
    05     foreach child of node
    06         v := −negamax(child, depth − 1, −color)
    07         bestValue := max( bestValue, v )
    08     return bestValue
   */
  def negamax(board: Board, depth: Int, player: Player): Int = {
    ???
  }

  def value(board: Board, player: Player): Int = {
    ???
  }
}
