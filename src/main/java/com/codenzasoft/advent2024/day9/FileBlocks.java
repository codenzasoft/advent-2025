package com.codenzasoft.advent2024.day9;

public record FileBlocks(int index, long fileId, int length) implements Block {
  @Override
  public boolean isFile() {
    return true;
  }

  @Override
  public boolean isFree() {
    return false;
  }
}
