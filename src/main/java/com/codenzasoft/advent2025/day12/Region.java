package com.codenzasoft.advent2025.day12;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.iterators.PermutationIterator;

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

  public boolean canFit(final List<Present> presents) {
    final List<Present> quantities = getRequiredQuantities(presents);

    // quick disqualification if not enough space
    final int presentUnits = quantities.stream().mapToInt(Present::getUnits).sum();
    if (presentUnits > (height * width)) {
      return false;
    }

    // rotationGroups contains the 4 possible rotations of each required present
    final List<List<Present>> rotationGroups =
        quantities.stream().map(Present::getRotations).toList();
    final GroupCombinationsIterator<Present> iterator =
        new GroupCombinationsIterator<>(rotationGroups);
    // iterate through all combinations of present rotations (order not important)
    while (iterator.hasNext()) {
      final List<Present> combination = iterator.next();
      // iterate through all permutations of the combination being attempted (order important)
      final PermutationIterator<Present> permutationIterator =
          new PermutationIterator<>(combination);
      while (permutationIterator.hasNext()) {
        final List<Present> permutation = permutationIterator.next();
        if (fitPermutation(permutation)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean fitPermutation(final List<Present> presents) {
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
