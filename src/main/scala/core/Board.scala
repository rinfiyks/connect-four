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

  def getMoves: List[Board] = {
    (0 until width).toList.flatMap {
      x =>
        val highestPiece: Int = pieces.filter(_.x == x).sortBy(-_.y).headOption.map(_.y).getOrElse(-1)
        if (highestPiece == height - 1) List.empty
        else List(Board(width, height, Piece(x, highestPiece + 1, turn) +: pieces, -turn))
    }
  }

  def isGameOver: Boolean = {
    ???
  }

  def winner: Int = {
    ???
  }

}

object Board {

  def newBoard = Board(7, 6, List.empty, 1)

  def fullMiddleColumnBoard = Board(7, 6, List(Piece(3, 0, -1), Piece(3, 1, 1), Piece(3, 2, -1), Piece(3, 3, 1), Piece(3, 4, -1), Piece(3, 5, 1)), 1)

}
