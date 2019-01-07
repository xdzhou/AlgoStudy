package com.loic.leetcode.easy;

import com.loic.helper.ListNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MergeTwoSortedListsTest {

  @Test
  void merge() {
    testCase(ListNode.createNodes(0), ListNode.createNodes(1, 2, 3), ListNode.createNodes(0, 1, 2, 3));
    testCase(ListNode.createNodes(1, 3, 4), ListNode.createNodes(1, 2, 4), ListNode.createNodes(1, 1, 2, 3, 4, 4));
  }

  private void testCase(ListNode l1, ListNode l2, ListNode merged) {
    Assertions.assertTrue(merged.equalTo(MergeTwoSortedLists.merge(l1, l2)));
  }
}