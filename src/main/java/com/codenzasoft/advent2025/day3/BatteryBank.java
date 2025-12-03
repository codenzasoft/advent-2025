package com.codenzasoft.advent2025.day3;

public record BatteryBank(String rating) {

  private static final int BASE_10 = 10;

  public int getMaxJoltage() {
    final String highOrderDigits = rating.substring(0, rating.length() - 1);
    final int highOrder = getMaxDigit(highOrderDigits);
    final int highIndex = highOrderDigits.indexOf(Character.forDigit(highOrder, BASE_10));
    final String lowOrderDigits = rating.substring(highIndex + 1);
    final int lowOrder = getMaxDigit(lowOrderDigits);
    return highOrder * BASE_10 + lowOrder;
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
