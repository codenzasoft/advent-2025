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

    def "test re-ordering rows"() {
        setup:
        var matrix = new Matrix([
                vector([1,1,1,1]),
                vector([2,2,2,2]),
                vector([3,3,3,3]),
                vector([4,4,4,4])
        ])
        var reordered = new Matrix([
                vector([4,4,4,4]),
                vector([1,1,1,1]),
                vector([2,2,2,2]),
                vector([3,3,3,3])
        ])

        when:
        var result = matrix.reorderRows([3,0,1,2])

        then:
        result == reordered
    }

    def "test column retrieval"() {
        setup:
        var matrix = new Matrix([
                vector([1,1,1,1]),
                vector([2,2,2,2]),
                vector([3,3,3,3]),
                vector([4,4,4,4])
        ])
        var columns = [
                vector([1,2,3,4]),
                vector([1,2,3,4]),
                vector([1,2,3,4]),
                vector([1,2,3,4])
        ]

        when:
        var result = matrix.columns()

        then:
        result == columns
    }

    def Vector vector(List<Integer> values) {
        return new Vector(values)
    }
}
