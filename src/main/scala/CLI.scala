import ai.Search
import core.{Board, Player}

import scala.util.Try

// CLI class to be run standalone for testing purposes or to play the game
object CLI extends App {

  println("Enter a number 1 to 7")
  val endBoard = loop(Board.newBoard)
  prettyPrint(endBoard)
  println("Game over!")

  @annotation.tailrec
  def loop(b: Board): Board = {
    prettyPrint(b)
    val i: Int = readColumn(b.width) - 1

    val nextBoard = b.move(i)
    if (nextBoard.isGameOver) return nextBoard
    prettyPrint(nextBoard)

    val computersBoard = nextBoard.getMoves.maxBy(Search.negamax(_, 5))
    if (computersBoard.isGameOver) return computersBoard

    loop(computersBoard)
  }

  def prettyPrint(board: Board): Unit = {
    println
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

  private def readColumn(maxSize: Int): Int =
    io.Source.stdin.getLines().dropWhile {
      s =>
        val i = Try(s.toInt).toOption.getOrElse(0)
        i <= 0 || i > maxSize
    }.next().toInt
}
