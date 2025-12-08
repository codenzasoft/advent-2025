package com.codenzasoft.advent2025.day7;

import java.awt.*;
import java.util.List;

public record Manifold(List<String> rows) {

  public Point findEntrance() {
    String row = rows.get(0);
    return new Point(row.indexOf('S'), 0);
  }

  public boolean isSplitter(final Point location) {
    String row = rows.get(location.y);
    return (row.charAt(location.x) == '^');
  }

  public int getRowCount() {
    return rows.size();
  }

  public int getColumnCount() {
    return rows.get(0).length();
  }
}
