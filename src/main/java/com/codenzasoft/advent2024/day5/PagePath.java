package com.codenzasoft.advent2024.day5;

import java.util.*;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

public record PagePath(List<Integer> pages) {

  public static Optional<PagePath> parse(String line) {
    if (line.indexOf(",") > 0) {
      return Optional.of(
          new PagePath(Arrays.stream(line.split(",")).map(Integer::parseInt).toList()));
    }
    return Optional.empty();
  }

  public int middlePageNumber() {
    return pages.get((pages.size() - 1) / 2);
  }

  public Integer source() {
    return pages.getFirst();
  }

  public PagePath tail() {
    final List<Integer> tail = new ArrayList<>(pages);
    tail.remove(source());
    return new PagePath(tail);
  }

  public boolean isEmpty() {
    return pages.isEmpty();
  }

  public boolean existsIn(final DirectedMultigraph<Integer, DefaultEdge> graph) {
    final DijkstraShortestPath<Integer, DefaultEdge> dijkstra = new DijkstraShortestPath<>(graph);
    final PagePath tail = tail();
    if (!tail.isEmpty()) {
      for (final Integer page : tail.pages()) {
        final GraphPath<Integer, DefaultEdge> path = dijkstra.getPath(source(), page);
        if (path == null) {
          return false;
        }
      }
      return tail.existsIn(graph);
    }
    return true;
  }
}
