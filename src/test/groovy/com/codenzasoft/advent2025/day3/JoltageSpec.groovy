package com.codenzasoft.advent2025.day3

import spock.lang.Specification

class JoltageSpec extends Specification {

    def "test 2 digit joltage sum calculation"() {
        setup:
        var input = [
                "987654321111111",
                "811111111111119",
                "234234234234278",
                "818181911112111"
        ]

        when:
        var result = new Joltage().getJoltageSum(input, 2)

        then:
        result == 357L
    }

    def "test 12 digit joltage sum calculation"() {
        setup:
        var input = [
                "987654321111111",
                "811111111111119",
                "234234234234278",
                "818181911112111"
        ]

        when:
        var result = new Joltage().getJoltageSum(input, 12)

        then:
        result == 3121910778619L
    }
}
