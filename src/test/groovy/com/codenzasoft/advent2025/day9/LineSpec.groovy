package com.codenzasoft.advent2025.day9

import spock.lang.Specification

class LineSpec extends Specification {

    def "test a point on a line"() {
        when:
        var line = new Line(new Point(11,2), new Point(11, 7))
        var p1 = new Point(11, 1)
        var p2 = new Point(11, 2)
        var p3 = new Point(11, 5)
        var p4 = new Point (11, 7)
        var p5 = new Point(11, 8)

        then:
        !line.isOnLine(p1)
        line.isOnLine(p2)
        line.isOnLine(p3)
        line.isOnLine(p4)
        !line.isOnLine(p5)
    }

    def "test an xray crosses a line"() {
        when:
        var line = new Line(new Point(11,2), new Point(11, 7))
        var p1 = new Point(5, 1)
        var p2 = new Point(5, 2)
        var p3 = new Point(5, 5)
        var p4 = new Point(5, 7)
        var p5 = new Point(5, 8)

        var p6 = new Point(13, 1)
        var p7 = new Point(13, 2)
        var p8 = new Point(13, 5)

        then:
        !line.xRayCrossesY(p1)
        !line.xRayCrossesY(p2)
        line.xRayCrossesY(p3)
        line.xRayCrossesY(p4)
        !line.xRayCrossesY(p5)

        !line.xRayCrossesY(p6)
        !line.xRayCrossesY(p7)
        !line.xRayCrossesY(p8)
    }

    def "test line intersection"() {
        when:
        var hLine = new Line(new Point(10,10), new Point(20,10))
        var vLine = new Line(new Point (15, 5), new Point(15,15))

        then:
        hLine.intersects(vLine)
    }

    def "test lines do not intersect"() {
        when:
        var hLine = new Line(new Point(10,10), new Point(20,10))
        var vLine = new Line(new Point (5, 5), new Point(5,15))

        then:
        !hLine.intersects(vLine)
    }

    def "test orientation"() {
        when:
        var hLine = new Line(new Point(10,10), new Point(20,10))
        var vLine = new Line(new Point (15, 5), new Point(15,15))

        then:
        hLine.isHorizontal()
        !hLine.isVertical()
        vLine.isVertical()
        !vLine.isHorizontal()
    }

}
