package com.codenzasoft.advent2025.day2

import spock.lang.Specification

class RangeValidatorSpec extends Specification {

    def "test the range validator from the given example"() {
        setup:
        var ranges = [
                Range.parse("11-22"),
                Range.parse("95-115"),
                Range.parse("998-1012"),
                Range.parse("1188511880-1188511890"),
                Range.parse("222220-222224"),
                Range.parse("1698522-1698528"),
                Range.parse("446443-446449"),
                Range.parse("38593856-38593862")
        ]

        when:
        var total = new RangeValidator().computeTotal(ranges)

        then:
        total == 1227775554L
    }
}
