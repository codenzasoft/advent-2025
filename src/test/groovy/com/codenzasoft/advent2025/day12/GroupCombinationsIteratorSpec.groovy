package com.codenzasoft.advent2025.day12

import spock.lang.Specification

class GroupCombinationsIteratorSpec extends Specification {

    def "test an iterator with 2 groups"() {
        setup:
        var g0 = [1, 2]
        var g1 = [1, 2, 3]

        when:
        var iter = new GroupCombinationsIterator([g0, g1])

        then:
        iter.hasNext()
        iter.next() == [1, 1]
        iter.next() == [1, 2]
        iter.next() == [1, 3]
        iter.next() == [2, 1]
        iter.next() == [2, 2]
        iter.next() == [2, 3]
        !iter.hasNext()
    }
}
