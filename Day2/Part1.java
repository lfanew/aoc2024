import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Part1 {
  public static void main(String[] args) {
    var file = new File("input.txt");
    long answer = 0L;

    try (var scanner = new Scanner(file)) {
      while (scanner.hasNext()) {
        var line = scanner.nextLine();
        var report = parseReport(line);
        if (isSafe(report)) answer++;
      }
    } catch (FileNotFoundException e) {
      System.err.println("Could not find file to read");
    }

    System.out.println(answer);
  }

  private static List<Long> parseReport(String s) {
    return Arrays.stream(s.split(" ")).map(Long::parseLong).toList();
  }

  private static boolean isSafe(List<Long> report) {
    var isAscending = report.get(0) < report.get(1);

    for (var i = 1; i < report.size(); i++) {
      var distance = Math.abs(report.get(i) - report.get(i-1));
      var current = report.get(i);
      var previous = report.get(i-1);

      if (distance == 0 || distance > 3) return false;
      if (isAscending && previous > current) return false;
      if (!isAscending && previous < current) return false;
    }
    return true;
  }
}
