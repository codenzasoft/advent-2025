package com.codenzasoft.advent2024.day11;

public record StoneBlinkCacheKey(long stoneValue, int blinksRemaining) {

  public static StoneBlinkCacheKey with(long stoneValue, int blinksRemaining) {
    return new StoneBlinkCacheKey(stoneValue, blinksRemaining);
  }
}
