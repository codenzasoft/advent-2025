package com.codenzasoft.advent2025.day12;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.ArrayList;
import java.util.List;

public class Tetris {

  public static void main(String[] args) {
    final List<String> lines = PuzzleHelper.getInputLines("input-day-12.txt");
    final List<List<String>> groups = buildGroups(lines);
    final List<Present> presents = buildPresents(groups);
    final List<Region> regions = buildRegions(groups);
    System.out.println("The answer to part 1 is: " + part1(presents, regions));
  }

  public static int part1(final List<Present> presents, final List<Region> regions) {
    int fits = 0;
    for (final Region region : regions) {
      if (region.canFit(presents)) {
        fits++;
        System.out.println("Region " + region + " fits");
      } else {
        System.out.println("Region " + region + " does not fit");
      }
    }
    return fits;
  }

  public static List<List<String>> buildGroups(final List<String> lines) {
    final List<List<String>> groups = new ArrayList<>();
    List<String> group = new ArrayList<>();
    for (String line : lines) {
      if (line.isBlank()) {
        groups.add(group);
        group = new ArrayList<>();
      } else {
        group.add(line);
      }
    }
    if (!group.isEmpty()) {
      groups.add(group);
    }
    return groups;
  }

  public static List<Present> buildPresents(final List<List<String>> groups) {
    final List<Present> presents = new ArrayList<>();
    for (List<String> group : groups.subList(0, groups.size() - 1)) {
      presents.add(Present.parse(group));
    }
    return presents;
  }

  public static List<Region> buildRegions(final List<List<String>> groups) {
    final List<Region> regions = new ArrayList<>();
    for (String line : groups.get(groups.size() - 1)) {
      regions.add(Region.parse(line));
    }
    return regions;
  }
}
