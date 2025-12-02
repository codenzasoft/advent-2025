package com.codenzasoft.advent2025.day2

import spock.lang.Specification

class ProductIdSpec extends Specification {

    def "test validity of product identifiers"() {
        expect:
        new ProductId(123).isValid()
        new ProductId(1).isValid()
        !(new ProductId(123123).isValid())
        !(new ProductId(11).isValid())
    }
}
