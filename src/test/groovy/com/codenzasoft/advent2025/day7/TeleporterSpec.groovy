package com.codenzasoft.advent2025.day7

import org.codehaus.groovy.transform.ASTTestTransformation
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
        var exitCount = Teleporter.countExits(manifold)
        var leafCount = Teleporter.buildTree(manifold)
        var compressedCount = Teleporter.compressExits(manifold)

        then:
        exitCount == 40
        leafCount == 40
        compressedCount == 40
    }

    def "test beam combination"() {
        setup:
        var b1 = new Beam(5, 1)
        var b2 = new Beam(5, 2)
        var b3 = new Beam(7 , 4)

        when:
        var c1 = b1.combine(b2)
        var c2 = b1.combine(b3)

        then:
        c1.isPresent()
        c1.get().edges() == 3
        c1.get().x() == 5
        c2.isEmpty()
    }

    def "test beam compression"() {
        setup:
        var b1 = new Beam(5, 1)
        var b2 = new Beam(5, 2)
        var b3 = new Beam(5 , 3)
        var list = new LinkedList()
        list.add(b1)
        list.add(b2)
        list.add(b3)

        when:
        var c = Teleporter.compress(list)

        then:
        c.size() == 1
        c.get(0).x() == 5
        c.get(0).edges() == 6
    }

}
