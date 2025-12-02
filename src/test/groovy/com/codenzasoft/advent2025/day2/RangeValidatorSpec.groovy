package com.codenzasoft.advent2025.day2

import spock.lang.Specification

class RangeValidatorSpec extends Specification {

    def "test the range validator using mirror IDs from the given example"() {
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
        var total = new RangeValidator().computeMirrorTotal(ranges)

        then:
        total == 1227775554L
    }

    def "test the range validator using sub-sequence IDs from the given example"() {
        setup:
        var ranges = [
                Range.parse("11-22"),
                Range.parse("95-115"),
                Range.parse("998-1012"),
                Range.parse("1188511880-1188511890"),
                Range.parse("222220-222224"),
                Range.parse("1698522-1698528"),
                Range.parse("446443-446449"),
                Range.parse("38593856-38593862"),
                Range.parse("565653-565659"),
                Range.parse("824824821-824824827"),
                Range.parse("2121212118-2121212124")
        ]

        when:
        var total = new RangeValidator().computeRepeatedSequenceTotal(ranges)

        then:
        total == 4174379265L
    }
}
