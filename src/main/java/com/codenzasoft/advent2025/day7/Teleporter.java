package com.codenzasoft.advent2025.day7;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Teleporter {

  public static void main(String[] args) {
    final List<String> lines = PuzzleHelper.getInputLines("input-day-7.txt");
    System.out.println("The number of splits for part 1 is: " + traceBeams(new Manifold(lines)));
  }

  public static int traceBeams(final Manifold manifold) {
    List<Set<Point>> rows = new ArrayList<>();
    for (int i = 0; i < manifold.getRowCount(); i++) {
      rows.add(new HashSet<>());
    }
    final Point entrance = manifold.findEntrance();
    rows.get(entrance.y).add(entrance);
    int splits = 0;
    int rowIndex = 1;
    while (rowIndex < manifold.getRowCount()) {
      Set<Point> prevBeams = rows.get(rowIndex - 1);
      Set<Point> nextBeams = rows.get(rowIndex);
      for (Point beam : prevBeams) {
        if (manifold.isSplitter(new Point(beam.x, beam.y + 1))) {
          splits++;
          nextBeams.add(new Point(beam.x - 1, beam.y + 1));
          nextBeams.add(new Point(beam.x + 1, beam.y + 1));
        } else {
          nextBeams.add(new Point(beam.x, beam.y + 1));
        }
      }
      rowIndex++;
    }
    return splits;
  }
}
