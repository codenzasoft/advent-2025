package com.codenzasoft.advent2025.day1

import spock.lang.Specification

class SafeCrackerSpec extends Specification {

    def "test a safe can be cracked with a dial size of 5 starting at 0"() {
        setup:
        var rotations = ["R2", "L2", "R3", "R2", "L6", "R1"]
        var cracker = new SafeCracker()

        when:
        var answer = cracker.solve(rotations, 5, 0)

        then:
        answer == 3
    }

    def "test a safe can be cracked with a dial size of 5 starting at 2"() {
        setup:
        var rotations = ["R2", "L2", "R3", "R2", "L6", "R1"]
        var cracker = new SafeCracker()

        when:
        var answer = cracker.solve(rotations, 5, 2)

        then:
        answer == 1
    }
}
