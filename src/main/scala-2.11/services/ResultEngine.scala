package services

import models.GameState.{DRAW, LOSE, WIN}
import models.{Ranking, Result, Team}

object ResultEngine {

  /** *
    * Funtion to parse the input file
    *
    * @param lines
    * @return
    */
  def generate(lines: List[String]): List[Ranking] = {
    val results = lines.flatMap { l =>
      parseLine(l)
    }

    val teams = results.groupBy(_.team.name).map { t =>
      val score = t._2.map(_.state.points).sum

      Team(t._1, score)
    }.toList

    rankResults(teams)
  }

  def rankResults(teams: List[Team]): List[Ranking] = {
    def iterate(tms: List[Team], previousScore: Int, rankPosition: Int, actualPosition: Int, rankings: List[Ranking]): List[Ranking] = tms match {
      case Nil => rankings
      case (h :: t) => {
        val pos =
          if (h.score == previousScore) rankPosition
          else actualPosition

        val rank = Ranking(pos, h.name, h.score)

        iterate(t, h.score, pos, actualPosition+1, rankings ::: List(rank) )
      }
    }

    iterate (teams.sortBy(s => (-s.score, s.name)), -200, 1, 1, List())
  }

  /** *
    * Parse a line in the file
    *
    * @param line
    * @return
    */
  private def parseLine(line: String) = {
    val split = line.split(',').toList
    val pattern = "^(.*) (\\d*)$".r

    if (split.length != 2) throw new IllegalArgumentException(s"Invalid line: $line")

    val teams = split.map { player =>
      val pattern(name, score) = player.trim

      Team(name, score.toInt)
    }

    validate(teams.head, teams(1))
    getResult(teams.head, teams(1))
  }

  /** *
    * Function to validate some logic for input.
    *
    * @param home
    * @param away
    */
  private def validate(home: Team, away: Team) = {
    if (home.name == away.name) throw new IllegalArgumentException(s"A team cannot play itself!")
  }

  /** *
    * Determines the result of the match
    *
    * @param home
    * @param away
    * @return
    */
  private def getResult(home: Team, away: Team) = {
    if (home.score == away.score) {

      List(Result(home, DRAW), Result(away, DRAW))
    } else if (home.score > away.score) {

      List(Result(home, WIN), Result(away, LOSE))
    } else

      List(Result(home, LOSE), Result(away, WIN))
  }
}
