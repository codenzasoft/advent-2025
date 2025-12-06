package com.codenzasoft.advent2025.day6

import spock.lang.Specification

class ProblemSolverSpec extends Specification {

    def "test part 2"() {
        setup:
        var lines = [
                "123 328  51 64 ",
                " 45 64  387 23 ",
                "  6 98  215 314",
                "*   +   *   +"
        ]

        when:
        var result = ProblemSolver.part2(lines)

        then:
        result == 3263827
    }
}
