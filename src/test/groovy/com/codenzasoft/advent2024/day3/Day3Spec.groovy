package com.codenzasoft.advent2024.day3

import spock.lang.Specification

class Day3Spec extends  Specification {

    def "test corrupted memory scan"() {
        setup:
        var lines = [
                "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
        ]

        when:
        var result = new Day3().scanCorruptedMemory(lines)

        then:
        result == 161
    }

    def "test corrupted enabled memory scan"() {
        setup:
        var lines = [
                "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
        ]

        when:
        var result = new Day3().scanEnabledCorruptedMemory(lines)

        then:
        result == 48
    }
}
