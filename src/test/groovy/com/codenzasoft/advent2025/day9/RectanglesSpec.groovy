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

    def "test max area search within polygon"() {
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
        var result = Rectangles.part2(points)

        then:
        result == 24
    }

    def "test polygon containment"() {
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
        var polygon = Rectangles.buildPolygon(points)

        then:
        points.stream().allMatch {point -> polygon.contains(point)}
        !polygon.contains(new Rectangle(points[0], points[2]))
        !polygon.contains(new Rectangle(points[0], points[6]))
    }
}
