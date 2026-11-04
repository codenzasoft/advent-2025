package com.codenzasoft.advent2024.day5;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record EdgesAndPaths(List<PageEdge> edges, List<PagePath> paths) {

  public static EdgesAndPaths parse(List<String> lines) {
    List<PageEdge> edges =
        lines.stream().map(PageEdge::parse).filter(Optional::isPresent).map(Optional::get).toList();
    List<PagePath> paths =
        lines.stream().map(PagePath::parse).filter(Optional::isPresent).map(Optional::get).toList();
    return new EdgesAndPaths(edges, paths);
  }

  public Set<Integer> getVertexes() {
    return edges.stream()
        .flatMap(edge -> Stream.of(edge.from(), edge.to()))
        .collect(Collectors.toSet());
  }
}
