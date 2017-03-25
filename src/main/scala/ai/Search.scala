package ai

import core.{Board, Player}

object Search {

  def negamax(board: Board, depth: Int): Int = {
    if (depth == 0 || board.isGameOver) return board.turn * value(board)

    val v = board.getMoves.map {
      b => -negamax(b, depth - 1)
    }.max
    if (depth > 4) println("value: " + v)
    v
  }

  // TODO consider other player's groups
  def value(board: Board): Int = {
    if (board.winner != 0) return Int.MaxValue
    if (board.isGameOver) return 0

    // Each element in the seq is a position on the board, and each item in the tuple is how many in a row that is, for horizontal, vertical, and both diagonals
    // E.g. counts(0) will be the top left square, and ._1 will by how many pieces in a row horizontally there are of your colour as long as there is a free spot past them
    val counts: Seq[(Int, Int, Int, Int)] = (0 until board.width).flatMap {
      x =>
        (0 until board.height).map {
          y => // TODO maybe split these into a tuple of size 8
            (lookForGroups(board, -1, 0, x, y, 1) + lookForGroups(board, 1, 0, x, y, 1),
              lookForGroups(board, -1, -1, x, y, 1) + lookForGroups(board, 1, 1, x, y, 1),
              lookForGroups(board, -1, 1, x, y, 1) + lookForGroups(board, 1, -1, x, y, 1),
              lookForGroups(board, 0, -1, x, y, 1) + lookForGroups(board, 0, 1, x, y, 1))
        }
    }
    // These are squared, so that much more weight is given to 3 in a row than 2 in a row
    counts.map(c => c._1 * c._1 + c._2 * c._2 + c._3 * c._3 + c._4 * c._4).sum
  }

  @annotation.tailrec
  private final def lookForGroups(board: Board, xInc: Int, yInc: Int, xCurr: Int, yCurr: Int, count: Int): Int = {
    if (board.gridOrElse0(xCurr + xInc, yCurr + yInc) == -board.turn) {
      lookForGroups(board, xInc, yInc, xCurr + xInc, yCurr + yInc, count + 1)
    } else if (board.gridOrElse0(xCurr + xInc, yCurr + yInc) == 0) { // TODO This is bad, because it will think that it can play outside the bounds of the board
      count
    } else 0
  }
}
