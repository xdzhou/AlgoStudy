package com.sky.dynamicProgramming;

import com.sky.common.CommonTest;
import com.sky.solution.SolutionProvider;
import org.testng.annotations.Test;

public class DPLCSTest extends CommonTest<String[], String> {
    @Test
    public void test() {
        check(transform("abcdefg", "apcmzf"), "acf");
        check(transform("123654987", "zfddsf"), (String) null);
        check(transform("1234", "34"), "34");
    }

    @Override
    public SolutionProvider<String[], String> getAlgo() {
        return new DPLCS();
    }
}
