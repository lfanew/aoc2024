import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;

public class Part1 {
  private static boolean isCorrect(Map<Integer,List<Integer>> rules, List<Integer> update) {
    for (var i = 0; i < update.size(); i++) {
      var n = update.get(i);
      if (rules.containsKey(n)) {
        for (var beforeNumber : rules.get(n)) {
          for (var j = i - 1; j >= 0; j--) {
            if (update.get(j) == beforeNumber) return false;
          }
        }
      }
    }
    return true;
  }
  public static void main(String[] args) {
    var file = new File("input.txt");
    Map<Integer,List<Integer>> rules = new HashMap<>();
    long answer = 0;

    try (var scanner = new Scanner(file)) {
      while (scanner.hasNext()) {
        var line = scanner.nextLine();
        if (line.contains("|")) {
          var parts = line.split("\\|");
          var a = Integer.parseInt(parts[0]);
          var b = Integer.parseInt(parts[1]);
          if (!rules.containsKey(a)) {
            var ruleList = new ArrayList<Integer>();
            rules.put(a, ruleList);
          }
          rules.get(a).add(b);
        } else if (!line.isBlank()) {
          var update = new ArrayList<Integer>();
          for (var s : line.split(",")) {
            update.add(Integer.parseInt(s));
          }
          if (isCorrect(rules, update)) {
            var middle = update.size() / 2;
            answer += update.get(middle);
          }
        }
      }
    } catch (FileNotFoundException e) {
      System.err.println("Could not find file to read");
    }

    System.out.println(answer);
  }
}
