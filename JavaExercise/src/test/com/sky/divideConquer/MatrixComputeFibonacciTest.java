package com.sky.divideConquer;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.sky.common.CommonTest;
import com.sky.divideConquer.MatrixComputeFibonacci;
public class MatrixComputeFibonacciTest extends CommonTest<Integer, Integer>
{
	@Test
	public void test()
	{
		check(10, null);
		check(100, null);
		check(1000, null);
		check(10000, null);
		check(100000, null);
	}

	@Override
	@BeforeTest
	public void init()
	{
		setAlgo(new MatrixComputeFibonacci());
	}
}