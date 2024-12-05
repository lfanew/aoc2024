import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Part2 {
  private static Point[] steps = {
    new Point(-1, -1),
    new Point(1, 1),
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
      }
    } catch (FileNotFoundException e) {
      System.err.println("Could not find file to read");
    }

    for (var y = 1; y < grid.getHeight() - 1; y++) {
      for (var x = 1; x < grid.getWidth() - 1; x++) {
        var currentPoint = new Point(x, y);
        var passed = true;
        if (grid.get(currentPoint) == 'A') {
          ArrayList<Point> points = new ArrayList<>();
          for (var step : steps) {
            points.add(currentPoint.apply(step));
          }
          for (var i = 0; i < points.size(); i+=2) {
            var first = grid.get(points.get(i));
            var second = grid.get(points.get(i+1));
            if (!(first == 'M' && second == 'S') && !(first == 'S' && second == 'M')) {
              passed = false;
              break;
            }
          }
          if (passed) answer++;
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

  public Point apply(Point p) {
    return new Point(this.x + p.getX(), this.y + p.getY());
  }
}

