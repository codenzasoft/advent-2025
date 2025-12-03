package com.codenzasoft.advent2025.day3

import spock.lang.Specification

class BatteryBankSpec extends Specification {

    def "test the max joltage of a battery bank"() {
        expect:
        new BatteryBank("987654321111111").getMaxJoltage() == 98
        new BatteryBank("811111111111119").getMaxJoltage() == 89
        new BatteryBank("234234234234278").getMaxJoltage() == 78
        new BatteryBank("818181911112111").getMaxJoltage() == 92
    }
}
