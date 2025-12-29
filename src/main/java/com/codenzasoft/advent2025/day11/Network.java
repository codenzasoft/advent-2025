package com.codenzasoft.advent2025.day11;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

public class Network {

  public static void main(final String[] args) {
    final List<String> lines = PuzzleHelper.getInputLines("input-day-11.txt");
    final List<Device> devices = lines.stream().map(Device::parse).toList();
    System.out.println("The number of paths for part 1 is: " + part1(devices));
  }

  public static int part1(final List<Device> devices) {
    final Graph<String, DefaultEdge> graph = buildGraph(devices);

    final AllDirectedPaths<String, DefaultEdge> allPathsAlg = new AllDirectedPaths<>(graph);

    List<GraphPath<String, DefaultEdge>> paths = allPathsAlg.getAllPaths("you", "out", true, null);

    System.out.println("All simple paths from you to out:");
    for (GraphPath<String, DefaultEdge> path : paths) {
      System.out.println(path.getVertexList());
    }
    return paths.size();
  }

  public static Graph<String, DefaultEdge> buildGraph(final List<Device> devices) {
    final Graph<String, DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);
    for (final Device device : devices) {
      graph.addVertex(device.name());
    }
    graph.addVertex("out");
    for (final Device device : devices) {
      for (final String output : device.outputs()) {
        final DefaultEdge edge = graph.addEdge(device.name(), output);
      }
    }
    return graph;
  }
}
