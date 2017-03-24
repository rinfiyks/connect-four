package core

case class Board(width: Int, height: Int, pieces: List[Piece], turn: Player) {

  lazy val grid: IndexedSeq[IndexedSeq[Player]] = {
    (0 until width).map {
      x =>
        (0 until height).map {
          y => pieces.find(p => x == p.x && y == p.y).map(_.player).getOrElse(0)
        }
    }
  }

  lazy val isGameOver: Boolean =
    winner != 0 || pieces.size == width * height

  // The assumption is that this will get checked after every move - it will only look for wins involving the last piece played.
  lazy val winner: Player = {
    @annotation.tailrec
    def lookForAdjacentPieces(xInc: Int, yInc: Int, xCurr: Int, yCurr: Int, count: Int): Int = {
      if (gridOrElse0(xCurr + xInc, yCurr + yInc) == -turn) return lookForAdjacentPieces(xInc, yInc, xCurr + xInc, yCurr + yInc, count + 1)
      else count
    }

    if (pieces.isEmpty) 0
    else {
      val x = pieces.head.x
      val y = pieces.head.y
      if ((lookForAdjacentPieces(-1, 0, x, y, 1) + lookForAdjacentPieces(1, 0, x, y, 1) > 4)
        || (lookForAdjacentPieces(-1, -1, x, y, 1) + lookForAdjacentPieces(1, 1, x, y, 1) > 4)
        || (lookForAdjacentPieces(-1, 1, x, y, 1) + lookForAdjacentPieces(1, -1, x, y, 1) > 4)
        || (lookForAdjacentPieces(0, -1, x, y, 1) + lookForAdjacentPieces(0, 1, x, y, 1) > 4)) {
        -turn
      } else 0
    }
  }

  def gridOrElse0(x: Int, y: Int): Player = {
    if (x < 0 || y < 0 || x >= width || y >= width) 0
    else grid(x)(y)
  }

  def getMoves: List[Board] = {
    (0 until width).toList.flatMap {
      x =>
        val highestPiece: Int = pieces.filter(_.x == x).sortBy(-_.y).headOption.map(_.y).getOrElse(-1)
        if (highestPiece == height - 1) List.empty
        else List(Board(width, height, Piece(x, highestPiece + 1, turn) +: pieces, -turn))
    }
  }

}

object Board {

  def newBoard = Board(7, 6, List.empty, 1)

  def fullMiddleColumnBoard = Board(7, 6, List(Piece(3, 0, -1), Piece(3, 1, 1), Piece(3, 2, -1), Piece(3, 3, 1), Piece(3, 4, -1), Piece(3, 5, 1)), 1)

}
