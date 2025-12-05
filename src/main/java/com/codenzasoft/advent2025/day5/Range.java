package com.codenzasoft.advent2025.day5;

import java.util.Optional;
import java.util.stream.LongStream;

public record Range(long start, long end) {

  public static Range parse(final String input) {
    String[] split = input.split("-");
    return new Range(Long.parseLong(split[0]), Long.parseLong(split[1]));
  }

  public boolean contains(final long value) {
    return value >= start && value <= end;
  }

  public boolean isGreaterThan(final long value) {
    return value < start;
  }

  public boolean overlaps(final Range other) {
    return contains(other.start()) || contains(other.end());
  }

  public Optional<Range> combine(final Range other) {
    if (overlaps(other)) {
      return Optional.of(
          new Range(Math.min(this.start(), other.start()), Math.max(this.end(), other.end())));
    }
    return Optional.empty();
  }

  public long size() {
    return end() - start() + 1;
  }
}
