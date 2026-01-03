package com.codenzasoft.advent2025.day10

import spock.lang.Specification

class JoltageLevelsSpec extends Specification {

    def "test gcd"() {
        setup:
        var j1 = new JoltageLevels([2,4,6,8])
        var j2= new JoltageLevels([3,7,4,5])

        when:
        var gcd1 = j1.getGreatestCommonDivisor()
        var gcd2 = j2.getGreatestCommonDivisor()

        then:
        gcd1.orElse(0) == 2
        gcd2.isEmpty()
    }
}
