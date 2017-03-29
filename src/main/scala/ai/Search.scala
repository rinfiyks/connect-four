package ai

import core.{Board, Player}

// TODO negamax with ab pruning
object Search {

  def negamax(board: Board, depth: Int): Double = {
    if (depth == 0 || board.isGameOver) return board.turn * value(board)

    val v = board.getMoves.map {
      b => -negamax(b, depth - 1)
    }.max
    if (depth > 4) println("value: " + v)
    v
  }

  // TODO speed this up, depth 5 takes 1 second already
  def value(board: Board): Double = {
    if (board.winner != 0) return 100000 // TODO new type that encapsulates the value but can just be "win/loses" too
    if (board.isGameOver) return 0
  
    val currentScore = scoreGroups(board, -board.turn)
    val opponentScore = scoreGroups(board, board.turn)
    currentScore.toDouble / opponentScore
  }
  
  private def scoreGroups(board: Board, turn: Player): Int = {
    // Each element in the seq is a position on the board, and each item in the list is how many in a row that is, for horizontal, vertical, and both diagonals
    // E.g. counts(0) will be the top left square, and _(0) will by how many pieces in a row horizontally there are of your colour as long as there is a free spot past them
    val counts: Seq[List[Int]] = (0 until board.width).flatMap {
      x =>
        (0 until board.height).map {
          y =>
            List(lookForGroups(board, -1, 0, x, y, turn, 1) + lookForGroups(board, 1, 0, x, y, turn, 1),
              lookForGroups(board, -1, -1, x, y, turn, 1) + lookForGroups(board, 1, 1, x, y, turn, 1),
              lookForGroups(board, -1, 1, x, y, turn, 1) + lookForGroups(board, 1, -1, x, y, turn, 1),
              lookForGroups(board, 0, -1, x, y, turn, 1) + lookForGroups(board, 0, 1, x, y, turn, 1))
        }
    }
    // These are squared, so that much more weight is given to 3 in a row than 2 in a row
    counts.map(_.map(x => x * x).sum).sum
  }

  @annotation.tailrec
  private final def lookForGroups(board: Board, xInc: Int, yInc: Int, xCurr: Int, yCurr: Int, turn: Player, count: Int): Int = {
    if (board.gridOption(xCurr + xInc, yCurr + yInc).contains(turn)) {
      lookForGroups(board, xInc, yInc, xCurr + xInc, yCurr + yInc, turn, count + 1)
    } else if (board.gridOption(xCurr + xInc, yCurr + yInc).contains(0)) {
      count
    } else 0
  }
}
