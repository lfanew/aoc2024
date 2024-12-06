import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;


public class Part1 {
  public static void main(String[] args) {
    var file = new File("input.txt");
    Set<Point> obstacles = new HashSet<>();
    Set<Point> distinctPositions = new HashSet<>();
    Guard guard = null;
    int maxX = 0;
    int maxY = 0;
    try (var scanner = new Scanner(file)) {
      int y = 0;
      while (scanner.hasNext()) {
        var line = scanner.nextLine();
        int x = 0;
        for (var c : line.toCharArray()) {
          if (x > maxX) maxX = x;
          var point = new Point(x, y);
          switch (c) {
            case '#' -> obstacles.add(point);
            case '^','>','v','<' -> {
              guard = new Guard(point, Direction.parseDirection(c));
            }
            default -> {}
          }
          x++;
        }
        if (y > maxY) maxY = y;
        y++;
      }
    } catch (FileNotFoundException e) {
      System.err.println("Could not find file to read");
    }

    while (guard.getPosition().isInBounds(maxX, maxY)) {
      distinctPositions.add(guard.getPosition());
      var nextPosition = guard.getNextPosition();
      if (obstacles.contains(nextPosition)) {
        guard.rotate();
      } else {
        guard.setPosition(nextPosition);
      }
    }

    System.out.println(distinctPositions.size());
  }
}

class Guard {
  private Point position;
  private Direction direction;

  public Guard(Point position, Direction direction) {
    this.position = position;
    this.direction = direction;
  }

  public Point getPosition() {
    return this.position;
  }

  public void setPosition(Point position) {
    this.position = position;
  }

  public void rotate() {
    var directionCount = Direction.values().length;
    var nextDirection = Direction.values()[(direction.ordinal() + 1) % directionCount];
    this.direction = nextDirection;
  }

  public Point getNextPosition() {
    var delta = switch (this.direction) {
      case NORTH  -> new Point(0, -1);
      case EAST   -> new Point(1, 0);
      case SOUTH  -> new Point(0, 1);
      case WEST   -> new Point(-1, 0);
    };

    return position.apply(delta);
  }

  @Override
  public String toString() {
    return String.format("Guard[%s, %s]", this.position, this.direction);
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

  public int getY() {
    return this.y;
  }

  public Point apply(Point other) {
    var x = this.x + other.x;
    var y = this.y + other.y;

    return new Point(x, y);
  }

  public boolean isInBounds(int maxX, int maxY) {
    return this.x >= 0 && this.x <= maxX && this.y >= 0 && this.y <= maxY;
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Point other) &&
      x == other.x && y == other.y;
  }

  @Override
  public int hashCode() {
    // not ideal hashing for a point, shouldn't matter
    return x * y;
  }

  @Override
  public String toString() {
    return String.format("(%d,%d)", this.x, this.y);
  }
}

enum Direction {
  NORTH,
  EAST,
  SOUTH,
  WEST;

  public static Direction parseDirection(Character c) {
    return switch (c) {
      case '^' -> Direction.NORTH;
      case '>' -> Direction.EAST;
      case 'v' -> Direction.SOUTH;
      case '<' -> Direction.WEST;
      default -> Direction.NORTH; 
    };
  }
}
