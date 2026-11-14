package com.codenzasoft.advent2024.day7;

import java.util.List;

public record OperatorPermutation(Operator[] permutation) {

  public static OperatorPermutation with(int numOperators, int permutationNumber, List<Operator> possibleOperators) {
    String operatorMask = Integer.toString(permutationNumber, possibleOperators.size());
    // pad with zeros
    while (operatorMask.length() < numOperators) {
      operatorMask = "0" + operatorMask;
    }
    final Operator[] operators = new Operator[numOperators];
    for (int i = 0; i < operatorMask.length(); i++) {
      final String text = String.valueOf(operatorMask.charAt(i));
      final int num = Integer.parseInt(text);
      operators[i] = possibleOperators.get(num);
    }
    return new OperatorPermutation(operators);
  }
}
