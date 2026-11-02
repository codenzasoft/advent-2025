package com.codenzasoft.advent2024.puzzle1

import spock.lang.Specification

class PuzzleOneSpec extends  Specification {

    def "test puzzle one"() {
        var lines = [
                "3  4",
                "4  3",
                "2  5",
                "1  3",
                "3  9",
                "3  3"
                ]

        when:
        var result = new PuzzleOne().solve(lines)

        then:
        result == 11
    }
}
