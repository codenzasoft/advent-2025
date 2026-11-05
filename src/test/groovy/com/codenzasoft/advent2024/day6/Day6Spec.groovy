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
}
