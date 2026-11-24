package com.codenzasoft.advent2024.day13

import com.codenzasoft.advent2024.day12.Day12
import spock.lang.Specification

class MachineSpec extends Specification {

    def "test a list of machines can be parsed"() {
        setup:
        var lines = [
                "Button A: X+94, Y+34",
                "Button B: X+22, Y+67",
                "Prize: X=8400, Y=5400",
                "",
                "Button A: X+26, Y+66",
                "Button B: X+67, Y+21",
                "Prize: X=12748, Y=12176",
                "",
                "Button A: X+17, Y+86",
                "Button B: X+84, Y+37",
                "Prize: X=7870, Y=6450",
                "",
                "Button A: X+69, Y+23",
                "Button B: X+27, Y+71",
                "Prize: X=18641, Y=10279"
        ]

        when:
        var machines = Machine.parse(lines)
        var m0 = machines.get(0)
        var machine1 = machines.get(1)
        var tokens = new Day13().solvePart1(lines)

        then:
        machines.size() == 4
        machine1.a() == new Vector(26, 66)
        machine1.b() == new Vector(67, 21)
        machine1.result() == new Vector(12748, 12176)
        m0.solve() == new Vector(80, 40)
        tokens == 480
    }
}
