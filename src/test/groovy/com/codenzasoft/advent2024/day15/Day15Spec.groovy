package com.codenzasoft.advent2024.day15

import spock.lang.Specification

import java.sql.PreparedStatement

class Day15Spec extends Specification {

    def "test the small example"() {
        setup:
        var lines = [
                "########",
                "#..O.O.#",
                "##@.O..#",
                "#...O..#",
                "#.#.O..#",
                "#...O..#",
                "#......#",
                "########",
                "",
                "<^^>>>vv<v>>v<<"
        ]

        when:
        var result = new Day15().solvePart1(lines)

        then:
        result == 2028
    }

    def "test the GPS sum"() {
        setup:
        var lines = [
                "##########",
                "#.O.O.OOO#",
                "#........#",
                "#OO......#",
                "#OO@.....#",
                "#O#.....O#",
                "#O.....OO#",
                "#O.....OO#",
                "#OO....OO#",
                "##########"
        ]
        var map = WarehouseMap.parse(lines)

        when:
        var result = map.boxGpsSum()

        then:
        result == 10092
    }
}
