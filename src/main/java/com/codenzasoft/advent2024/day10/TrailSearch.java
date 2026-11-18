package com.codenzasoft.advent2024.day10;

import com.codenzasoft.advent2024.day6.Position;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrailSearch {

  public int calculateScore(final TopoMap map) {
    return map.getTrailheads().stream().mapToInt(th -> calculateScore(map, th)).sum();
  }

  public int calculateScore(final TopoMap map, final Position trailhead) {
    final Set<Position> summits = new HashSet<>();
    findSummits(map, trailhead, summits);
    return summits.size();
  }

  public void findSummits(final TopoMap map, final Position position, final Set<Position> summits) {
    if (map.isSummit(position)) {
      summits.add(position);
      return;
    }
    final List<Position> candidates = map.getAdjacentOnTrail(position);
    for (Position candidate : candidates) {
      findSummits(map, candidate, summits);
    }
  }
}
