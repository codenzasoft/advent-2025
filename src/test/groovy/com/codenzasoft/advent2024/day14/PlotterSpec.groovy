package com.codenzasoft.advent2024.day14

import spock.lang.Specification

class PlotterSpec extends Specification {

    def "test a robot moving"() {
        setup:
        var grid = new Grid(11,7)
        var robot = new Robot(new Location(2,4), new Velocity(2, -3))

        when:
        var r1 = robot.move(grid)
        var r2 = r1.move(grid)

        then:
        r1.location() == new Location(4,1)
        r2.location() == new Location(6,5)
        grid.quadrantOf(r1.location()).get() == new Quadrant(0,0)
        grid.quadrantOf(r2.location()).get() == new Quadrant(1, 1)
        grid.quadrantOf(new Location(5,2)).isEmpty()
        grid.quadrantOf(new Location(2,3)).isEmpty()
    }
}
