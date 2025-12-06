package com.codenzasoft.advent2025.day2

import spock.lang.Specification

class RangeSpec extends Specification {

    def "test a range can be parsed from a string"() {
        when:
        var range = Range.parse("69684057-69706531")

        then:
        range.start() == 69684057L
        range.end() == 69706531L
    }

    def "test invalid IDs can be found in a range"() {
        setup:
        var range = Range.parse("1188511880-1188511890")

        when:
        var invalid = range.findHalfAndHalfIds()

        then:
        invalid.size() == 1
        invalid.contains(1188511885L)
    }

    def "test multiple invalid IDs can be found in a range"() {
        setup:
        var range = Range.parse("11-22")

        when:
        var invalid = range.findHalfAndHalfIds()

        then:
        invalid.size() == 2
        invalid.contains(11L)
        invalid.contains(22L)
    }
}
