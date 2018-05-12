package com.loic.dynamicProgramming;

import com.loic.solution.SolutionChecker;
import org.testng.annotations.Test;

import static com.loic.solution.TestHelper.toArray;

public class LongestIncreasingSubsequenceTest {

  @Test
  public void test() {
    SolutionChecker.create(new LongestIncreasingSubsequence())
        .check(toArray(4, 5, 6, 1, 2, 3), 3)
        .check(toArray(1, 2, 3, 4, 5, 6, 7, 8, 9), 9)
        .check(toArray(9, 8, 7, 6, 5, 4, 3, 2, 1), 1)
        .check(toArray(7, 7, 7, 7, 7), 5);
  }
}
