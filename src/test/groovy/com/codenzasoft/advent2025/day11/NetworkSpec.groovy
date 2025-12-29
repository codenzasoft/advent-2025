package com.codenzasoft.advent2025.day11

import spock.lang.Specification

class NetworkSpec extends Specification {

    def "test graph building and path finding"() {
        setup:
        var lines = [
                "aaa: you hhh",
                "you: bbb ccc",
                "bbb: ddd eee",
                "ccc: ddd eee fff",
                "ddd: ggg",
                "eee: out",
                "fff: out",
                "ggg: out",
                "hhh: ccc fff iii",
                "iii: out"
        ]
        var devices = lines.stream().map(Device::parse).toList();
        var graph = Network.buildGraph(devices)

        when:
        var result = Network.part1(devices)

        then:
        result == 5
    }
}
