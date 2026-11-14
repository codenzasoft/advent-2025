package com.codenzasoft.advent2024.day7;

public record OperatorPermutation(Operator[] permutation) {

  public static OperatorPermutation with(int numOperators, int permutationNumber) {
    final Operator[] theOperators = Operator.values();
    String operatorMask = Integer.toString(permutationNumber, theOperators.length);
    // pad with zeros
    while (operatorMask.length() < numOperators) {
      operatorMask = "0" + operatorMask;
    }
    final Operator[] operators = new Operator[numOperators];
    for (int i = 0; i < operatorMask.length(); i++) {
      final String text = String.valueOf(operatorMask.charAt(i));
      final int num = Integer.parseInt(text);
      operators[i] = theOperators[num];
    }
    return new OperatorPermutation(operators);
  }
}
