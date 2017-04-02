package ai

import core._

// TODO ab pruning
object Search {

  def minimax(board: Board, depth: Int): Value = {
    if (board.isGameOver) {
      if (board.winner == 0) return Score(0)
      else return PlyRemaining(0, -board.turn)
    }
    if (depth == 0) {
      return value(board)
    }

    // TODO reduce depth dynamically
    // this performs unnecessary evaluations when one of the moves is e.g. M3,
    // because it might still search for depth 5 down other branches when it only needs to look 2 deep
    // it should reduce the depth once it finds out that it's got a forced win
    val values = board.getMoves.map {
      b => minimax(b, depth - 1).addTurn
    }

    if (board.turn == 1) return values.max
    else return values.min
  }

  // TODO speed this up, depth 5 takes 1 second already
  def value(board: Board): Value = {
    val playerScore = scoreGroups(board, 1)
    val computerScore = scoreGroups(board, -1)
    Score(playerScore - computerScore)
  }

  private def scoreGroups(board: Board, turn: Player): Int = {
    // Each element in the seq is a position on the board, and each item in the list is how many in a row that is, for horizontal, vertical, and both diagonals
    // E.g. counts(0) will be the top left square, and _(0) will by how many pieces in a row horizontally there are of your colour as long as there is a free spot past them
    val counts: Seq[List[Int]] = (0 until board.width).flatMap {
      x =>
        (0 until board.height).map {
          y =>
            List(getGroupSize(lookForGroups(board, -1, 0, x, y, turn, 0), lookForGroups(board, 1, 0, x, y, turn, 0)),
              getGroupSize(lookForGroups(board, -1, -1, x, y, turn, 0), lookForGroups(board, 1, 1, x, y, turn, 0)),
              getGroupSize(lookForGroups(board, -1, 1, x, y, turn, 0), lookForGroups(board, 1, -1, x, y, turn, 0)),
              getGroupSize(lookForGroups(board, 0, -1, x, y, turn, 0), lookForGroups(board, 0, 1, x, y, turn, 0)))
        }
    }
    counts.map(_.sum).sum
  }

  private def getGroupSize(group1: (Int, Boolean), group2: (Int, Boolean)): Int = {
    if (!group1._2 && !group2._2) return 0
    else return group1._1 + group2._1 - 1
  }

  // The boolean in the return type is whether the piece after then end of the group is free or not
  @annotation.tailrec
  private final def lookForGroups(board: Board, xInc: Int, yInc: Int, xCurr: Int, yCurr: Int, turn: Player, count: Int): (Int, Boolean) = {
    if (board.gridOption(xCurr + xInc, yCurr + yInc).contains(turn)) {
      lookForGroups(board, xInc, yInc, xCurr + xInc, yCurr + yInc, turn, count + 1)
    } else if (board.gridOption(xCurr + xInc, yCurr + yInc).contains(0)) {
      (count, true)
    } else (count, false)
  }
}
