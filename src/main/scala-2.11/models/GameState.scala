package models

/**
  * Created by ivanmay on 2016/12/12.
  */
sealed trait GameState {
  val points: Int
}

/***
  * Result Enum that keeps track of result and corresponding points
  */
object GameState {
  case object WIN extends GameState {
    val points = 3
  }

  case object LOSE extends GameState {
    val points = 0
  }

  case object DRAW extends GameState {
    val points = 1
  }
}