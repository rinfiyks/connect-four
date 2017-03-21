import core.Board
import org.scalatest.FunSuite

class BoardTest extends FunSuite {

  test("getMoves returns the correct number of boards for an empty board") {
    assert(7 === Board.newBoard.getMoves.size)
  }

  test("getMoves returns the correct number of boards for a board with the middle column full") {
    assert(6 === Board.fullMiddleColumnBoard.getMoves.size)
  }

}
