package com.codenzasoft.advent2025.day2;

import java.util.ArrayList;
import java.util.List;

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
  public boolean isMirrorSequence() {
    final String digits = Long.toString(value);
    if (digits.length() % 2 == 0) {
      final int half = digits.length() / 2;
      final String highOrder = digits.substring(0, half);
      final String lowOrder = digits.substring(half);
      return highOrder.equals(lowOrder);
    }
    return false;
  }

  public boolean isRepeatedSequence() {
    final String digits = Long.toString(value);
    int size = 1;
    while (size <= digits.length() / 2) {
      final String sequence = digits.substring(0, size);
      if (isSequenceOf(sequence)) {
        return true;
      }
      size++;
    }
    return false;
  }

  public boolean isSequenceOf(final String sequence) {
    final String digits = Long.toString(value);
    if (digits.length() % sequence.length() == 0) {
      int offset = 0;
      final List<String> candidates = new ArrayList<>();
      while (offset < digits.length()) {
        candidates.add(digits.substring(offset, offset + sequence.length()));
        offset += sequence.length();
      }
      return candidates.stream().allMatch(c -> c.equals(sequence));
    }
    return false;
  }
}
