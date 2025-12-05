package com.codenzasoft.advent2025.day5

import spock.lang.Specification

class RangeSpec extends Specification {

    def "test range parsing"() {
        expect:
        Range.parse("12-18") == new Range(12,18)
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
