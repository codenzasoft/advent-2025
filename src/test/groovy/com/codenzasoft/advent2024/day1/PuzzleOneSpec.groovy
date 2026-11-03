package com.codenzasoft.advent2024.day1

import spock.lang.Specification

class PuzzleOneSpec extends  Specification {

    def "test distance calculation"() {
        var lines = [
                "3  4",
                "4  3",
                "2  5",
                "1  3",
                "3  9",
                "3  3"
                ]

        when:
        var result = new PuzzleOne().solveDistance(lines)

        then:
        result == 11
    }

    def "test similarity calculation"() {
        var lines = [
                "3  4",
                "4  3",
                "2  5",
                "1  3",
                "3  9",
                "3  3"
        ]

        when:
        var result = new PuzzleOne().solveSimilarity(lines)

        then:
        result == 31
    }
}
