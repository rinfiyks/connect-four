import core.{Board, Piece}
import org.scalatest.FunSuite

class BoardTest extends FunSuite {

  test("getMoves returns the correct number of boards for an empty board") {
    assert(7 === Board.newBoard.getMoves.size)
  }

  test("getMoves returns the correct number of boards for a board with the middle column full") {
    assert(6 === Board(7, 6, List(Piece(3, 0, -1), Piece(3, 1, 1), Piece(3, 2, -1), Piece(3, 3, 1), Piece(3, 4, -1), Piece(3, 5, 1)), 1).getMoves.size)
  }

  test("empty board does not count as a win") {
    assert(0 === Board.newBoard.winner)
  }

  test("board with one piece in it does not count as a win") {
    assert(0 === Board.newBoard.getMoves(3).winner)
  }

  test("4 in a row vertically counts as a win") {
    val b = Board.newBoard.getMoves(0).getMoves(1).getMoves(0).getMoves(1).getMoves(0).getMoves(1).getMoves(0)
    assert(1 === b.winner)
  }

  test("4 in a row horizontally counts as a win") {
    val b = Board.newBoard.getMoves(0).getMoves(0).getMoves(1).getMoves(1).getMoves(2).getMoves(2).getMoves(3)
    assert(1 === b.winner)
  }

  test("4 in a row diagonally counts as a win") {
    val b = Board.newBoard.getMoves(0).getMoves(5).getMoves(4).getMoves(4).getMoves(3).getMoves(3).getMoves(2).getMoves(3).getMoves(2).getMoves(2).getMoves(5).getMoves(2)
    assert(-1 === b.winner)
  }

  test("a board where the best is 3 in a row does not count as a win") {
    val b = Board.newBoard.getMoves(1).getMoves(2).getMoves(3).getMoves(2).getMoves(1).getMoves(4).getMoves(3).getMoves(3).getMoves(2).getMoves(6).getMoves(0)
    assert(0 === b.winner)
  }

}
