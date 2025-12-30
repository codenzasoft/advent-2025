package com.codenzasoft.advent2025.day12;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GroupCombinationsIterator<E> implements Iterator<List<E>> {

  final List<List<E>> groups;
  final long[] indexes;

  public GroupCombinationsIterator(final List<List<E>> groups) {
    this.groups = groups;
    this.indexes = new long[groups.size()];
    for (int i = 0; i < groups.size(); i++) {
      indexes[i] = 0;
    }
  }

  @Override
  public boolean hasNext() {
    return indexes[0] < groups.get(0).size();
  }

  @Override
  public List<E> next() {
    final List<E> combination = new ArrayList<>();
    for (int i = 0; i < indexes.length; i++) {
      final List<E> group = groups.get(i);
      final int offset = (int) (indexes[i] % group.size());
      combination.add(groups.get(i).get(offset));
    }

    incrementIndex(groups.size() - 1);

    return combination;
  }

  private void incrementIndex(int groupIndex) {
    if (groupIndex >= 0) {
      indexes[groupIndex]++;
      final List<E> group = groups.get(groupIndex);
      if (indexes[groupIndex] % group.size() == 0) {
        incrementIndex(groupIndex - 1);
      }
    }
  }
}
