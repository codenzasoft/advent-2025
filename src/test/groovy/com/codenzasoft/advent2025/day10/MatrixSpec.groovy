package com.codenzasoft.advent2025.day10

import spock.lang.Specification

class MatrixSpec extends Specification {

    def "test re-ordering columns"() {
        setup:
        var matrix = new Matrix([
                vector([1,2,3,4,5]),
                vector([2,2,3,4,5]),
                vector([3,2,3,4,5]),
                vector([4,2,3,4,5]),
                vector([5,2,3,4,5]),
                vector([6,2,3,4,5])
        ])
        var reordered = new Matrix([
                vector([5,4,3,2,1]),
                vector([5,4,3,2,2]),
                vector([5,4,3,2,3]),
                vector([5,4,3,2,4]),
                vector([5,4,3,2,5]),
                vector([5,4,3,2,6])
        ])
        var vector = vector([5,4,3,2,1])


        when:
        var result = matrix.reorderColumns(vector.getAscendingIncdicies())

        then:
        result == reordered
    }

    def Vector vector(List<Integer> values) {
        return new Vector(values)
    }
}
