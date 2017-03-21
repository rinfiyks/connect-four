import core.Board

object CLI extends App {


  prettyPrint(Board.newBoard.getMoves(0).getMoves(6))

  def prettyPrint(board: Board): Unit = {
    board.grid.transpose.reverse.foreach {
      v =>
        println("|" + v.map {
          case -1 => "O"
          case 0 => "·"
          case 1 => "X"
        }.mkString + "|")
    }
    println("-" * (board.width + 2))
  }

}
