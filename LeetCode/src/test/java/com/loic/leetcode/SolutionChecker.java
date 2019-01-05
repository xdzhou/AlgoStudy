package com.loic.leetcode;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.junit.jupiter.api.Assertions;

public final class SolutionChecker<I, O> {
  private final List<Function<I, O>> algos;

  private SolutionChecker(List<Function<I, O>> algos) {
    this.algos = algos;
  }

  public static <I, O> SolutionChecker<I, O> create(Function<I, O>... algos) {
    return new SolutionChecker<>(Arrays.asList(algos));
  }

  public SolutionChecker<I, O> check(I input, O... expected) {
    for (Function<I, O> algo : algos) {
      assertOutputs(algo.apply(input), expected);
    }
    return this;
  }

  public SolutionChecker<I, O> check(I input, BiConsumer<I, O> consumer) {
    for (Function<I, O> algo : algos) {
      consumer.accept(input, algo.apply(input));
    }
    return this;
  }

  public SolutionChecker<I, O> checkInput(I input) {
    if (algos.size() < 2) {
      throw new IllegalStateException("need at least 2 alogs");
    }
    O expected = algos.get(0).apply(input);
    for (int i = 1; i < algos.size(); i++) {
      Assertions.assertEquals(expected, algos.get(i).apply(input));
    }
    return this;
  }

  private void assertOutputs(O output, O... expected) {
    boolean match = Arrays.stream(expected)
      .map(e -> Objects.equals(e, output))
      .anyMatch(b -> b);
    Assertions.assertTrue(match, "expected outputs : " + Arrays.toString(expected) + " , but found " + output);
  }
}
