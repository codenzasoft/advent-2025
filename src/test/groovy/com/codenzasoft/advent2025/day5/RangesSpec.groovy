package com.codenzasoft.advent2025.day5

import spock.lang.Specification

class RangesSpec extends Specification {

    def "test compression"() {
        setup:
        var lines = [
                "3-5",
                "10-14",
                "16-20",
                "12-18",
                "",
                "1",
                "5",
                "17",
                "8",
                "11",
                "32"
        ]

        when:
        var ranges = IdSearch.parse(lines).ranges().compress()

        then:
        ranges.ranges() == [new Range(3,5), new Range(10,20)]
    }

    def "test range selection"() {
        setup:
        var lines = [
                "3-5",
                "10-14",
                "16-20",
                "12-18",
                "",
                "1",
                "5",
                "17",
                "8",
                "11",
                "32"
        ]

        when:
        var pair = IdSearch.parse(lines)
        var compressed = pair.ranges().compress()

        then:
        compressed.getContainedIds(pair.ids()) == [5L, 11L, 17L]
    }

    def "test streaming all ids in range"() {
        setup:
        var lines = [
                "3-5",
                "10-14",
                "16-20",
                "12-18",
        ]

        when:
        var pair = IdSearch.parse(lines)
        var compressed = pair.ranges().compress()

        then:
        compressed.idSize() == 14
    }

}
