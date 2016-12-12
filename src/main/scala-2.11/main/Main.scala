package main

import java.io.FileNotFoundException

import services.ResultEngine

import scala.io.Source

object Main {

  def main(args: Array[String]) = {

    if (args.length == 0) {
      println("Please provide a filename")
    } else {
      val lines = readFile(args.head)

      val rankings = ResultEngine.generate(lines)

      println(rankings.mkString("\n"))
    }
  }

  /** *
    * Read the file and validate the file path
    *
    * @param fileName
    * @return
    */
  private def readFile(fileName: String): List[String] = {
    try {
      Source.fromFile(fileName).getLines.toList
    } catch {
      case f: FileNotFoundException => throw new Exception(s"Cannot find file: $fileName")
      case _: Throwable => throw new Exception("Internal Error. Cannot open file")
    }
  }
}
