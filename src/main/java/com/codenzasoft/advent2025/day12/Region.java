package com.codenzasoft.advent2025.day12;

import java.util.ArrayList;
import java.util.List;

public record Region(int width, int height, List<Integer> quantities) {

  public static Region parse(String input) {
    int colon = input.indexOf(':');
    String sizing = input.substring(0, colon);
    String quant = input.substring(colon + 2);
    String[] sizes = sizing.split("x");
    int width = Integer.parseInt(sizes[0]);
    int height = Integer.parseInt(sizes[1]);
    List<Integer> quantities = new ArrayList<>();
    String[] quants = quant.split(" ");
    for (String s : quants) {
      quantities.add(Integer.parseInt(s));
    }
    return new Region(width, height, quantities);
  }

  public Grid emptyGrid() {
    return Grid.empty(height(), width());
  }

  public List<Present> getRequiredQuantities(final List<Present> presents) {
    final List<Present> requiredQuantities = new ArrayList<>();
    for (int i = 0; i < quantities.size(); i++) {
      int quantity = quantities.get(i);
      if (quantity > 0) {
        for (int j = 0; j < quantity; j++) {
          final Present present = presents.get(i);
          if (!Integer.toString(i).equals(present.id())) {
            throw new IllegalStateException("unexpected present id");
          }
          requiredQuantities.add(presents.get(i));
        }
      }
    }
    return requiredQuantities.stream().toList();
  }

  public boolean fitPermutation(final List<Present> presents) {
    Grid grid = emptyGrid();
    for (final Present present : presents) {
      final List<Position> available = grid.getEmptyPositions();
      boolean inserted = false;
      for (Position position : available) {
        if (grid.canInsert(present, position)) {
          grid = grid.insert(present, position);
          inserted = true;
          break;
        }
      }
      if (!inserted) {
        return false;
      }
    }
    return true;
  }
}
