package com.sky.exercise;

import com.sky.common.CommonTest;
import com.sky.problem.Problem;
import org.testng.annotations.Test;

public class UglyNumberTest extends CommonTest<Integer, Integer> {

    @Test
    public void test() {
        check(3, (Integer) null);
        check(10, (Integer) null);
        check(99, (Integer) null);
        check(911, (Integer) null);
    }

    @Override
    public Problem<Integer, Integer> getAlgo() {
        return new UglyNumber();
    }
}
