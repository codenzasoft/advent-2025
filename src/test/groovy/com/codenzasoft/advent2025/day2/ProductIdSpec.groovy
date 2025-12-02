package com.codenzasoft.advent2025.day2

import spock.lang.Specification

class ProductIdSpec extends Specification {

    def "test identification of mirror product identifiers"() {
        expect:
        !(new ProductId(123).isMirrorSequence())
        !(new ProductId(1).isMirrorSequence())
        new ProductId(123123).isMirrorSequence()
        new ProductId(11).isMirrorSequence()
    }

    def "test identification of repeated subsequence identifiers"() {
        expect:
        !(new ProductId(123).isRepeatedSequence())
        !(new ProductId(1).isRepeatedSequence())
        new ProductId(12341234).isRepeatedSequence()
        new ProductId(123123123).isRepeatedSequence()
        new ProductId(1212121212).isRepeatedSequence()
        new ProductId(1111111).isRepeatedSequence()
    }
}
