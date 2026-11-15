package com.codenzasoft.advent2024.day8

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

        then:
        result == 14
    }
}
