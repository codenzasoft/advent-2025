package com.codenzasoft.advent2024.day11

import spock.lang.Specification

class Day11Spec extends Specification {

    def "test the example data is processed correctly"() {
        when:
        var result = new Day11().solveBlinks("125 17", 25)
        var optimal = new Day11().splitAndSolve("125 17", 25)

        then:
        result == 55312
        optimal == result
    }

    def "test walking the tree"() {
        setup:
        var stone = new Stone(125)
        var cache = new StoneBlinkCache()

        when:
        var result = stone.countStones(blinks, cache)

        then:
        result == expectedResult

        where:
        blinks  || expectedResult
        1       || 1
        2       || 2
        3       || 2
    }
}
