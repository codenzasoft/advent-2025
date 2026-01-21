package com.codenzasoft.advent2025.day10

import spock.lang.Specification

class EquationSpec extends Specification {

    def "test reducing a solution"() {
        setup:
        var matrix = new Matrix([
                vector([1,1,1,1,1]),
                vector([2,0,9,0,2]),
                vector([3,0,3,3,3]),
                vector([4,0,8,0,4]),
                vector([5,5,5,0,5]),
                vector([6,6,6,6,6])
                ])
        var total = vector([1,0,3,0,5])
        var reduced = new Matrix([
                vector([2,9,2]),
                vector([4,8,4]),
        ])
        var reducedTotal = vector([1,3,5])
        var solution = new Equation(matrix, total)

        when:
        var result = solution.reduce()

        then:
        result.getMatrix() == reduced
        result.getDesiredTotal() == reducedTotal
    }

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
        var totals = vector([5,4,3,2,1])
        var solution = new Equation(matrix, totals)


        when:
        var result = solution.reorderColumns()

        then:
        result.getMatrix() == reordered
        result.getDesiredTotal() == vector([1,2,3,4,5])
    }

    def "test re-ordering rows"() {
        setup:
        var matrix = new Matrix([
                vector([0,0,0,1]),
                vector([1,0,1,0]),
                vector([1,1,0,0]),
                vector([0,0,1,1]),
                vector([0,1,0,1])
        ])
        var reordered = new Matrix([
                vector([1,1,0,0]),
                vector([1,0,1,0]),
                vector([0,1,0,1]),
                vector([0,0,1,1]),
                vector([0,0,0,1])
        ])
        var totals = vector([5,4,3,2])
        var solution = new Equation(matrix, totals)


        when:
        var result = solution.reorderRows()

        then:
        result.getMatrix() == reordered
        result.getDesiredTotal() == vector([5,4,3,2])
    }

    def Vector vector(List<Integer> values) {
        return new Vector(values)
    }
}
