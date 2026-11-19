package com.codenzasoft.advent2024.day11;

import java.util.List;
import java.util.Optional;

public record Stone(long number) {

  public List<Stone> blink() {
    // 1. Zero becomes 1.
    if (number == 0) return List.of(new Stone(1));

    // 2. If the stone is engraved with a number that has an even number of digits, it is replaced
    // by two stones
    final String digits = String.valueOf(number);
    if (digits.length() % 2 == 0) {
      // replaced with two stones
      String hiHalf = digits.substring(0, digits.length() / 2);
      String lowHalf = digits.substring(digits.length() / 2);
      return List.of(new Stone(Long.parseLong(hiHalf)), new Stone(Long.parseLong(lowHalf)));
    }

    // 3. The old stone's number multiplied by 2024
    return List.of(new Stone(number * 2024));
  }

  public long countStones(final int blinksRemaining, final StoneBlinkCache cache) {
    if (blinksRemaining == 0) {
      return 1;
    }
    final Optional<Long> cached = cache.get(this, blinksRemaining);
    if (cached.isPresent()) {
      return cached.get();
    }
    final List<Stone> children = blink();
    final long sum =
        children.stream().mapToLong(stone -> stone.countStones(blinksRemaining - 1, cache)).sum();
    cache.cache(this, blinksRemaining, sum);
    return sum;
  }
}
