package Day8;

import scala.annotation.tailrec

object Part1 {
  @main
  def solution(inputPath: String): Unit = {
    val lines = readLines(inputPath).filter(_.nonEmpty)
    val machines = buildMachines(lines)
    val answer = machines.map(m => minimumCost(m)).filter(_ != Int.MaxValue).sum()
    println(answer)
  }

  @tailrec
  def buildMachines(lines: List[String], acc: List[Machine] = List.empty): List[Machine] =
    if (lines.size < 3) acc
    else buildMachines(lines.drop(3), acc :+ Machine.parseMachine(lines.take(3)))

  def readLines(path: String): List[String] = {
    val source = scala.io.Source.fromFile(path)
    try {
      source.getLines().toList
    } finally {
      source.close()
    }
  }

  @tailrec
  def minimumCost(machine: Machine, aPresses: Int = 0, bPresses: Int = 0, min: Int = Int.MaxValue): Int =
    if (bPresses > 100) min
    else if (aPresses > 100) minimumCost(machine, 0, bPresses + 1, min)
    else {
      val point = (machine.a * aPresses) + (machine.b * bPresses)
      val cost = (3 * aPresses) + bPresses
      minimumCost(machine, aPresses + 1, bPresses, if (point == machine.prize) Math.min(cost, min) else min)
    }

  case class Point(x: Int, y: Int) {
    def *(that: Int): Point = Point(this.x * that, this.y * that)
    def +(that: Point): Point = Point(this.x + that.x, this.y + that.y)
  }
  case class Machine(a: Point, b: Point, prize: Point)

  object Machine {
    private val pattern = raw"\d+".r
    def parseMachine(lines: List[String]): Machine =
      val aNumbers = pattern.findAllIn(lines(0)).map(_.toInt).toList
      val a = Point(aNumbers(0), aNumbers(1))
      
      val bNumbers = pattern.findAllIn(lines(1)).map(_.toInt).toList
      val b = Point(bNumbers(0), bNumbers(1))
      
      val prizeNumbers = pattern.findAllIn(lines(2)).map(_.toInt).toList
      val prize = Point(prizeNumbers(0), prizeNumbers(1))

      Machine(a, b, prize)
  }
}
