package com.codenzasoft.advent2024.day6;

public abstract class GridEntry {

  public abstract boolean isObstacle();

  public boolean isVisit() {
    return !isObstacle();
  }

  public abstract char getSymbol();
}
