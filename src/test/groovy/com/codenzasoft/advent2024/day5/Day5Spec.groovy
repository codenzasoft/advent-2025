package com.codenzasoft.advent2024.day5

import spock.lang.Specification

class Day5Spec extends Specification {

    def "test that correct orders are found and totaled"() {
        setup:
        var lines = [
                "47|53",
                "97|13",
                "97|61",
                "97|47",
                "75|29",
                "61|13",
                "75|53",
                "29|13",
                "97|29",
                "53|29",
                "61|53",
                "97|53",
                "61|29",
                "47|13",
                "75|47",
                "97|75",
                "47|61",
                "75|61",
                "47|29",
                "75|13",
                "53|13",
"",
                "75,47,61,53,29",
                "97,61,53,29,13",
                "75,29,13",
                "75,97,47,61,53",
                "61,13,29",
                "97,13,75,29,47"
        ]

        when:
        var data = EdgesAndPaths.parse(lines)
        var result = new Day5().validMiddlePageSum(data)
        var fixed = new Day5().correctAndSumInvalidPaths(data)

        then:
        result == 143
        fixed == 123
    }
}
