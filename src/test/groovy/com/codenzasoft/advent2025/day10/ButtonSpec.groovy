package com.codenzasoft.advent2025.day10

import spock.lang.Specification

class ButtonSpec extends Specification {

    def "test translation to a vector"() {
        setup:
        var input = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}"

        when:
        var machine = Machine.parse(input)
        var button = machine.buttonList().get(1)

        then:
        button.getVector(machine) == new Vector([0,1,0,1])
    }
}
