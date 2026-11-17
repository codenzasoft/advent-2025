package com.codenzasoft.advent2024.day9;

public record FreeBlocks(int index, int length) implements Block {
  @Override
  public boolean isFile() {
    return false;
  }

  @Override
  public boolean isFree() {
    return true;
  }

  @Override
  public long fileId() {
    return -1;
  }
}
