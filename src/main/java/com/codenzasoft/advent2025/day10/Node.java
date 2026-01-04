package com.codenzasoft.advent2025.day10;

import java.util.ArrayList;
import java.util.List;

public class Node {

  private final Vector vector;
  private final int count;

  public Node(final Vector vector, final int count) {
    this.vector = vector;
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  public Vector getVector() {
    return vector;
  }

  public List<Node> buildChildren(final Matrix matrix, final Vector solution) {
    final List<Node> children = new ArrayList<>();
    if (!getVector().equals(solution)) {
      for (final Vector v : matrix.rows()) {
        final Vector sum = getVector().add(v);
        if (!sum.greaterThan(solution)) {
          children.add(new Node(sum, count + 1));
        }
      }
    }
    return children;
  }
}
