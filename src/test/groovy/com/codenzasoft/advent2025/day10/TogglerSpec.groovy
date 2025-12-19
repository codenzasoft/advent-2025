package com.codenzasoft.advent2025.day10

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
}
