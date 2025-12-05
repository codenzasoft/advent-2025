package com.codenzasoft.advent2025.day5

import spock.lang.Specification

class RangesIdsPairSpec extends Specification {

    def "test parsing"() {
        setup:
        var lines = [
                "3-5",
                "10-14",
                "16-20",
                "12-18",
                "",
                "1",
                "5",
                "8",
                "11",
                "17",
                "32"
        ]

        when:
        var pair = RangesIdsPair.parse(lines)

        then:
        pair.ranges().size() == 4
        pair.ids().size() == 6
    }

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
        var pair = RangesIdsPair.parse(lines)
        var compressed = pair.compress()

        then:
        compressed.ranges() == [new Range(3,5), new Range(10,20)]
        compressed.ids() == [1L,5L,8L,11L,17L,32L]
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
        var pair = RangesIdsPair.parse(lines)
        var compressed = pair.compress()

        then:
        compressed.findIdsInRange() == [5L, 11L, 17L]
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
        var pair = RangesIdsPair.parse(lines)
        var compressed = pair.compress()

        then:
        compressed.idSize() == 14
    }

}
