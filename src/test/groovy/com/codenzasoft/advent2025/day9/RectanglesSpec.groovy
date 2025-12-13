package com.codenzasoft.advent2025.day9

import spock.lang.Specification

class RectanglesSpec extends Specification {

    def "test max area search"() {
        setup:
        var lines = [
                "7,1",
                "11,1",
                "11,7",
                "9,7",
                "9,5",
                "2,5",
                "2,3",
                "7,3"
        ]
        var points = Rectangles.parsePoints(lines)

        when:
        var result = Rectangles.part1(points)

        then:
        result == 50
    }
}
