package com.codenzasoft.advent2024.day15

import com.codenzasoft.advent2024.day6.Position
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
        var part1 = new Day15().solvePart(lines, 1)

        then:
        part1 == 2028
    }

    def "test the GPS sum with width 1"() {
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
        var map1 = WarehouseMap.parse(lines, 1)

        when:
        var result1 = map1.boxGpsSum()

        then:
        result1 == 10092
    }

    def "test map object contains"() {
        when:
        def object = new MapObject(new Position(0, 0), 2, 1, Type.BOX)

        then:
        object.contains(new Position(0,0))
        object.contains(new Position(0,1))
        !object.contains(new Position(0,2))
        !object.contains(new Position(1,0))
    }

    def "test the larger example"() {
        setup:
        var lines = [
                "##########",
                "#..O..O.O#",
                "#......O.#",
                "#.OO..O.O#",
                "#..O@..O.#",
                "#O#..O...#",
                "#O..O..O.#",
                "#.OO.O.OO#",
                "#....O...#",
                "##########",
                "",
                "<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^",
                "vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v",
                "><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<",
                "<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^",
                "^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><",
                "^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^",
                ">^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^",
                "<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>",
                "^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>",
                "v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^"
        ]

        when:
        var part2 = new Day15().solvePart(lines, 2)

        then:
        part2 == 9021
    }

}
