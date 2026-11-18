package com.codenzasoft.advent2024.day11;

import java.util.Arrays;
import java.util.List;

public record StoneList(List<Stone> stones) {

  public static StoneList parse(String input) {
    final String[] values = input.split("\\s+");
    return new StoneList(Arrays.stream(values).map(Long::parseLong).map(Stone::new).toList());
  }

  public StoneList blink() {
    return new StoneList(stones.stream().flatMap(stone -> stone.blink().stream()).toList());
  }
}
