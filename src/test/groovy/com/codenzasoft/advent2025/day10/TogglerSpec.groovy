package com.codenzasoft.advent2025.day10

import com.codenzasoft.advent2025.day12.Tetris
import spock.lang.Specification

class TogglerSpec extends Specification {

    def "test solving a machine's lights"() {
        setup:
        var machine = Machine.parse("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}")

        when:
        int min = Toggler.solveMachine(machine)

        then:
        min == 2
    }

    def "test solving a machine's joltage"() {
        setup:
        var machine = Machine.parse("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}")

        when:
        int min = Toggler.solveJoltage(machine)
        int min2 = Toggler.solveJoltage2(machine)

        then:
        min == 10
        min2 == 10
    }

    def "test solving another machine's joltage"() {
        setup:
        var machine = Machine.parse("[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}")

        when:
        int min = Toggler.solveJoltage(machine)
        int min2 = Toggler.solveJoltage2(machine)

        then:
        min == 12
        min2 == 12
    }

    def "test solving sample machine joltages"() {
        setup:
        var lines = [
                "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}",
                "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}",
                "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}"
        ]
        var machines = lines.stream().map(Machine::parse).toList()

        when:
        int result = Toggler.part2(machines)

        then:
        result == 33
    }
}
