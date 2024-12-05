import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Part1 {
  private static Point[] steps = {
    new Point(-1, -1),
    new Point(-1, 0),
    new Point(0, -1), 
    new Point(1, 1),
    new Point(1, 0),
    new Point(0, 1),
    new Point(-1, 1),
    new Point(1, -1)
  };
  public static void main(String[] args) {
    var file = new File("input.txt");
    var grid = new Grid();
    long answer = 0;

    try (var scanner = new Scanner(file)) {
      while (scanner.hasNext()) {
        var line = scanner.nextLine();
        var row = line.chars().mapToObj(e->(char)e).collect(Collectors.toList());
        grid.addRow(row);
        System.out.println(row);
      }
    } catch (FileNotFoundException e) {
      System.err.println("Could not find file to read");
    }

    for (var y = 0; y < grid.getHeight(); y++) {
      for (var x = 0; x < grid.getWidth(); x++) {
        for (var step : steps) {
          var sb = new StringBuilder();
          var currentPoint = new Point(x, y);
          while (sb.length() < 4 && grid.contains(currentPoint)) {
            sb.append(grid.get(currentPoint));
            currentPoint.apply(step);
          }
          var word = sb.toString();
          if (word.equals("XMAS")) {
            System.out.printf("Found XMAS starting at %d,%d%n", x, y);
            answer++;
          }
        }
      }
    }

    System.out.println(answer);
  }
}

// -4,-4
// -4,0
// -4,4
// 0,-4
// 0,4
// 4,-4
// 4,0
// 4,4

class Grid {
  private List<List<Character>> rows;
  private int width;
  private int height;

  public Grid() {
    this.rows = new ArrayList<>();
  }

  public Character get(Point p) {
    return this.rows.get(p.getY()).get(p.getX());
  }

  public void addRow(List<Character> row) {
    this.rows.add(row);
    this.height++;
    if (this.width < row.size()) {
      this.width = row.size();
    }
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public boolean contains(Point p) {
    return (p.getX() >= 0 && p.getX() < this.getWidth()) && (p.getY() >= 0 && p.getY() < this.getHeight());
  }
}

class Point {
  private int x;
  private int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return this.x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return this.y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void apply(Point p) {
    this.x += p.getX();
    this.y += p.getY();
  }
}

