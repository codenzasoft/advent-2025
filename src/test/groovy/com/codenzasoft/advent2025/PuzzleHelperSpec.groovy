package com.codenzasoft.advent2025

import spock.lang.Specification

class PuzzleHelperSpec extends Specification {

    def "test identification of mirror product identifiers"() {
        expect:
        !(PuzzleHelper.isHalfAndHalf('123'))
        !(PuzzleHelper.isHalfAndHalf('1'))
        PuzzleHelper.isHalfAndHalf('123123')
        PuzzleHelper.isHalfAndHalf('11')
    }

    def "test identification of repeated subsequence identifiers"() {
        expect:
        !(PuzzleHelper.isPeriodicSequence('123'))
        !(PuzzleHelper.isPeriodicSequence('1'))
        PuzzleHelper.isPeriodicSequence('12341234')
        PuzzleHelper.isPeriodicSequence('123123123')
        PuzzleHelper.isPeriodicSequence('1212121212')
        PuzzleHelper.isPeriodicSequence('1111111')
    }

    def "test parse and map"() {
        setup:
        var input = " 123 456  789 "
        var mapper = Long::parseLong

        when:
        var result = PuzzleHelper.parseAndMap(input, mapper)

        then:
        result == [123L, 456L, 789L]
    }

}
