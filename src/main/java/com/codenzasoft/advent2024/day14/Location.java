package com.codenzasoft.advent2024.day14;

public record Location(int x, int y) implements Comparable<Location> {

  public static Location parse(String input) {
    final String[] coordinates = input.substring(2).split(",");
    return new Location(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
  }

  public Location move(Velocity velocity, Grid grid) {
    int nextX = wrap(x + velocity.x(), grid.width());
    int nextY = wrap(y + velocity.y(), grid.height());
    return new Location(nextX, nextY);
  }

  private int wrap(final int value, final int limit) {
    if (value > limit - 1) {
      return value % limit;
    }
    if (value < 0) {
      return limit + value;
    }
    return value;
  }

  @Override
  public int compareTo(final Location o) {
    if (this.y() == o.y()) {
      return Integer.compare(this.x(), o.x());
    }
    return Integer.compare(this.y(), o.y());
  }

  public Location up() {
    return new Location(this.x(), this.y() - 1);
  }

  public Location down() {
    return new Location(this.x(), this.y() + 1);
  }

  public Location left() {
    return new Location(this.x() - 1, this.y());
  }

  public Location right() {
    return new Location(this.x() + 1, this.y());
  }
}
