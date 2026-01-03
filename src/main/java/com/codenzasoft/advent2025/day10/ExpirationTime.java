package com.codenzasoft.advent2025.day10;

import java.util.concurrent.TimeUnit;

public record ExpirationTime(long epochMillis) {

  public static ExpirationTime after(final TimeUnit timeUnit, final long time) {
    return new ExpirationTime(System.currentTimeMillis() + timeUnit.toMillis(time));
  }

  public static ExpirationTime never() {
    return new ExpirationTime(Long.MAX_VALUE);
  }

  public boolean isExpired() {
    return System.currentTimeMillis() > epochMillis;
  }
}
