package com.codenzasoft.advent2024.day11

import spock.lang.Specification

class Day11Spec extends Specification {

    def "test the example data is processed correctly"() {
        when:
        var result = new Day11().solveBlinks("125 17", 25)

        then:
        result == 55312
    }

    def "test walking the tree"() {
        setup:
        var stone = new Stone(125)

        when:
        var result = stone.countStones(blinks)

        then:
        result == expectedResult

        where:
        blinks  || expectedResult
        1       || 1
        2       || 2
        3       || 2
    }
}
