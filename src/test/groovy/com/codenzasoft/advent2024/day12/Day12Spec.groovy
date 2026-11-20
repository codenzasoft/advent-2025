package com.codenzasoft.advent2024.day12

import spock.lang.Specification

class Day12Spec extends Specification {

    def "test region discovery"() {
        setup:
        var lines = [
                "AAAA",
                "BBCD",
                "BBCC",
                "EEEC"
        ]

        when:
        var map = GardenMap.parse(lines)
        var regions = map.buildRegions()
        var regionA = regions.stream().filter {r -> r.plantType() == 'A'}.findFirst().orElse(null)
        var price = new Day12().computeTotalPerimeterPrice(lines)
        var sidesA = regionA.computeSides(map)
        var regionC = regions.stream().filter {r -> r.plantType() == 'C'}.findFirst().orElse(null)
        var sidesC = regionC.computeSides(map)
        var sidePrice = new Day12().computeTotalSidePrice(lines)

        then:
        regions.size() == 5
        regionA != null
        regionA.computePerimeter(map) == 10
        regionA.getArea() == 4
        price == 140
        sidesA.size() == 4
        sidesC.size() == 8
        sidePrice == 80
    }

    def "test region discovery with interior islands"() {
        setup:
        var lines = [
                "OOOOO",
                "OXOXO",
                "OOOOO",
                "OXOXO",
                "OOOOO"
        ]

        when:
        var map = GardenMap.parse(lines)
        var regions = map.buildRegions()
        var sidePrice = new Day12().computeTotalSidePrice(lines)

        then:
        regions.size() == 5
        sidePrice == 436
    }

    def "test price calc of larger example"() {
        setup:
        var lines = [
                "RRRRIICCFF",
                "RRRRIICCCF",
                "VVRRRCCFFF",
                "VVRCCCJFFF",
                "VVVVCJJCFE",
                "VVIVCCJJEE",
                "VVIIICJJEE",
                "MIIIIIJJEE",
                "MIIISIJEEE",
                "MMMISSJEEE"
        ]

        when:
        var price = new Day12().computeTotalPerimeterPrice(lines)

        then:
        price == 1930
    }
}
