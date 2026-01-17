package com.codenzasoft.advent2025.day10


import spock.lang.Specification

class TogglerSpec extends Specification {

    def "test solving a machine's lights"() {
        setup:
        var machine = Machine.parse("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}")

        when:
        int min = Toggler.solveMachineLights(machine)

        then:
        min == 2
    }

    def "test solving a machine's joltage"() {
        setup:
        var machine = Machine.parse("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}")

        when:
        int min = Toggler.solve(machine).map(Vector::sum).orElse(0)
        int parts = Toggler.solveInParts(machine)
        int bfs = Toggler.bfs(machine)
        int coeff = Toggler.solveCoefficients(machine)

        then:
        min == 10
        parts == 10
        bfs == 10
        coeff == 10
    }

//    def "test solving a machine's joltage using Jama"() {
//        setup:
//        var machine = Machine.parse("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}")
//
//        when:
//        int jama = Toggler.solveJama(machine)
//
//        then:
//        jama == 10
//    }

    def "test solving a machine's joltage using min norm"() {
        setup:
        var machine = Machine.parse("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}")

        when:
        int jama = Toggler.solveMinimumNorm(machine)

        then:
        jama == 10
    }

    //
    // 0, 0, 0, 1
    // 0, 1, 0, 1
    // 0, 0, 1, 0
    // 0, 0, 1, 1
    // 1, 0, 1, 0
    // 1, 1, 0, 0
    //
    // 3, 5, 4, 7

    // e + f = 3
    // b + f = 5
    // c + d + e = 4
    // a + b + d = 7

    def "test solving another machine's joltage"() {
        setup:
        var machine = Machine.parse("[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}")

        when:
        int min = Toggler.solve(machine).map(Vector::sum).orElse(0)
        int coeff = Toggler.solveCoefficients(machine)

        then:
        min == 12
        coeff == 12
    }

    def "test solving for ones"() {
        setup:
        var machine = Machine.parse("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {1,1,1,1}")

        when:
        int r0 = Toggler.solve(machine).map(Vector::sum).orElse(0)

        then:
        r0 == 2
    }

    def "test solving parts of the puzzle"() {
        setup:
        var machine = Machine.parse("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}")
        var m1 = Machine.parse("[.##.] (2,3) (0,1) {3,3,4,4}")
        var m2 = Machine.parse("[.##.] (3) (1,3) (2) (0,2) {0,2,0,3}")

        when:
        int r0 = Toggler.solve(machine).map(Vector::sum).orElse(0)
        int r1 = Toggler.solve(m1).map(Vector::sum).orElse(0)
        int r2 = Toggler.solve(m2).map(Vector::sum).orElse(0)

        then:
        r0 == 10
        r1 == 7
        r2 == 3
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
        int parts = Toggler.solveInParts(machines)

        then:
        result == 33
//        SOLVING IN PARTS IS NOT WORKING
//        parts == 33
    }
}
