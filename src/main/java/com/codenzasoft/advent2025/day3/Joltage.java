package com.codenzasoft.advent2025.day3;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.List;

public class Joltage {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("input-day-3.txt");
    System.out.println("The sum of 2 digit joltages is: " + new Joltage().getJoltageSum(lines, 2));
    System.out.println(
        "The sum of 12 digit joltages is: " + new Joltage().getJoltageSum(lines, 12));
  }

  public long getJoltageSum(final List<String> ratings, final int numDigits) {
    return ratings.stream().map(BatteryBank::new).mapToLong(b -> b.getMaxJoltage(numDigits)).sum();
  }
}
