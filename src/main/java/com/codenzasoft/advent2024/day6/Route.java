package com.codenzasoft.advent2024.day6;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Route {

  private final List<Step> steps = new ArrayList<>();
  private boolean isCycle = false;

  public boolean addStep(Step step) {
    if (steps.contains(step)) {
      isCycle = true;
      return false;
    }
    steps.add(step);
    return true;
  }

  public boolean isCycle() {
    return isCycle;
  }

  public List<Step> getSteps() {
    return steps;
  }

  public Step getLastStep() {
    return steps.getLast();
  }

  public List<LabPosition> uniquePositions() {
    return steps.stream().map(Step::position).distinct().toList();
  }

}
