package com.codenzasoft.advent2024.day11

import spock.lang.Specification

class Day11Spec extends Specification {

    def "test the example data is processed correctly"() {
        when:
        var result = new Day11().solvePart1("125 17")

        then:
        result == 55312
    }
}
