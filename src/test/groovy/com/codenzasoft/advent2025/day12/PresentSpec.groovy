package com.codenzasoft.advent2025.day12


import spock.lang.Specification

class PresentSpec extends Specification {

    def "test present parsing"() {
        setup:
        def lines = getTestPresentInput()

        when:
        def present = Present.parse(lines)

        then:
        present.getWidth() == 3
        present.getHeight() == 3
        present.id() == "3"
        present.toString() == "##.\n###\n##.\n"
    }

    def "test present rotate"() {
        setup:
        def present = buildTestPresent()

        when:
        def r90 = present.rotate90();

        then:
        r90.getWidth() == 3
        r90.getHeight() == 3
        r90.id() == "3"
        r90.toString() == "###\n###\n.#.\n"
    }

    def "test present rotations"() {
        setup:
        def present = buildTestPresent()

        when:
        def rotations = present.getRotations();
        def r0 = rotations.get(0)
        def r90 = rotations.get(1)
        def r180 = rotations.get(2)
        def r270 = rotations.get(3)

        then:
        rotations.stream().allMatch(r -> r.getWidth() == 3)
        rotations.stream().allMatch(r -> r.getHeight() == 3)
        rotations.stream().allMatch(r -> r.id() == "3")
        r0 == present
        r90.toString() == "###\n###\n.#.\n"
        r180.toString() == ".##\n###\n.##\n"
        r270.toString() == ".#.\n###\n###\n"
    }

    def static getTestPresentInput() {
        return List.of(
                "3:",
                "##.",
                "###",
                "##.")
    }

    def static buildTestPresent() {
        return Present.parse(getTestPresentInput())
    }
}
