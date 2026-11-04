package com.codenzasoft.advent2024.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MulScanner {

  public static final Pattern MULTIPLICATION_REGEX =
      Pattern.compile("mul\\((?<x>\\d+),(?<y>\\d+)\\)");

  public static final Pattern MULTIPLICATION_DO_OR_DONT_REGEX =
      Pattern.compile("mul\\((?<x>\\d+),(?<y>\\d+)\\)|(?<do>do\\(\\))|(?<dont>don't\\(\\))");

  public List<Multiplication> scan(List<String> input) {
    List<Multiplication> multiplications = new ArrayList<>();
    for (String line : input) {
      Matcher matcher = MULTIPLICATION_REGEX.matcher(line);
      while (matcher.find()) {
        final long x = Long.parseLong(matcher.group("x"));
        final long y = Long.parseLong(matcher.group("y"));
        multiplications.add(new Multiplication(x, y));
      }
    }
    return multiplications;
  }

  public List<Multiplication> scanEnabled(List<String> input) {
    List<Multiplication> multiplications = new ArrayList<>();
    boolean enabled = true;
    for (String line : input) {
      Matcher matcher = MULTIPLICATION_DO_OR_DONT_REGEX.matcher(line);
      while (matcher.find()) {
        if (matcher.group("do") != null) {
          enabled = true;
        } else if (matcher.group("dont") != null) {
          enabled = false;
        } else if (enabled) {
          final long x = Long.parseLong(matcher.group("x"));
          final long y = Long.parseLong(matcher.group("y"));
          multiplications.add(new Multiplication(x, y));
        }
      }
    }
    return multiplications;
  }
}
