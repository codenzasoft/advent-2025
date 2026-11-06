package com.codenzasoft.advent2024.day6

import spock.lang.Specification

class Day6Spec extends Specification {

    def "test route tracing"() {
        setup:
        var lines = [
                "....#.....",
                ".........#",
                "..........",
                "..#.......",
                ".......#..",
                "..........",
                ".#..^.....",
                "........#.",
                "#.........",
                "......#..."
        ]

        when:
        var grid = new Day6().traceRoute(lines)

        then:
        grid.countVisitedPositions() == 41
        grid.getLoopCounter() == 6
    }

    def "test floor map tracing"() {
        setup:
        var lines = [
                "....#.....",
                ".........#",
                "..........",
                "..#.......",
                ".......#..",
                "..........",
                ".#..^.....",
                "........#.",
                "#.........",
                "......#..."
        ]

        when:
        var route = new Day6().buildRoute(lines)
        var cycles = new Day6().cycleSearch(lines)

        then:
        route.uniquePositions().size() == 41
        !route.isCycle()
        cycles.size() == 6
    }
}
