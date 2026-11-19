package com.codenzasoft.advent2024.day11;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StoneBlinkCache {

  private final Map<StoneBlinkCacheKey, Long> cache = new HashMap<>();

  public void cache(Stone stone, int blinksRemaining, long value) {
    cache.putIfAbsent(StoneBlinkCacheKey.with(stone.number(), blinksRemaining), value);
  }

  public Optional<Long> get(Stone stone, int blinksRemaining) {
    return Optional.ofNullable(cache.get(StoneBlinkCacheKey.with(stone.number(), blinksRemaining)));
  }
}
