package com.codenzasoft.advent2025.day7

import spock.lang.Specification

class TeleporterSpec extends Specification {

    def "test beam tracing"() {
        setup:
        var lines = [
                ".......S.......",
                "...............",
                ".......^.......",
                "...............",
                "......^.^......",
                "...............",
                ".....^.^.^.....",
                "...............",
                "....^.^...^....",
                "...............",
                "...^.^...^.^...",
                "...............",
                "..^...^.....^..",
                "...............",
                ".^.^.^.^.^...^.",
                "..............."
        ]
        var manifold = new Manifold(lines)

        when:
        var result = Teleporter.traceBeams(manifold)

        then:
        result == 21
    }
}
