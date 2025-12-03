package com.codenzasoft.advent2025.day3

import spock.lang.Specification

class JoltageSpec extends Specification {

    def "test joltage sum calculation"() {
        setup:
        var input = [
                "987654321111111",
                "811111111111119",
                "234234234234278",
                "818181911112111"
        ]

        when:
        var result = new Joltage().getDoulbeDigitJoltageSum(input)

        then:
        result == 357
    }
}
