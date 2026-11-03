package com.codenzasoft.advent2024.day2

import spock.lang.Specification

class Day2Spec extends Specification {

    def "test counting safe reports"() {
        setup:
        var lines = [
                "7 6 4 2 1",
                "1 2 7 8 9",
                "9 7 6 2 1",
                "1 3 2 4 5",
                "8 6 4 4 1",
                "1 3 6 7 9"
        ]

        when:
        var result = new Day2().countSafeReports(lines)
        var tolerable = new Day2().countTolerableReports(lines)

        then:
        result == 2
        tolerable == 4
    }

}
