package com.codenzasoft.advent2025.day4;

import com.codenzasoft.advent2025.PuzzleInput;
import com.codenzasoft.advent2025.day3.Joltage;

import java.util.List;

public class RollFinder {

  public static void main(String[] args) {
    List<String> lines = PuzzleInput.getInputLines("input-day-4.txt");
    System.out.println("The number of paper rolls for part one is: " + new Grid.GridBuilder().withRows(lines).build().partOne());
  }
}
