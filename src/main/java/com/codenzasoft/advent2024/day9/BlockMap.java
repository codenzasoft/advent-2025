package com.codenzasoft.advent2024.day9;

import java.util.ArrayList;
import java.util.List;

public class BlockMap {

  private final Long[] blocks;
  int freeSpaceIndex = 0;
  int lastFileIndex = 0;

  public BlockMap(Long[] blocks) {
    this.blocks = blocks;
    this.lastFileIndex = blocks.length - 1;
    seek();
  }

  private void seek() {
    while (blocks[freeSpaceIndex] != null) {
      freeSpaceIndex++;
    }
    while (blocks[lastFileIndex] == null) {
      lastFileIndex--;
    }
  }

  public void compress() {
    while (freeSpaceIndex < lastFileIndex) {
      blocks[freeSpaceIndex] = blocks[lastFileIndex];
      blocks[lastFileIndex] = null;
      seek();
    }
  }

  public long checksum() {
    long checksum = 0;
    for (int i = 0; i < blocks.length; i++) {
      final Long value = blocks[i];
      if (value != null) {
        checksum += value * i;
      }
    }
    return checksum;
  }

  public static BlockMap parse(String input) {
    final List<Integer> blockSizes = new ArrayList<>();
    for (int i = 0; i < input.length(); i++) {
      final char c = input.charAt(i);
      final Integer value = Integer.parseInt(String.valueOf(c));
      blockSizes.add(value);
    }
    final int totalBlocks = blockSizes.stream().mapToInt(blockSize -> blockSize).sum();
    final Long[] blocks = new Long[totalBlocks];
    int index = 0;
    long id = 0L;
    for (int i = 0; i < blockSizes.size(); i++) {
      final int size = blockSizes.get(i);
      if (i % 2 == 0) {
        // file
        for (int j = 0; j < size; j++) {
          blocks[index] = id;
          index++;
        }
        id++;
      } else {
        // free space
        index += size;
      }
    }
    return new BlockMap(blocks);
  }
}
