package com.codenzasoft.advent2025.day6

import spock.lang.Specification

class MatrixSpec extends Specification {

    def "test building a matrix"() {
        setup:
        var lines = [
                "123 328  51 64",
                "45 64  387 23",
                "6 98  215 314",
        ]
        var builder = new Matrix.MatrixBuilder()

        when:
        lines.forEach {l -> builder.addStringRow(l)}
        var matrix = builder.build()

        then:
        matrix.getColumnCount() == 4
        matrix.getRowCount() == 3
    }

    def "test applying operations to columns"() {
        setup:
        var lines = [
                "123 328  51 64",
                "45 64  387 23",
                "6 98  215 314",
        ]
        var builder = new Matrix.MatrixBuilder()
        lines.forEach {l -> builder.addStringRow(l)}
        var matrix = builder.build()

        when:
        var c1 = matrix.applyToColum(0, Operation.MULTIPLICATION)
        var c2 = matrix.applyToColum(1, Operation.ADDITION)
        var c3 = matrix.applyToColum(2, Operation.MULTIPLICATION)
        var c4 = matrix.applyToColum(3, Operation.ADDITION)

        then:
        c1 == 33210
        c2 == 490
        c3 == 4243455
        c4 == 401
        ProblemSolver.part1(matrix, List.of(Operation.MULTIPLICATION, Operation.ADDITION, Operation.MULTIPLICATION, Operation.ADDITION)) == 4277556L
    }
}
