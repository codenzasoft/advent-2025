package com.codenzasoft.advent2025.day5

import spock.lang.Specification

class IdSearchSpec extends Specification {

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
        var search = new IdSearch().part1(lines)

        then:
        search == 3
    }
}
