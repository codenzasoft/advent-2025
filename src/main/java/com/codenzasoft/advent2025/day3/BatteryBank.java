package com.codenzasoft.advent2025.day3;

public record BatteryBank(String rating) {

  private static final int BASE_10 = 10;

  public long getMaxJoltage(int numDigits) {
    long joltage = 0;
    int used = 0;
    for (int remainingDigits = numDigits; remainingDigits > 0; remainingDigits--) {
      final String digits = rating.substring(used, rating.length() - remainingDigits + 1);
      final int digit = getMaxDigit(digits);
      used = used + digits.indexOf(Character.forDigit(digit, BASE_10)) + 1;
      joltage = joltage * BASE_10 + digit;
    }
    return joltage;
  }

  private int getMaxDigit(final String digits) {
    return digits
        .chars()
        .mapToObj(i -> (char) i)
        .mapToInt(c -> Character.digit(c, BASE_10))
        .max()
        .orElse(0);
  }
}
