package com.codenzasoft.advent2025.day7;

import java.awt.Point;
import java.util.Stack;

public class Node {
  private final Point location;
  private Node left;
  private Node right;

  public Node(final Point location) {
    this.location = location;
  }

  public Point getLocation() {
    return location;
  }

  public Node getLeft() {
    return left;
  }

  public void setLeft(final Node left) {
    this.left = left;
  }

  public Node getRight() {
    return right;
  }

  public void setRight(final Node right) {
    this.right = right;
  }

  public boolean isLeaf() {
    return left == null && right == null;
  }

  public int countLeafNodesRecursive() {
    if (isLeaf()) {
      return 1;
    }
    int leafNodes = 0;
    if (left != null) {
      leafNodes += left.countLeafNodesRecursive();
    }
    if (right != null) {
      leafNodes += right.countLeafNodesRecursive();
    }
    return leafNodes;
  }

  public int findLeafNodes() {
    int leaves = 0;
    final Stack<Node> stack = new Stack<>();
    stack.push(this);
    while (!stack.isEmpty()) {
      final Node node = stack.pop();
      if (node.isLeaf()) {
        leaves++;
      } else {
        stack.push(node.left);
        stack.push(node.right);
      }
    }
    return leaves;
  }
}
