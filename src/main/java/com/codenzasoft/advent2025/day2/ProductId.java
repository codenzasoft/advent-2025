package com.codenzasoft.advent2025.day2;

/**
 * A numeric product identifier.
 *
 * @param value The product ID
 */
public record ProductId(long value) {

  /**
   * Returns whether this {@link ProductId} is valid. Any ID which is made only of some sequence of
   * digits repeated twice is invalid. For example, 55 (5 twice), 6464 (64 twice), and 123123 (123
   * twice) are all invalid.
   *
   * @return Whether this {@link ProductId} is valid.
   */
  public boolean isValid() {
    final String digits = Long.toString(value);
    if (digits.length() % 2 == 0) {
      final int half = digits.length() / 2;
      final String highOrder = digits.substring(0, half);
      final String lowOrder = digits.substring(half);
      return !highOrder.equals(lowOrder);
    }
    return true;
  }
}
