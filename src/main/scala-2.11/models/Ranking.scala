package models

case class Ranking (
  position: Int,
  name: String,
  score: Int
) {
  val pts =
    if (score == 1) "pnt"
    else "pts"

  override def toString = s"$position. $name, $score $pts"

}