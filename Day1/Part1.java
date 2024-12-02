// binary insert into each list
// calculate a running sum of distances between pairs

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Part1 {
  public static void main(String[] args) {
    var file = new File("input.txt");

    ArrayList<Long> left = new ArrayList<>();
    ArrayList<Long> right = new ArrayList<>();

    try (var scanner = new Scanner(file)) {
      while (scanner.hasNext()) {
        var line = scanner.nextLine();
        var parts = line.split("\s+");

        var leftNumber = Long.parseLong(parts[0]);
        binaryInsert(left, leftNumber);
        var rightNumber = Long.parseLong(parts[1]);
        binaryInsert(right, rightNumber);
      }
    } catch (FileNotFoundException e) {
      System.err.println("Could not find file to read");
    }

    long answer = 0L;
    for (var i = 0; i < left.size() && left.size() == right.size(); i++) {
      answer += Math.abs(left.get(i) - right.get(i));
    }
    System.out.println(answer);
  }

  public static void binaryInsert(ArrayList<Long> list, Long number) {
    var insertionPoint = Collections.binarySearch(list, number);
    if (insertionPoint < 0) {
      insertionPoint = (insertionPoint + 1) * - 1;
    }
    list.add(insertionPoint, number);
  }
}
