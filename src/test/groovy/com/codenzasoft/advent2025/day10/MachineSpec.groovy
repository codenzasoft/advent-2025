package com.codenzasoft.advent2025.day10

import spock.lang.Specification

class MachineSpec extends Specification {

    def "test machine parsing"() {
        setup:
        var input = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}"

        when:
        var machine = Machine.parse(input)

        then:
        machine.lights().states() == [false, true, true, false]
        machine.buttonList().get(0).buttonOffsets() == new int[]{3}
        machine.buttonList().get(1).buttonOffsets() == new int[]{1,3}
        machine.buttonList().get(2).buttonOffsets() == new int[]{2}
        machine.buttonList().get(3).buttonOffsets() == new int[]{2,3}
        machine.buttonList().get(4).buttonOffsets() == new int[]{0,2}
        machine.buttonList().get(5).buttonOffsets() == new int[]{0,1}
        machine.joltage().levels() == [3, 5, 4, 7]
    }

    def "test part 1 sum"() {
        setup:
        var m1 = Machine.parse("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}")
        var m2 = Machine.parse("[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}")
        var m3 = Machine.parse("[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}")

        when:
        var result = Toggler.part1([m1, m2, m3])

        then:
        result == 7
    }

}
