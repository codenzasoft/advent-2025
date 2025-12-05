package com.codenzasoft.advent2025.day5

import spock.lang.Specification

class IdSearchSpec extends Specification {

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
        var pair = IdSearch.parse(lines)

        then:
        pair.ranges().ranges().size() == 4
        pair.ids().size() == 6
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
        var search = new IdSearch().part1(pair.ranges(), pair.ids())

        then:
        search == 3
    }
}
