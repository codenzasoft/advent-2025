package com.codenzasoft.advent2024.day3;

import com.codenzasoft.advent2024.day2.Report;
import com.codenzasoft.advent2025.PuzzleHelper;

import java.util.ArrayList;
import java.util.List;

public class Day3 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-3.txt");
    System.out.println("The decoded sum is: " + new Day3().scanCorruptedMemory(lines));
    System.out.println("The enabled sum is: " + new Day3().scanEnabledCorruptedMemory(lines));
  }

  public long scanCorruptedMemory(List<String> lines) {
    final List<Multiplication> multiplications = new MulScanner().scan(lines);
    return multiplications.stream().mapToLong(Multiplication::value).sum();
  }

  public long scanEnabledCorruptedMemory(List<String> lines) {
    final List<Multiplication> multiplications = new MulScanner().scanEnabled(lines);
    return multiplications.stream().mapToLong(Multiplication::value).sum();
  }

}
