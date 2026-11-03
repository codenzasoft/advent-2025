package com.codenzasoft.advent2024.day4

import spock.lang.Specification

class Day4Spec extends Specification {

    def "test XMAS search"() {
        setup:
        var lines = [
                "MMMSXXMASM",
                "MSAMXMSMSA",
                "AMXSXMAAMM",
                "MSAMASMSMX",
                "XMASAMXAMM",
                "XXAMMXXAMA",
                "SMSMSASXSS",
                "SAXAMASAAA",
                "MAMMMXMMMM",
                "MXMXAXMASX"
        ]

        when:
        var count = new Day4().searchWord(lines)
        var xCount = new Day4().searchGrid(lines)

        then:
        count == 18
        xCount == 9
    }
}
