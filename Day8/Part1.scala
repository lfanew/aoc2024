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
    println(grid.antennas.keySet.size)
    val antinodes = mutable.Set[Point]()
    for key <- grid.antennas.keySet do {
      val points = grid.antennas(key)
      val pairs = points.flatMap(p => points.map(q => (p, q)))
      print(pairs)
    }
  }
  
  
  def readLines(path: String): List[String] = {
    val source = scala.io.Source.fromFile(path)
    try {
      return source.getLines().toList
    } finally {
      source.close()
    }
  }
}

class Point(val x: Int, val y: Int) {
  def +(other: Point): Point = Point(this.x + other.x, this.y + other.y)
  def -(other: Point): Point = Point(this.x - other.x, this.y - other.y)
  def difference(other: Point): Point = Point(Math.abs(this.x - other.x), Math.abs(this.y - other.y))

  override def toString(): String = s"($x,$y)"
}

class Grid(val width: Int, val height: Int) {
  val antennas = mutable.Map[Char, ListBuffer[Point]]()
  def contains(point: Point): Boolean = {
    0.until(width).contains(point.x) && 0.until(height).contains(point.y)
  }
}