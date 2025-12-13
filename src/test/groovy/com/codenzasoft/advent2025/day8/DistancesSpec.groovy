package com.codenzasoft.advent2025.day8

import spock.lang.Specification

class DistancesSpec extends Specification {

    def "test circuit gathering"() {
        setup:
        var points = [
                new Point3D(162,817,812),
                new Point3D(57,618,57 ),
                new Point3D(906,360,560),
                new Point3D(592,479,940),
                new Point3D(352,342,300),
                new Point3D(466,668,158),
                new Point3D(542,29,236),
                new Point3D(431,825,988),
                new Point3D(739,650,466),
                new Point3D(52,470,668),
                new Point3D(216,146,977),
                new Point3D(819,987,18),
                new Point3D(117,168,530),
                new Point3D(805,96,715),
                new Point3D(346,949,466),
                new Point3D(970,615,88),
                new Point3D(941,993,340),
                new Point3D(862,61,35),
                new Point3D(984,92,344),
                new Point3D(425,690,689)
        ]

        when:
        long result = Distances.part1(points, 10)

        then:
        result == 40
    }
}
