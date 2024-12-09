package Day7;

import scala.collection.mutable.Stack
object Part2 {
    @main
    def solution(inputPath: String): Unit = {
      var answer: BigInt = 0
      val lines = readLines(inputPath)
      for line <- lines do {
        val pattern = "\\d+".r
        val numbers = pattern.findAllMatchIn(line).map(m => m.toString.toLong).toList
        
        val goal = numbers(0)
        val results = possibilities(numbers.drop(2), numbers(1), List[BigInt]())
        if (results.contains(goal)) answer += goal
      }
    
      println(answer)
    }
    
    def concat(a: BigInt, b: BigInt): BigInt = BigInt(s"$a$b")
    
    def possibilities(operands: List[Long], total: BigInt, result: List[BigInt]): List[BigInt] = {
      if (operands.isEmpty) {
        return result :+ total
      }
      return possibilities(
        operands.drop(1), total + operands(0), result)
            ::: possibilities(operands.drop(1), total * operands(0), result)
            ::: possibilities(operands.drop(1), concat(total, operands(0)), result)
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
