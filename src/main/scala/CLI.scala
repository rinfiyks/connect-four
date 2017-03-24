import core.{Board, Player}

// CLI class to be run standalone for testing purposes
object CLI extends App {

  val b = Board.newBoard.getMoves(0).getMoves(5).getMoves(4).getMoves(4).getMoves(3).getMoves(3).getMoves(2).getMoves(3).getMoves(2).getMoves(2).getMoves(5).getMoves(2)

  prettyPrint(b)
  println(b.winner)

  def prettyPrint(board: Board): Unit = {
    board.grid.transpose.reverse.foreach {
      line =>
        println("|" + line.map(playerMap).mkString + "|")
    }
    println("-" * (board.width + 2) + "  next to move: " + playerMap(board.turn))

    def playerMap(p: Player): String = p match {
      case -1 => "O"
      case 1 => "X"
      case _ => "·"
    }
  }

}
