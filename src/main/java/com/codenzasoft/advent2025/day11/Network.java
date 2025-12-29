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
    System.out.println("The number of paths for part 2 is: " + part2(devices));
  }

  public static int part1(final List<Device> devices) {
    final Graph<String, DefaultEdge> graph = buildGraph(devices);
    final List<GraphPath<String, DefaultEdge>> paths = findPaths(graph, "you", "out");
    return paths.size();
  }

  public static long part2(final List<Device> devices) {
    final Graph<String, DefaultEdge> graph = buildGraph(devices);
    final List<GraphPath<String, DefaultEdge>> fftToDac = findPaths(graph, "fft", "dac");
    final List<GraphPath<String, DefaultEdge>> svrToFft = findPaths(graph, "svr", "fft");
    // final List<GraphPath<String, DefaultEdge>> svrToDac = findPaths(graph, "svr", "dac");
    final List<GraphPath<String, DefaultEdge>> dacToFft = findPaths(graph, "dac", "fft");
    // final List<GraphPath<String, DefaultEdge>> fftToOut = findPaths(graph, "fft", "out");
    final List<GraphPath<String, DefaultEdge>> dacToOut = findPaths(graph, "dac", "out");

    final long srvToOut1 = (long) svrToFft.size() * (long) fftToDac.size() * (long) dacToOut.size();
    // final int srvToOut2 = svrToDac.size() * dacToFft.size() * fftToOut.size();
    return srvToOut1;
  }

  private static List<GraphPath<String, DefaultEdge>> findPaths(
      final Graph<String, DefaultEdge> graph, final String from, final String to) {
    final AllDirectedPaths<String, DefaultEdge> allPathsAlg = new AllDirectedPaths<>(graph);
    final List<GraphPath<String, DefaultEdge>> paths =
        allPathsAlg.getAllPaths(from, to, true, null);

    System.out.println(paths.size() + " paths from " + from + " to " + to);
    return paths;
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
