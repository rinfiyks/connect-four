package core

// Represents the board value.
// Can either be a Score, which estimates the value of the board, or a PlyRemaining,
// which says how many ply until a win can be forced with optimal play.
sealed abstract class Value extends Ordered[Value] {

  override def compare(that: Value): Int = this match {
    case PlyRemaining(p1, t1) => that match {
      case PlyRemaining(p2, t2) if t1 == t2 => t1 * (p2 compareTo p1)
      case PlyRemaining(_, t2) if t1 != t2 => t1 compareTo t2
      case _ => t1
    }
    case Score(s1) => that match {
      case PlyRemaining(_, t2) => -t2
      case Score(s2) => s1 compareTo s2
    }
  }

  def addTurn: Value

}

final case class Score(value: Double) extends Value {
  override def addTurn: Value = this

  override def toString: String = value.toString
}

final case class PlyRemaining(ply: Int, player: Player) extends Value {
  override def addTurn: Value = PlyRemaining(ply + 1, player)

  // The M is an allusion to chess engines (M3 means forced mate in 3)
  override def toString: String = player match {
    case 1 => "M" + ply.toString
    case -1 => "M-" + ply.toString
  }
}