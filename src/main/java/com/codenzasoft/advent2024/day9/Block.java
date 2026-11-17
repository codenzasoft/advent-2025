package com.codenzasoft.advent2024.day9;

public interface Block {

  boolean isFile();
  boolean isFree();
  int length();
  long fileId();
}
