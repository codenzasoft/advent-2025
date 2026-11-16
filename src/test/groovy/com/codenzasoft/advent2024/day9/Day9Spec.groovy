package com.codenzasoft.advent2024.day9

import spock.lang.Specification

class Day9Spec extends Specification {

    def "test the example checksum can be computed"() {
        setup:
        var line = "2333133121414131402"

        when:
        var checksum = new Day9().solvePart1(line)

        then:
        checksum == 1928
    }
}
