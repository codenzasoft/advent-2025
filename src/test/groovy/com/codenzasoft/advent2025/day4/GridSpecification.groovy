package com.codenzasoft.advent2025.day4

import spock.lang.Specification

class GridSpecification extends Specification {

    def "test finding entries with fewer than 4 adjacent filled (paper roll) entries"() {
        setup:
        var rows = [
                "..@@.@@@@.",
                "@@@.@.@.@@",
                "@@@@@.@.@@",
                "@.@@@@..@.",
                "@@.@@@@.@@",
                ".@@@@@@@.@",
                ".@.@.@.@@@",
                "@.@@@.@@@@",
                ".@@@@@@@@.",
                "@.@.@@@.@.",
        ]
        var grid = new Grid.GridBuilder().withRows(rows).build()

        when:
        var count = new RollFinder().partOne(grid)

        then:
        count == 13
    }

    def "test repeatedly removing entries with fewer than 4 adjacent filled (paper roll) entries"() {
        setup:
        var rows = [
                "..@@.@@@@.",
                "@@@.@.@.@@",
                "@@@@@.@.@@",
                "@.@@@@..@.",
                "@@.@@@@.@@",
                ".@@@@@@@.@",
                ".@.@.@.@@@",
                "@.@@@.@@@@",
                ".@@@@@@@@.",
                "@.@.@@@.@.",
        ]
        var grid = new Grid.GridBuilder().withRows(rows).build()

        when:
        var count = new RollFinder().partTwo(grid)

        then:
        count == 43
    }

    def "test finding entries"() {
        setup:
        var rows = [
                "..@@.@@@@.",
                "@@@.@.@.@@",
                "@@@@@.@.@@",
                "@.@@@@..@.",
                "@@.@@@@.@@",
                ".@@@@@@@.@",
                ".@.@.@.@@@",
                "@.@@@.@@@@",
                ".@@@@@@@@.",
                "@.@.@@@.@.",
        ]

        when:
        var grid = new Grid.GridBuilder().withRows(rows).build()

        then:
        grid.getEntry(new Position(0,0)).get().value() == GridEntry.EMPTY
        grid.getEntry(new Position(-1,0)).isEmpty()
        grid.getEntry(new Position(3,-1)).isEmpty()
        grid.getEntry(new Position(2,10)).isEmpty()
        grid.getEntry(new Position(10,0)).isEmpty()
        grid.getEntry(new Position(9,8)).get().value() == GridEntry.PAPER_ROLL
    }

    def "test finding matching adjacent entries"() {
        setup:
        var rows = [
                "..@@.@@@@.",
                "@@@.@.@.@@",
                "@@@@@.@.@@",
                "@.@@@@..@.",
                "@@.@@@@.@@",
                ".@@@@@@@.@",
                ".@.@.@.@@@",
                "@.@@@.@@@@",
                ".@@@@@@@@.",
                "@.@.@@@.@.",
        ]

        when:
        var grid = new Grid.GridBuilder().withRows(rows).build()

        then:
        grid.getMatchingAdjacentPositions(new Position(0,0), GridEntry.PAPER_ROLL ).size() == 2
        grid.getMatchingAdjacentPositions(new Position(0,0), GridEntry.EMPTY ).size() == 1
        grid.getMatchingAdjacentPositions(new Position(4,4), GridEntry.PAPER_ROLL ).size() == 8
    }

    def "test finding entries"() {
        setup:
        var rows = [
                "..@@.@@@@.",
                "@@@.@.@.@@",
                "@@@@@.@.@@",
                "@.@@@@..@.",
                "@@.@@@@.@@",
                ".@@@@@@@.@",
                ".@.@.@.@@@",
                "@.@@@.@@@@",
                ".@@@@@@@@.",
                "@.@.@@@.@.",
        ]

        when:
        var grid = new Grid.GridBuilder().withRows(rows).build()

        then:
        grid.getRowStrings() == rows
    }
}
