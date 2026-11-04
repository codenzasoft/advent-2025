package com.codenzasoft.advent2024.day2;

import java.util.ArrayList;
import java.util.List;

public record Report(List<Long> levels) {

  public static Long MAX_DIFFERENCE = 3L;

  public boolean isSafe() {
    return (isDecreasing() || isIncreasing()) && isWithinMaxDiff();
  }

  public boolean isTolerable() {
    if (isSafe()) {
      return true;
    }
    return dampenedReports().stream().anyMatch(Report::isSafe);
  }

  public boolean isWithinMaxDiff() {
    for (int i = 0; i < levels.size() - 1; i++) {
      if (Math.abs(levels.get(i) - levels.get(i + 1)) > MAX_DIFFERENCE) {
        return false;
      }
    }
    return true;
  }

  public boolean isDecreasing() {
    for (int i = 0; i < levels.size() - 1; i++) {
      if (levels.get(i) <= levels.get(i + 1)) {
        return false;
      }
    }
    return true;
  }

  public boolean isIncreasing() {
    for (int i = 0; i < levels.size() - 1; i++) {
      if (levels.get(i) >= levels.get(i + 1)) {
        return false;
      }
    }
    return true;
  }

  public List<Report> dampenedReports() {
    List<Report> dampenedReports = new ArrayList<>();
    for (int i = 0; i < levels.size(); i++) {
      final List<Long> dampenedLevels = new ArrayList<>(levels);
      dampenedLevels.remove(i);
      dampenedReports.add(new Report(dampenedLevels));
    }
    return dampenedReports;
  }
}
