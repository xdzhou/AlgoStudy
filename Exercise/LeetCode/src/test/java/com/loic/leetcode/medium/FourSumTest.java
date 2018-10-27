package com.loic.leetcode.medium;

import com.loic.leetcode.medium.FourSum;
import com.loic.solution.BiSolutionChecker;
import com.loic.solution.TestHelper;
import org.junit.Test;

import java.util.Arrays;

public class FourSumTest {

  @Test
  public void test() {
    BiSolutionChecker.create(new FourSum())
      .check(TestHelper.toIntArray(1, 0, -1, 0, -2, 2), 0,
        Arrays.asList(Arrays.asList(-2, -1, 1, 2), Arrays.asList(-2, 0, 0, 2), Arrays.asList(-1, 0, 0, 1)));
  }
}