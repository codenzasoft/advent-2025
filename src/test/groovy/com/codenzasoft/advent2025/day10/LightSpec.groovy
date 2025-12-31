package com.codenzasoft.advent2025.day10

import spock.lang.Specification

class LightSpec extends Specification {

    def "test button press"() {
        setup:
        var lights = Lights.parse("[.##.]")
        var buttons = Button.parse(1, "(0,2)")

        when:
        var next = lights.press(buttons)

        then:
        next.states() == [true, true, false, false]
    }
}
