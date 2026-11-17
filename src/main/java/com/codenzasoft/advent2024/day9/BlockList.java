package com.codenzasoft.advent2024.day9;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BlockList {

  private final LinkedList<Block> blocks;

  public BlockList(final LinkedList<Block> blocks) {
    this.blocks = blocks;
  }

  public static BlockList parse(String input) {
    final List<Integer> blockSizes = BlockMap.parseBlockSizes(input);
    final LinkedList<Block> blocks = new LinkedList<>();
    int index = 0;
    long id = 0L;
    for (int i = 0; i < blockSizes.size(); i++) {
      final int size = blockSizes.get(i);
      if (i % 2 == 0) {
        // file
        blocks.add(new FileBlocks(id, size));
        for (int j = 0; j < size; j++) {
          index++;
        }
        id++;
      } else {
        // free space
        blocks.add(new FreeBlocks(size));
        index += size;
      }
    }
    return new BlockList(blocks);
  }

  public void compress() {
    int fileIndex = findIndexOfPrevFile(blocks.size());
    while (fileIndex > 0) {
      Block file = blocks.get(fileIndex);
      if (file.fileId() % 100 == 0) {
        System.out.println("Working on file: " + file.fileId());
      }
      final int freeIndex = findIndexOfFreeSpace(fileIndex, file.length());
      if (freeIndex > -1) {
        Block free = blocks.get(freeIndex);
        blocks.remove(fileIndex);
        blocks.add(fileIndex, new FreeBlocks(file.length()));
        blocks.remove(freeIndex);
        blocks.add(freeIndex, file);
        if (free.length() > file.length()) {
          blocks.add(freeIndex + 1, new FreeBlocks(free.length() - file.length()));
          fileIndex--;
        }
      }
      fileIndex--;
      fileIndex = findIndexOfPrevFile(fileIndex);
    }
  }

  public BlockMap toBlockMap() {
    final int totalBlocks = blocks.stream().mapToInt(Block::length).sum();
    final Long[] blockIds = new Long[totalBlocks];
    int index = 0;
    for (Block block : blocks) {
      if (block.isFile()) {
        for (int i = 0; i < block.length(); i++) {
          blockIds[index] = block.fileId();
          index++;
        }
      } else {
        index += block.length();
      }
    }
    return new BlockMap(blockIds);
  }

  private int findIndexOfPrevFile(int offset) {
    int index = offset;
    if (index >= blocks.size()) {
      index = blocks.size() - 1;
    }
    while (!blocks.get(index).isFile()) {
      index--;
    }
    return index;
  }

  private int findIndexOfFreeSpace(int beforeIndex, int length) {
    for (int i = 0; i < beforeIndex; i++) {
      if (blocks.get(i).isFree()) {
        if (blocks.get(i).length() >= length) {
          return i;
        }
      }
    }
    return -1;
  }
}
