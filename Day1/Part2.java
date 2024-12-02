// create a map for left and right
  // the maps represent a number and its occurrences
// the answer is a running total of: n * left[n] * right[n]

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;

public class Part2 {
  public static void main(String[] args) {
    var file = new File("input.txt");

    HashMap<Long, Long> left = new HashMap<>();
    HashMap<Long, Long> right = new HashMap<>();

    try (var scanner = new Scanner(file)) {
      while (scanner.hasNext()) {
        var line = scanner.nextLine();
        var parts = line.split("\s+");

        var leftNumber = Long.parseLong(parts[0]);
        left.put(leftNumber, left.getOrDefault(leftNumber, 0L) + 1);
        var rightNumber = Long.parseLong(parts[1]);
        right.put(rightNumber, right.getOrDefault(rightNumber, 0L) + 1);
      }
    } catch (FileNotFoundException e) {
      System.err.println("Could not find file to read");
    }

    long answer = 0L;

    for (var number : left.keySet()) {
      answer += number * left.get(number) * right.getOrDefault(number, 0L);
    }

    System.out.println(answer);
  }
}
