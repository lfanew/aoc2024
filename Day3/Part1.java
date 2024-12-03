import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.regex.Pattern;

public class Part1 {
  public static void main(String[] args) {
    var file = new File("input.txt");
    long answer = 0;

    try (var scanner = new Scanner(file)) {
      var pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
      while (scanner.hasNext()) {
        var line = scanner.nextLine();
        var matcher = pattern.matcher(line);

        while (matcher.find()) {
          var op1 = Long.parseLong(matcher.group(1));
          var op2 = Long.parseLong(matcher.group(2));
          answer += op1 * op2;
        }
      }
    } catch (FileNotFoundException e) {
      System.err.println("Could not find file to read");
    }

    System.out.println(answer);
  }
}
