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

  public PagePath reversed() {
    return new PagePath(new ArrayList<>(pages.reversed()));
  }

  public boolean isSorted(EdgesAndPaths data) {
    final List<Integer> sorted = sorted(data);
    return sorted.equals(pages);
  }

  public List<Integer> sorted(EdgesAndPaths data) {
    return pages.stream().map(data::getPageOrdering).sorted().map(PageNumber::page).toList();
  }

  public boolean isEmpty() {
    return pages.isEmpty();
  }

  public Set<Integer> vertexes() {
    return new HashSet<>(pages);
  }

  public DirectedMultigraph<Integer, DefaultEdge> buildGraph(final EdgesAndPaths data) {
    final DirectedMultigraph<Integer, DefaultEdge> graph =
        new DirectedMultigraph<>(DefaultEdge.class);
    vertexes().forEach(graph::addVertex);
    data.getRelevantEdges(this).forEach(edge -> graph.addEdge(edge.from(), edge.to()));
    return graph;
  }

  public boolean isCorrect(final DirectedMultigraph<Integer, DefaultEdge> graph) {
    // return reversed().allPaths(graph).isEmpty();
    List<GraphPath<Integer, DefaultEdge>> all = reversed().allPaths(graph);
    return all.isEmpty();
  }

  public List<GraphPath<Integer, DefaultEdge>> allPaths(
      final DirectedMultigraph<Integer, DefaultEdge> graph) {
    final DijkstraShortestPath<Integer, DefaultEdge> dijkstra = new DijkstraShortestPath<>(graph);
    final PagePath tail = tail();
    final List<GraphPath<Integer, DefaultEdge>> paths = new ArrayList<>();
    if (!tail.isEmpty()) {
      for (final Integer page : tail.pages()) {
        final GraphPath<Integer, DefaultEdge> path = dijkstra.getPath(source(), page);
        if (path != null) {
          paths.add(path);
        }
      }
      paths.addAll(tail.allPaths(graph));
    }
    return paths;
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
