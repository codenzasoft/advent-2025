package com.codenzasoft.advent2025.day4;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public record Grid(List<List<GridEntry>> rows) {

  /**
   * Builds a grid from a set of string representing grid entries. An entry can be
   * empty (<code>.</code>) or contain a roll of paper (<code>@</code>).
   */
  public static class GridBuilder {
    private final List<String> rows = new ArrayList<>();
    public GridBuilder addRow(final String row) {
      rows.add(row);
      return this;
    }
    public GridBuilder withRows(final List<String> rows) {
      this.rows.clear();
      rows.forEach(this::addRow);
      return this;
    }
    public Grid build() {
      return new Grid( rows.stream().map(row ->
        row.chars().mapToObj(c -> new GridEntry((char)c)).toList()
      ).toList());
    }
  }

  /**
   * Returns the 8 positions adjacent to the provided position.
   *
   * @param row zero based row index
   * @param col zero based column index
   * @return A list of adjacent positions
   */
  public List<Position> getAdjacentPositions(final int row, final int col) {
    return List.of(
        new Position(row -1, col -1),
        new Position(row -1, col),
        new Position(row -1, col + 1),
        new Position(row, col -1),
        new Position(row, col + 1),
        new Position(row + 1, col -1),
        new Position(row + 1, col),
        new Position(row + 1, col + 1)
    );
  }

  /**
   * Returns the {@link GridEntry}s adjacent to the provided position that contain the provided
   * character.
   *
   * @param position A {@link Position} in this {@link Grid}
   * @return The {@link GridEntry}s adjacent to the provided position that contain the provided
   * character.
   */
  public List<GridEntry> getMatchingAdjacentPositions(final Position position, final char c) {
    return getAdjacentPositions(position.row(), position.column()).stream().map(this::getEntry)
        .filter(Optional::isPresent).map(Optional::get).filter(e -> e.value() == c).toList();
  }

  /**
   * Returns the {@link GridEntry} at the specified position, or <code>empty</code> if none (not within this grid).
   *
   * @param position A {@link Position}
   * @return the {@link GridEntry} at the specified position, or <code>empty</code> if not contained in the grid.
   */
  public Optional<GridEntry> getEntry(final Position position) {
    if (position.row() < 0 || position.row() >= getRowCount()) return Optional.empty();
    if (position.column() < 0 || position.column() >= getColumnCount()) return Optional.empty();
    return Optional.of(rows.get(position.row()).get(position.column()));
  }

  public int getRowCount() {
    return rows.size();
  }
  public int getColumnCount() {
    return (rows.isEmpty()) ?  0 : rows.get(0).size();
  }

  /**
   * Returns the {@link GridEntry}s with the provided value that have fewer adjacent entries than the provided threshold
   * containing the specified matching value.
   *
   * @param entryValue {@link GridEntry}s with this value will be inspected
   * @param matchingValue Adjacent {@link GridEntry} with this value will be selected
   * @param threshold The value to test "less than" against
   * @return the {@link GridEntry}s that have fewer adjacent entries with the provided value (less than the
   * given threshold)
   *
   */
  public List<GridEntry> getEntriesWithFewerAdjacentValues(final char entryValue, final char matchingValue, final int threshold) {
    List<GridEntry> result = new ArrayList<>();
    for (int row = 0; row < getRowCount(); row++) {
      for (int column = 0; column < getColumnCount(); column++) {
        final Position position = new Position(row, column);
        final Optional<GridEntry> optional = getEntry(position);
        if (optional.isPresent() && optional.get().value() == entryValue) {
          final int count = getMatchingAdjacentPositions(position, matchingValue).size();
          if (count < threshold) {
            result.add(optional.get());
          }
        }
      }
    }
    return result;
  }

  public int partOne() {
    return getEntriesWithFewerAdjacentValues(GridEntry.PAPER_ROLL, GridEntry.PAPER_ROLL,4).size();
  }
}
