package com.codenzasoft.advent2025.day12

import spock.lang.Specification

class GridSpec extends Specification {

    def "test fit in empty grid"() {
        when:
        def grid = Grid.empty(5,5)
        def present = PresentSpec.buildTestPresent()

        then:
        grid.getEmptyUnits() == 25
        grid.emptyNeighbourCount(0,0) == 3
        grid.getConnectedUnits(9) == 9
        grid.canInsert(present, 0, 0)
        grid.canInsert(present, 0, 1)
        grid.canInsert(present, 0, 2)
        !grid.canInsert(present, 0, 3)
        !grid.canInsert(present, 3, 0)
    }

    def "test insert into empty grid"() {
        setup:
        def grid = Grid.empty(5,5)
        def present = PresentSpec.buildTestPresent()

        when:
        var grid2 = grid.insert(present, 1, 2)

        then:
        grid2.toString() == ".....\n..##.\n..###\n..##.\n.....\n"
        grid2.getEmptyUnits() == 18
    }

    def "def test tetris fit"() {
        setup:
        var p1 = Present.parse(List.of(
                "1:",
                "##.",
                "###",
                "##."))
        var p2 = Present.parse(List.of(
                "2:",
                "..##",
                "...#",
                "..##"))
        var grid = Grid.empty(5,5)

        when:
        var g1 = grid.insert(p1, 0, 0)

        then:
        g1.canInsert(p2, 0, 0)
        !g1.canInsert(p2, 0, -1)

        when:
        var g2 = g1.insert(p2, 0, 0)

        then:
        g2.toString() == "####.\n####.\n####.\n.....\n.....\n"
    }

}
