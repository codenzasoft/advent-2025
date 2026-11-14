package com.codenzasoft.advent2024.day7

import spock.lang.Specification

class Day7Spec extends Specification {

    def "test that an equation can be parsed"() {
        when:
        var equation = Equation.parse("3267: 81 40 27")
        var operators = new Operator[2]
        operators[0] = Operator.ADDITION
        operators[1] = Operator.ADDITION

        then:
        equation.testValue() == 3267L
        equation.operands() == [81L, 40L, 27L]
        equation.valueWith(operators) == 81L + 40L + 27L
        equation.isSolvableUsing(List.of(Operator.ADDITION, Operator.MULTIPLICATION))
    }

    def "test that concatenation works"() {
        when:
        var equation = Equation.parse("7290: 6 8 6 15")
        var operators = new Operator[3]
        operators[0] = Operator.MULTIPLICATION
        operators[1] = Operator.CONCATENATION
        operators[2] = Operator.MULTIPLICATION

        then:
        equation.testValue() == 7290L
        equation.operands() == [6L, 8L, 6L, 15L]
        equation.valueWith(operators) == 7290L
        equation.isSolvableUsing(List.of(Operator.MULTIPLICATION, Operator.CONCATENATION, Operator.MULTIPLICATION))
    }

    def "test that a permutation can be created"() {
        when:
        var permutation = OperatorPermutation.with(3, 0, List.of(Operator.ADDITION, Operator.MULTIPLICATION))

        then:
        permutation.permutation()[0] == Operator.ADDITION
        permutation.permutation()[1] == Operator.ADDITION
        permutation.permutation()[2] == Operator.ADDITION
    }

    def "test sample puzzle"() {
        setup:
        var lines = [
                "190: 10 19",
                "3267: 81 40 27",
                "83: 17 5",
                "156: 15 6",
                "7290: 6 8 6 15",
                "161011: 16 10 13",
                "192: 17 8 14",
                "21037: 9 7 18 13",
                "292: 11 6 16 20"
        ]

        when:
        var result = new Day7().solvePartOne(Day7.parseEquations(lines))
        var two = new Day7().solvePartTwo(Day7.parseEquations(lines))

        then:
        result == 3749
        two == 11387
    }
}
