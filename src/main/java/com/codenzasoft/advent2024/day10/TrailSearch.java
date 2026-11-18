package com.codenzasoft.advent2024.day10;

import com.codenzasoft.advent2024.day6.Position;
import java.util.ArrayList;
import java.util.List;

public class TrailSearch {

  public long calculateDistinctSummits(final TopoMap map) {
    return map.getTrailheads().stream().mapToLong(th -> calculateDistinctSummits(map, th)).sum();
  }

  public long calculateDistinctSummits(final TopoMap map, final Position trailhead) {
    final List<Position> summits = new ArrayList<>();
    findSummits(map, trailhead, summits);
    return summits.stream().distinct().count();
  }

  public long calculateDistinctTrails(final TopoMap map) {
    return map.getTrailheads().stream().mapToLong(th -> calculateDistinctTrails(map, th)).sum();
  }

  public long calculateDistinctTrails(final TopoMap map, final Position trailhead) {
    final List<Position> summits = new ArrayList<>();
    findSummits(map, trailhead, summits);
    return summits.size();
  }

  public void findSummits(
      final TopoMap map, final Position position, final List<Position> summits) {
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
