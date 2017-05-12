package com.sky.exercise;

import com.sky.common.CommonTest;
import com.sky.problem.Problem;
import org.testng.annotations.Test;

public class SumIsContinuousNumSequenceTest extends CommonTest<Integer, Integer> {
    @Test
    public void test() {
        check(15, 3);
        check(3, 1);
    }

    @Override
    public Problem<Integer, Integer> getAlgo() {
        return new SumIsContinuousNumSequence();
    }
}