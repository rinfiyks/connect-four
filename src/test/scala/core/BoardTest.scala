package core

import org.scalatest.FunSuite

// TODO come up with a better way than .move( ).get repeatedly
class BoardTest extends FunSuite {

  test("move returns the correct number of boards for an empty board") {
    assert(7 === Board.newBoard.getMoves.size)
  }

  test("move returns the correct number of boards for a board with the middle column full") {
    assert(6 === Board(7, 6, List(Piece(3, 0, -1), Piece(3, 1, 1), Piece(3, 2, -1), Piece(3, 3, 1), Piece(3, 4, -1), Piece(3, 5, 1)), 1).getMoves.size)
  }

  test("empty board does not count as a win") {
    assert(0 === Board.newBoard.winner)
  }

  test("board with one piece in it does not count as a win") {
    assert(0 === Board.newBoard.move(3).get.winner)
  }

  test("4 in a row vertically counts as a win") {
    val b = Board.newBoard.move(0).get.move(1).get.move(0).get.move(1).get.move(0).get.move(1).get.move(0).get
    assert(1 === b.winner)
  }

  test("4 in a row horizontally counts as a win") {
    val b = Board.newBoard.move(0).get.move(0).get.move(1).get.move(1).get.move(2).get.move(2).get.move(3).get
    assert(1 === b.winner)
  }

  test("4 in a row diagonally counts as a win") {
    val b = Board.newBoard.move(0).get.move(5).get.move(4).get.move(4).get.move(3).get.move(3).get.move(2).get.move(3).get.move(2).get.move(2).get.move(5).get.move(2).get
    assert(-1 === b.winner)
  }

  test("a board where the best is 3 in a row does not count as a win") {
    val b = Board.newBoard.move(1).get.move(2).get.move(3).get.move(2).get.move(1).get.move(4).get.move(3).get.move(3).get.move(2).get.move(6).get.move(0).get
    assert(0 === b.winner)
  }

}
