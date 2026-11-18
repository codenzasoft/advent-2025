package com.codenzasoft.advent2024.day10

import spock.lang.Specification

class Day10Spec extends Specification {

    def "test that the score of the simple map can be computed"() {
        setup:
        var lines = [
                "0123",
                "1234",
                "8765",
                "9876"
        ]

        when:
        var map = TopoMap.parse(lines)
        var result = new TrailSearch().calculateScore(map)

        then:
        result == 1
    }

    def "test that the score of the example map can be computed"() {
        setup:
        var lines = [
                "89010123",
                "78121874",
                "87430965",
                "96549874",
                "45678903",
                "32019012",
                "01329801",
                "10456732"
        ]

        when:
        var map = TopoMap.parse(lines)
        var result = new TrailSearch().calculateScore(map)

        then:
        result == 36
    }
}
