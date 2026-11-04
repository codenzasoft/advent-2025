package com.codenzasoft.advent2024.day5;

import java.util.HashSet;
import java.util.Set;

public class PageNumber implements Comparable<PageNumber> {

  final int pageNumber;
  final Set<Integer> higherPages = new HashSet<>();

  public static PageNumber fromEdge(final PageEdge edge) {
    return new PageNumber(edge.from());
  }

  public PageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public int page() {
    return pageNumber;
  }

  public void addHigherPage(int page) {
    higherPages.add(page);
  }

  @Override
  public int compareTo(PageNumber other) {
    if (higherPages.contains(other.pageNumber)) {
      return -1;
    } else if (pageNumber == other.pageNumber) {
      return 0;
    } else {
      return 1;
    }
  }
}
