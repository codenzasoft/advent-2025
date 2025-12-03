package com.codenzasoft.advent2025.day3;

import com.codenzasoft.advent2025.PuzzleInput;
import java.util.List;

public class Joltage {

  public static void main(String[] args) {
    List<String> lines = PuzzleInput.getInputLines("input-day-3.txt");
    System.out.println(
        "The sum of double digit joltages is: " + new Joltage().getDoulbeDigitJoltageSum(lines));
  }

  public int getDoulbeDigitJoltageSum(final List<String> ratings) {
    return ratings.stream().map(BatteryBank::new).mapToInt(BatteryBank::getMaxJoltage).sum();
  }
}
