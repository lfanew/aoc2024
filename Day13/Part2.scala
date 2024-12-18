// Button A: X+69, Y+23
// Button B: X+27, Y+71
// Prize: X=10000000018641, Y=10000000010279

// I think here since we don't just repeat until 100, we can repeat until
// aPresses are 0 and current point x or y is > prize x or y

// Naive solution is too long. Maybe I can find a point such that it evenly divides into a prize point
// and use that as the stopping point

package Day8;

import scala.annotation.tailrec

object Part2 {
  @main
  def solution(inputPath: String): Unit = {
    val lines = readLines(inputPath).filter(_.nonEmpty)
    val machines = buildMachines(lines)
    val answer = machines.map(m => minimumCost(m)).filter(_ != Long.MaxValue).sum()
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
  def minimumCost(machine: Machine, aPresses: Long = 0, bPresses: Long = 0, min: Long = Long.MaxValue): Long =
    println(s"a: $aPresses | b: $bPresses")
    val point = (machine.a * aPresses) + (machine.b * bPresses)
    if (point > machine.prize) {
      if (aPresses == 0) min
      else minimumCost(machine, 0, bPresses + 1, min)
    }
    else {
      val cost = (3 * aPresses) + bPresses
      minimumCost(machine, aPresses + 1, bPresses, if (point == machine.prize) Math.min(cost, min) else min)
    }

  case class Point(x: Long, y: Long) {
    def *(that: Long): Point = Point(this.x * that, this.y * that)
    def +(that: Point): Point = Point(this.x + that.x, this.y + that.y)
    def >(that: Point): Boolean = this.x > that.x || this.y > that.y
  }
  case class Machine(a: Point, b: Point, prize: Point)

  object Machine {
    private val pattern = raw"\d+".r
    def parseMachine(lines: List[String]): Machine =
      val aNumbers = pattern.findAllIn(lines(0)).map(_.toLong).toList
      val a = Point(aNumbers(0), aNumbers(1))
      
      val bNumbers = pattern.findAllIn(lines(1)).map(_.toLong).toList
      val b = Point(bNumbers(0), bNumbers(1))
      
      val prizeNumbers = pattern.findAllIn(lines(2)).map(_.toLong).toList.map(_ + 10000000000000L)
      val prize = Point(prizeNumbers(0), prizeNumbers(1))

      Machine(a, b, prize)
  }
}
