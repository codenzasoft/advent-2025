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

  public void compressFiles() {
    int endOfFileIndex = lastFileIndex;
    long fileId = blocks[endOfFileIndex];
    int beginOfFileIndex = seekBeginOffsetOfFile(fileId, endOfFileIndex);
    while (beginOfFileIndex > 0) {
      int fileSize = endOfFileIndex - beginOfFileIndex + 1;
    }
  }

  private int seekBeginOffsetOfFile(final long fileId, final int endOfFileIndex) {
    int prevIndex = endOfFileIndex - 1;
    while (blocks[prevIndex] == fileId) {
      prevIndex--;
    }
    return prevIndex + 1;
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

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Long block : blocks) {
      if (block != null) {
        sb.append(block);
      } else {
        sb.append(".");
      }
    }
    return sb.toString();
  }

  public static List<Integer> parseBlockSizes(String input) {
    final List<Integer> blockSizes = new ArrayList<>();
    for (int i = 0; i < input.length(); i++) {
      final char c = input.charAt(i);
      final Integer value = Integer.parseInt(String.valueOf(c));
      blockSizes.add(value);
    }
    return blockSizes;
  }

  public static BlockMap parse(String input) {
    final List<Integer> blockSizes = parseBlockSizes(input);
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
