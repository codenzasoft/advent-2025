package com.codenzasoft.advent2025.day10

import spock.lang.Specification

class VectorSpec extends Specification {

    def "test max coefficient"() {
        when:
        var v1 = new Vector([0,0,0,1])
        var v2 = new Vector([1,1,0,0])
        var v3 = new Vector([0,0,1,1])
        var total = new Vector([3,5,4,7])

        then:
        v1.getMaxCoefficient(total) == 7
        v2.getMaxCoefficient(total) == 3
        v3.getMaxCoefficient(total) == 4
    }

    def "test multiply"() {
        when:
        var v1 = new Vector([0,0,1,1])

        then:
        v1.multiply(3) == new Vector([0,0,3,3])
    }

    def "test subtract"() {
        when:
        var v1 = new Vector([0,0,1,1])
        var v2 = new Vector([3,5,4,7])

        then:
        v2.subtract(v1) == new Vector([3,5,3,6])
    }

    def "test add"() {
        when:
        var v1 = new Vector([0,0,1,1])
        var v2 = new Vector([3,5,4,7])

        then:
        v2.add(v1) == new Vector([3,5,5,8])
    }

}
