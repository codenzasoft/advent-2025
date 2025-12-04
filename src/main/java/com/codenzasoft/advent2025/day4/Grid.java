package com.codenzasoft.advent2025.day4;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record Grid(List<List<GridEntry>> rows) {

  /**
   * Builds a {@link Grid} from {@link String}s, where each string represents a row of grid entries.
   * An individual entry can be empty (<code>.</code>) or contain a roll of paper (<code>@</code>).
   * A string contains N characters, each identifying the contents of the column at the
   * corresponding offset.
   */
  public static class GridBuilder {
    private final List<String> rows = new ArrayList<>();

    /**
     * Appends a row to the {@link Grid} being built.
     *
     * @param row A {@link String} where each character identifies the content of each column.
     * @return This {@link GridBuilder} for chaining.
     */
    public GridBuilder addRow(final String row) {
      rows.add(row);
      if (rows.size() > 1) {
        if (rows.get(0).length() != row.length()) {
          throw new IllegalArgumentException("All rows must have the same number of columns");
        }
      }
      return this;
    }

    /**
     * Clears and sets the rows for the {@link Grid} being built.
     *
     * @param rows A new collection of rows for a {@link Grid}, where each character in a row
     *     identifies the content of each column.
     * @return This {@link GridBuilder} for chaining.
     */
    public GridBuilder withRows(final List<String> rows) {
      this.rows.clear();
      rows.forEach(this::addRow);
      return this;
    }

    /**
     * Marks the entry at the specified {@link Position} as removed.
     *
     * @param position A {@link Position} to mark removed.
     * @return This {@link GridBuilder} for chaining.
     */
    public GridBuilder removeEntry(final Position position) {
      if (position.row() >= rows.size()) {
        throw new IllegalArgumentException("Row index out of bounds");
      }
      String row = rows.get(position.row());
      if (position.column() >= row.length()) {
        throw new IllegalArgumentException("Column index out of bounds");
      }
      StringBuilder next = new StringBuilder();
      for (int col = 0; col < row.length(); col++) {
        if (col == position.column()) {
          next.append(GridEntry.REMOVED);
        } else {
          next.append(row.charAt(col));
        }
      }
      rows.set(position.row(), next.toString());
      return this;
    }

    /**
     * Builds and returns a {@link Grid} based on the rows that have been provided to this {@link
     * GridBuilder}.
     *
     * @return A {@link Grid}
     */
    public Grid build() {
      final List<List<GridEntry>> theRows = new ArrayList<>();
      for (int row = 0; row < rows.size(); row++) {
        final String chars = rows.get(row);
        final List<GridEntry> theRow = new ArrayList<>();
        for (int col = 0; col < chars.length(); col++) {
          final GridEntry entry = new GridEntry(chars.charAt(col), new Position(row, col));
          theRow.add(entry);
        }
        theRows.add(theRow);
      }
      return new Grid(theRows);
    }
  }

  /**
   * Returns the 8 positions adjacent to the provided position. Note that the {@link Position}s are
   * not verified to exist in this {@link Grid}.
   *
   * @param position A {@link Position} from which to generate adjacent {@link Position}s.
   * @return A list of adjacent {@link Position}s.
   */
  public List<Position> getAdjacentPositions(final Position position) {
    final int row = position.row();
    final int col = position.column();
    return List.of(
        new Position(row - 1, col - 1),
        new Position(row - 1, col),
        new Position(row - 1, col + 1),
        new Position(row, col - 1),
        new Position(row, col + 1),
        new Position(row + 1, col - 1),
        new Position(row + 1, col),
        new Position(row + 1, col + 1));
  }

  /**
   * Returns the {@link GridEntry}s adjacent to the provided position that contain the provided
   * character.
   *
   * @param position A {@link Position} in this {@link Grid}
   * @return The {@link GridEntry}s adjacent to the provided position that contain the provided
   *     character.
   */
  public List<GridEntry> getMatchingAdjacentPositions(final Position position, final char c) {
    return getAdjacentPositions(position).stream()
        .map(this::getEntry)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .filter(e -> e.value() == c)
        .toList();
  }

  /**
   * Returns the {@link GridEntry} at the specified {@link Position}, or <code>empty</code> if none
   * (not within this {@link Grid}).
   *
   * @param position A {@link Position}
   * @return the {@link GridEntry} at the specified position, or <code>empty</code> if not contained
   *     in the grid.
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
    return (rows.isEmpty()) ? 0 : rows.get(0).size();
  }

  /**
   * Returns the {@link GridEntry}s with the provided value that have fewer adjacent entries than
   * the provided threshold containing the specified matching value.
   *
   * @param entryValue {@link GridEntry}s with this value will be inspected
   * @param matchingValue Adjacent {@link GridEntry} with this value will be selected
   * @param threshold The value to test "less than" against
   * @return the {@link GridEntry}s that have fewer adjacent entries with the provided value (less
   *     than the given threshold)
   */
  public List<GridEntry> getEntriesWithFewerAdjacentValues(
      final char entryValue, final char matchingValue, final int threshold) {
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

  public List<String> getRowStrings() {
    List<String> result = new ArrayList<>();
    for (int row = 0; row < getRowCount(); row++) {
      final StringBuilder rowBuilder = new StringBuilder();
      for (int column = 0; column < getColumnCount(); column++) {
        rowBuilder.append(rows.get(row).get(column).value());
      }
      result.add(rowBuilder.toString());
    }
    return result;
  }
}
