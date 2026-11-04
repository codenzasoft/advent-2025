package com.codenzasoft.advent2024.day5;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record EdgesAndPaths(
    List<PageEdge> edges, List<PagePath> paths, Map<Integer, PageNumber> ordering) {

  public static EdgesAndPaths parse(List<String> lines) {
    List<PageEdge> edges =
        lines.stream().map(PageEdge::parse).filter(Optional::isPresent).map(Optional::get).toList();
    List<PagePath> paths =
        lines.stream().map(PagePath::parse).filter(Optional::isPresent).map(Optional::get).toList();
    Map<Integer, PageNumber> ordering =
        edges.stream()
            .flatMap(edge -> Stream.of(edge.from(), edge.to()))
            .distinct()
            .map(PageNumber::new)
            .collect(Collectors.toMap(PageNumber::page, pn -> pn));
    for (final PageEdge edge : edges) {
      final PageNumber from = ordering.get(edge.from());
      from.addHigherPage(edge.to());
    }
    return new EdgesAndPaths(edges, paths, ordering);
  }

  public PageNumber getPageOrdering(int page) {
    return ordering.get(page);
  }

  public Set<Integer> getVertexes() {
    return edges.stream()
        .flatMap(edge -> Stream.of(edge.from(), edge.to()))
        .collect(Collectors.toSet());
  }

  public List<PageEdge> getRelevantEdges(final PagePath path) {
    final Set<Integer> vertexes = path.vertexes();
    return edges.stream()
        .filter(edge -> vertexes.contains(edge.from()) && vertexes.contains(edge.to()))
        .toList();
  }
}
