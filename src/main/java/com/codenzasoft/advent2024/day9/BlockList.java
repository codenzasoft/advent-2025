package com.codenzasoft.advent2024.day9;

import java.util.*;
import java.util.stream.Collectors;

public class BlockList {

  private final LinkedList<Block> blocks;
  private final Map<Integer, TreeSet<Block>> freeBlocks;

  public BlockList(final LinkedList<Block> blocks) {
    this.blocks = blocks;
    final Map<Integer, List<Block>> temp =
        blocks.stream().filter(Block::isFree).collect(Collectors.groupingBy(Block::length));
    freeBlocks = new HashMap<>(temp.size());
    for (Map.Entry<Integer, List<Block>> entry : temp.entrySet()) {
      freeBlocks.put(entry.getKey(), new TreeSet<>(entry.getValue()));
    }
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
        blocks.add(new FileBlocks(index, id, size));
        for (int j = 0; j < size; j++) {
          index++;
        }
        id++;
      } else {
        // free space
        if (size > 0) {
          blocks.add(new FreeBlocks(index, size));
          index += size;
        }
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
      final TreeSet<Block> candidatesOfLength = findLeftMostFreeBlocksOfMinLength(file.length());
      if (candidatesOfLength != null) {
        final Block candidate = candidatesOfLength.first();
        if (candidate.index() < file.index()) {
          final Block free = candidatesOfLength.removeFirst();
          blocks.remove(fileIndex);
          // replace the file with free space
          blocks.add(fileIndex, new FreeBlocks(file.index(), file.length()));
          // replace the free block with the file and any leftover free space
          final int freeIndex = blocks.indexOf(free);
          blocks.remove(freeIndex);
          blocks.add(freeIndex, new FileBlocks(free.index(), file.fileId(), file.length()));
          if (free.length() > file.length()) {
            final FreeBlocks leftOver =
                new FreeBlocks(free.index() + file.length(), free.length() - file.length());
            blocks.add(freeIndex + 1, leftOver);
            addFreeBlock(leftOver);
            fileIndex++;
          }
        }
      }
      fileIndex--;
      fileIndex = findIndexOfPrevFile(fileIndex);
    }
  }

  private TreeSet<Block> findLeftMostFreeBlocksOfMinLength(final int length) {
    int size = length;
    TreeSet<Block> candidates = null;
    int leftMostIndex = Integer.MAX_VALUE;
    while (size < 10) {
      TreeSet<Block> next = freeBlocks.get(size);
      if (next != null && !next.isEmpty()) {
        final Block peek = next.first();
        if (peek.index() < leftMostIndex) {
          leftMostIndex = peek.index();
          candidates = next;
        }
      }
      size++;
    }
    return candidates;
  }

  private void addFreeBlock(final FreeBlocks free) {
    final TreeSet<Block> blocksOfSize =
        freeBlocks.computeIfAbsent(free.length(), k -> new TreeSet<>());
    blocksOfSize.add(free);
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
