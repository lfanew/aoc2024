package Day8;

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Part1 {
  @main
  def solution(inputPath: String): Unit = {
    val lines = readLines(inputPath)
    val grid = Grid(lines(0).length(), lines.size)
    for (line, y) <- lines.zipWithIndex do {
      for (c, x) <- line.zipWithIndex do {
        if (c != '.') {
          val point = Point(x, y)
          grid.antennas.get(c) match {
            case Some(a) => a.append(point)
            case None => {
              grid.antennas(c) = ListBuffer(point)
            }
          }
        }
      }
    }

    val antinodes = mutable.Set[Point]()
    for key <- grid.antennas.keySet do {
      val points = grid.antennas(key)
      val pairs = ListBuffer[(Point, Point)]()
      for i <- 0 until points.size do
        for j <- i + 1 until points.size do
          val difference = points(i) - points(j)
          val antinode1 = points(i) + difference
          val antinode2 = points(j) - difference
          if (grid.contains(antinode1)) antinodes.add(antinode1)
          if (grid.contains(antinode2)) antinodes.add(antinode2)

    }
    println(antinodes.size)
  }

  def readLines(path: String): List[String] = {
    val source = scala.io.Source.fromFile(path)
    try {
      return source.getLines().toList
    } finally {
      source.close()
    }
  }

  class Point(val x: Int, val y: Int) {
    def +(other: Point): Point = Point(this.x + other.x, this.y + other.y)
    def -(other: Point): Point = Point(this.x - other.x, this.y - other.y)

    override def toString(): String = s"[$x,$y]"
    override def equals(obj: Any): Boolean = {
      obj match {
        case that: Point => that.x == x && that.y == y
        case _           => false
      }
    }
    override def hashCode(): Int = 31 * x + y
  }

  class Grid(val width: Int, val height: Int) {
    val antennas = mutable.Map[Char, ListBuffer[Point]]()
    def contains(point: Point): Boolean = {
      0.until(width).contains(point.x) && 0.until(height).contains(point.y)
    }
  }
}
