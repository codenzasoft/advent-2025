package com.codenzasoft.advent2024.day9;

public interface Block extends Comparable<Block> {

  boolean isFile();

  boolean isFree();

  int length();

  int index();

  long fileId();

  default int compareTo(Block o) {
    return Integer.compare(index(), o.index());
  }
}
