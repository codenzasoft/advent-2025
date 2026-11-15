package com.codenzasoft.advent2024.day7;

import java.util.List;

public record Equation(long testValue, long[] operands) {

  public static Equation parse(String input) {
    final String[] valueAndOperands = input.split(":");
    if (valueAndOperands.length != 2) {
      throw new IllegalArgumentException("Invalid input: " + input);
    }
    final long testValue = Long.parseLong(valueAndOperands[0]);
    final String[] operandStrings = valueAndOperands[1].trim().split(" ");
    final long[] operands = new long[operandStrings.length];
    for (int i = 0; i < operandStrings.length; i++) {
      final long operand = Long.parseLong(operandStrings[i].trim());
      operands[i] = operand;
    }
    return new Equation(testValue, operands);
  }

  public long valueWith(Operator[] operators) {
    long value = operands[0];
    for (int i = 0; i < operators.length; i++) {
      final Operator operator = operators[i];
      value = operator.apply(value, operands[i + 1]);
    }
    return value;
  }

  public boolean isSolved(Operator[] operators) {
    return testValue == valueWith(operators);
  }

  public int getNumberOfOperands() {
    return operands.length;
  }

  public boolean isSolvableUsing(List<Operator> operators) {
    final int numOperation = getNumberOfOperands() - 1;
    long numPermutations = (long) Math.pow(operators.size(), numOperation);
    for (int i = 0; i < numPermutations; i++) {
      final OperatorPermutation permutation = OperatorPermutation.with(numOperation, i, operators);
      if (isSolved(permutation.permutation())) {
        return true;
      }
    }
    return false;
  }
}
