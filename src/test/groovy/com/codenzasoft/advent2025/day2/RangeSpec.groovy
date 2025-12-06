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
        var invalid = range.findHalfAndHalfs()

        then:
        invalid.size() == 1
        invalid.contains(1188511885L)
    }

    def "test multiple invalid IDs can be found in a range"() {
        setup:
        var range = Range.parse("11-22")

        when:
        var invalid = range.findHalfAndHalfs()

        then:
        invalid.size() == 2
        invalid.contains(11L)
        invalid.contains(22L)
    }

    def "test range overlap"() {
        when:
        var r1 = new Range(16,20)
        var r2 = new Range(12,18)

        then:
        r1.overlaps(r2)
        r2.overlaps(r1)
    }

    def "test containment"() {
        when:
        var r1 = new Range(16,20)

        then:
        r1.contains(16)
        r1.contains(17)
        r1.contains(20)
        !r1.contains(15)
        !r1.contains(21)
    }

    def "test ordering"() {
        when:
        var r1 = new Range(16,20)

        then:
        r1.isGreaterThanValue(15)
        !r1.isGreaterThanValue(16)
        !r1.isGreaterThanValue(21)
    }

}
