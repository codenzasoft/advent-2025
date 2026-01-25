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

    def "test a theory"() {
        setup:
        var matrix = new Matrix([
                vector([0,0,0,1]),
                vector([0,1,0,1]),
                vector([0,0,1,0]),
                vector([0,0,1,1]),
                vector([1,0,1,0]),
                vector([1,1,0,0])
        ])
        var p1 = vector([3,5,4,7])
        var e1 = new Equation(matrix, p1)

        when:
        var r1 = Toggler.useLa4j(e1)
        var totals = matrix.getLa4jSum(r1)

        then:
        Math.floor(r1.sum()) == 10L
    }

    def Vector vector(List<Integer> values) {
        return new Vector(values)
    }
}
