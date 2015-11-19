package com.sky.recursion;

import org.testng.annotations.BeforeTest;

import com.sky.common.CommonTest;

public class CombinationParenthesTest extends CommonTest<Integer, Integer>
{

	@Override
	@BeforeTest
	public void init()
	{
		setAlgo(new CombinationParenthes());
	}

	public void test()
	{
		check(1, 1);
		check(2, 2);
		check(3, 5);
	}
}