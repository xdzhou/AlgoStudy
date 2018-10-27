package com.loic.leetcode.medium;

import com.loic.leetcode.medium.LetterCombinations;
import com.loic.solution.SolutionChecker;
import org.junit.Test;

import java.util.Arrays;

public class LetterCombinationsTest {

  @Test
  public void test() {
    SolutionChecker.create(new LetterCombinations())
      .check("23", Arrays.asList("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"));
  }
}