package com.codenzasoft.advent2024.day14

import spock.lang.Specification

class Day14Spec extends Specification {

    def "test the small example"() {
        setup:
        var lines = [
                "p=0,4 v=3,-3",
                "p=6,3 v=-1,-3",
                "p=10,3 v=-1,2",
                "p=2,0 v=2,-1",
                "p=0,0 v=1,3",
                "p=3,0 v=-2,-2",
                "p=7,6 v=-1,-3",
                "p=3,0 v=-1,-2",
                "p=9,3 v=2,3",
                "p=7,3 v=-1,2",
                "p=2,4 v=2,-3",
                "p=9,5 v=-3,-3"
        ]
        var grid = new Grid(11, 7)

        when:
        def result = new Day14().solvePart1(lines, grid, 100)

        then:
        result == 12
    }
}
