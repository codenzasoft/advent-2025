package com.codenzasoft.advent2025.day12

import spock.lang.Specification

class TetrisSpec extends Specification {

    def "test input parsing"() {
        setup:
        var lines = getSampleInput()

        when:
        var groups = Tetris.buildGroups(lines)
        var presents = Tetris.buildPresents(groups)
        var regions = Tetris.buildRegions(groups)

        then:
        groups.size() == 7
        presents.size() == 6
        regions.size() == 3
    }

    def "test a present quantities"() {
        setup:
        var lines = getSampleInput()
        var groups = Tetris.buildGroups(lines)
        var presents = Tetris.buildPresents(groups)
        var p0 = presents.get(0)
        var p1 = presents.get(1)
        var p2 = presents.get(2)
        var p3 = presents.get(3)
        var p4 = presents.get(4)
        var p5 = presents.get(5)
        var regions = Tetris.buildRegions(groups)
        var r0 = regions.get(0)
        var r1 = regions.get(1)
        var r2 = regions.get(2)

        when:
        var q0 = r0.getRequiredQuantities(presents)
        var q1 = r1.getRequiredQuantities(presents)
        var q2 = r2.getRequiredQuantities(presents)

        then:
        q0 == [p4, p4]
        q1 == [p0, p2, p4, p4, p5, p5]
        q2 == [p0, p2, p4, p4, p4, p5, p5]
    }

    def "test a single fitting"() {
        setup:
        var lines = getSampleInput()
        var groups = Tetris.buildGroups(lines)
        var presents = Tetris.buildPresents(groups)
        var regions = Tetris.buildRegions(groups)
        var r0 = regions.get(0)
        var p4 = presents.get(4)

        when:
        var fits = r0.fitPermutation([p4, p4.rotate90().rotate90()])
        var nope = r0.fitPermutation([p4, p4])

        then:
        fits
        !nope
    }

    def "test a trial fitting"() {
        setup:
        var lines = getSampleInput()
        var groups = Tetris.buildGroups(lines)
        var presents = Tetris.buildPresents(groups)
        var regions = Tetris.buildRegions(groups)
        var r0 = regions.get(0)

        when:
        var fits = r0.canFit(presents)

        then:
        fits
    }

//    def "the sample run"() {
//        setup:
//        var lines = getSampleInput()
//        var groups = Tetris.buildGroups(lines)
//        var presents = Tetris.buildPresents(groups)
//        var regions = Tetris.buildRegions(groups)
//
//        when:
//        var fits = Tetris.part1(presents, regions)
//
//        then:
//        fits == 2
//    }

    def getSampleInput() {
        return [
        "0:",
        "###",
        "##.",
        "##.",
        "",
        "1:",
        "###",
        "##.",
        ".##",
        "",
        "2:",
        ".##",
        "###",
        "##.",
        "",
        "3:",
        "##.",
        "###",
        "##.",
        "",
        "4:",
        "###",
        "#..",
        "###",
        "",
        "5:",
        "###",
        ".#.",
        "###",
        "",
        "4x4: 0 0 0 0 2 0",
        "12x5: 1 0 1 0 2 2",
        "12x5: 1 0 1 0 3 2"
                ]
    }
}
