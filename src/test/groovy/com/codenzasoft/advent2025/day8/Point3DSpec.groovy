package com.codenzasoft.advent2025.day8

import spock.lang.Specification

class Point3DSpec extends Specification {

    def "test distance"() {
        setup:
        var p1 = new Point3D(162,817,812)
        var p2 = new Point3D(425,690,689)

        when:
        double dist = p1.distance(p2)

        then:
        dist > 0
    }

}
