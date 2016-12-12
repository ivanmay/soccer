import models.Ranking
import org.scalatest.FlatSpec
import services.ResultEngine

class GameEngineTests extends FlatSpec {
  val t1 = List("Tarantulas 8, Blakernasie 3")

  val fail = List("Tarantulas 4, Tarantulas 2")

  val draw = List("Tarantulas 9, Blakernasie 9")
  val winLose = List("Tarantulas 9, Blakernasie 10")

  val sameRank = List("Man Seperated 1, FC Bismarck 4", "FC Bismarck 2, Arsenals 3")


  val spaces = List("  T g annn 5, J m ff 3")

  "Input" should "fail if a team plays against itself" in {
    assertThrows[IllegalArgumentException] {
      ResultEngine.generate(fail)
    }
  }

  "Input with spaces" should "not affect the output" in {
    val rankings = ResultEngine.generate(spaces)

    assert(rankings.head == Ranking(1, "T g annn", 3))
  }

  "A draw" should "only be one 1 point per team" in {
    val rankings = ResultEngine.generate(draw)

    assert(rankings == List(Ranking(1, "Blakernasie", 1), Ranking(1, "Tarantulas", 1)))
  }

  "A win/lose" should "give 3 points to the winner and 0 to the loser" in {
    val rankings = ResultEngine.generate(winLose)

    assert(rankings == List(Ranking(1, "Blakernasie", 3), Ranking(2, "Tarantulas", 0)))
  }

  "Teams with the same number of points" should "get the same rank, in alphabetical order and the following rank should be the ignored" in {
    val rankings = ResultEngine.generate(sameRank)

    assert(rankings == List(Ranking(1, "Arsenals", 3), Ranking(1, "FC Bismarck", 3), Ranking(3, "Man Seperated", 0)))
  }
}
