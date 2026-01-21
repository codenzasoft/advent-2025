package com.codenzasoft.advent2025.day10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record Vector(List<Integer> values) {

  public static Vector withAll(int size, int value) {
    final List<Integer> list = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      list.add(value);
    }
    return new Vector(list);
  }

  public double[] toJama() {
    final double[] result = new double[values().size()];
    for (int i = 0; i < values().size(); i++) {
      result[i] = values().get(i);
    }
    return result;
  }

  public int getBinaryValue() {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < values().size(); i++) {
      builder.append(values().get(i));
    }
    return Integer.parseInt(builder.toString(), 2);
  }

  public int getValue(int index) {
    return values.get(index);
  }

  public int sum() {
    return values.stream().mapToInt(i -> i).sum();
  }

  public int minValue() {
    return values.stream().min(Comparator.naturalOrder()).orElse(0);
  }

  public Vector multiply(final int value) {
    final List<Integer> newValues = new ArrayList<>();
    for (int i = 0; i < values.size(); i++) {
      newValues.add(values.get(i) * value);
    }
    return new Vector(newValues);
  }

  public List<Integer> getAscendingIncdicies() {
    return getSortedIndicies(Comparator.comparingInt(values::get));
  }

  public List<Integer> getSortedIndicies(final Comparator<Integer> comparator) {
    return IntStream.range(0, values.size())
        .boxed()
        .sorted(comparator)
        .collect(Collectors.toList());
  }

  public Vector reorder(final List<Integer> indicies) {
    final List<Integer> newValues = new ArrayList<>();
    for (final Integer index : indicies) {
      newValues.add(values.get(index));
    }
    return new Vector(newValues);
  }

  public Vector subtract(final Vector vector) {
    final List<Integer> newValues = new ArrayList<>();
    for (int i = 0; i < values.size(); i++) {
      newValues.add(values.get(i) - vector.values.get(i));
    }
    return new Vector(newValues);
  }

  public Vector add(final Vector vector) {
    final List<Integer> newValues = new ArrayList<>();
    for (int i = 0; i < values().size(); i++) {
      newValues.add(vector.values().get(i) + values().get(i));
    }
    return new Vector(newValues);
  }

  public int getMaxCoefficient(final Vector vector) {
    final List<Integer> coefficients = new ArrayList<>();
    for (int i = 0; i < values().size(); i++) {
      if (values().get(i) != 0) {
        coefficients.add(vector.values().get(i) / values().get(i));
      }
    }
    return coefficients.stream().mapToInt(Integer::intValue).min().orElse(0);
  }

  public boolean greaterThan(final Vector vector) {
    for (int i = 0; i < values().size(); i++) {
      if (values().get(i) > vector.values().get(i)) {
        return true;
      }
    }
    return false;
  }

  public boolean contains(final int value) {
    if (values().contains(value)) {
      return true;
    }
    return false;
  }

  public List<Integer> indiciesOf(final int value) {
    final List<Integer> indicies = new ArrayList<>();
    for (int i = 0; i < values().size(); i++) {
      if (values().get(i) == value) {
        indicies.add(i);
      }
    }
    return indicies;
  }

  public Vector removeColumn(final int column) {
    final List<Integer> newValues = new ArrayList<>(values());
    newValues.remove(column);
    return new Vector(newValues);
  }

  public Vector removeIndicies(final List<Integer> indicies) {
    final List<Integer> newValues = new ArrayList<>();
    for (int i = 0; i < values().size(); i++) {
      if (!indicies.contains(i)) {
        newValues.add(values().get(i));
      }
    }
    return new Vector(newValues);
  }
}
