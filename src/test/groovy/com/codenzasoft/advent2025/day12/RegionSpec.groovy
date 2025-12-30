package com.codenzasoft.advent2025.day12

import spock.lang.Specification

class RegionSpec extends Specification {

    def "test region parsing"() {
        setup:
        var text = "12x5: 1 0 1 0 2 2"

        when:
        def region = Region.parse(text)

        then:
        region.width() == 12
        region.height() == 5
        region.quantities() == [1, 0, 1, 0, 2, 2]
    }

}
