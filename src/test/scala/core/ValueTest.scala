package core

import org.scalatest.FunSuite

class ValueTest extends FunSuite {

  test("Score < PlyRemaining for player 1") {
    assert(true === (Score(5) < PlyRemaining(8, 1)))
    assert(true === (Score(8) < PlyRemaining(5, 1)))
  }

  test("Score > PlyRemaining for player -1") {
    assert(true === (Score(5) > PlyRemaining(8, -1)))
    assert(true === (Score(8) > PlyRemaining(5, -1)))
  }

  test("Score < Score") {
    assert(true === (Score(5) < Score(8)))
  }

  test("Score > Score") {
    assert(true === (Score(8) > Score(5)))
  }

  test("Score == Score") {
    assert(true === (Score(5) == Score(5)))
  }

  test("PlyRemaining > PlyRemaining for player 1") {
    assert(true === (PlyRemaining(5, 1) > PlyRemaining(8, 1)))
  }

  test("PlyRemaining < PlyRemaining for player 1") {
    assert(true === (PlyRemaining(8, 1) < PlyRemaining(5, 1)))
  }

  test("PlyRemaining > PlyRemaining for player -1") {
    assert(true === (PlyRemaining(8, -1) > PlyRemaining(5, -1)))
  }

  test("PlyRemaining < PlyRemaining for player -1") {
    assert(true === (PlyRemaining(5, -1) < PlyRemaining(8, -1)))
  }

  test("PlyRemaining < PlyRemaining for opposite players") {
    assert(true === (PlyRemaining(5, -1) < PlyRemaining(8, 1)))
    assert(true === (PlyRemaining(8, -1) < PlyRemaining(5, 1)))
  }

  test("PlyRemaining > PlyRemaining for opposite players") {
    assert(true === (PlyRemaining(5, 1) > PlyRemaining(8, -1)))
    assert(true === (PlyRemaining(8, 1) > PlyRemaining(5, -1)))
  }

  test("PlyRemaining == PlyRemaining for player 1") {
    assert(true === (PlyRemaining(5, 1) == PlyRemaining(5, 1)))
  }

  test("PlyRemaining == PlyRemaining for player -1") {
    assert(true === (PlyRemaining(5, -1) == PlyRemaining(5, -1)))
  }

}
