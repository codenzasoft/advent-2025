package com.codenzasoft.advent2024.day8

import com.codenzasoft.advent2025.day9.Point
import spock.lang.Specification

class Day8Spec extends Specification {

    def "test solving the sample puzzle"() {
        setup:
        var lines = [
                "............",
                "........0...",
                ".....0......",
                ".......0....",
                "....0.......",
                "......A.....",
                "............",
                "............",
                "........A...",
                ".........A..",
                "............",
                "............"
        ]

        when:
        var result = new Day8().solvePart1(lines)
        var part2 = new Day8().solvePart2(lines)

        then:
        result == 14
        part2 == 34
    }

    def "test computing all points on a line"() {
        setup:
        var map = new CityMap(10, 8, List.of())
        var line = new Line(new Position(1,1), new Position(3,3))

        when:
        var result = line.getExtendedPointsWithin(map)

        then:
        result.size() == 4
    }
}
