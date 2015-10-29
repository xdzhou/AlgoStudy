package com.sky.codingame.match;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TeadsQ1Test
{
	private TeadsQ1 algo;

	@BeforeTest
	public void init()
	{
		algo = new TeadsQ1();
	}
	
	private void chargeData(String fileIn)
	{
		String floder = "src/resources/com/sky/codingame/training";
		try
		{
			Scanner in = new Scanner(new File(floder+File.separator+fileIn));
			int n = in.nextInt();
	        for (int i = 0; i < n; i++) 
	        {
	            int xi = in.nextInt();
	            int yi = in.nextInt();
	            algo.addNewLien(xi, yi);
	        }
			in.close();
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void test1()
	{
		algo.clear();
		chargeData("ShortestTransformPathIn1.txt");
		Assert.assertEquals(algo.getShortestTransformPathLength(), 2);
	}
	
	@Test
	public void test2()
	{
		algo.clear();
		chargeData("ShortestTransformPathIn2.txt");
		Assert.assertEquals(algo.getShortestTransformPathLength(), 2);
	}
	
	@Test
	public void test3()
	{
		algo.clear();
		chargeData("ShortestTransformPathIn3.txt");
		Assert.assertEquals(algo.getShortestTransformPathLength(), 3);
	}
	
	@Test
	public void test4()
	{
		algo.clear();
		chargeData("ShortestTransformPathIn4.txt");
		Assert.assertEquals(algo.getShortestTransformPathLength(), 5);
	}
	
	@Test
	public void test5()
	{
		algo.clear();
		chargeData("ShortestTransformPathIn5.txt");
		Assert.assertEquals(algo.getShortestTransformPathLength(), 5);
	}
	
	@Test
	public void test6()
	{
		algo.clear();
		chargeData("ShortestTransformPathIn6.txt");
		Assert.assertEquals(algo.getShortestTransformPathLength(), 7);
	}
	
	@Test
	public void test7()
	{
		algo.clear();
		chargeData("ShortestTransformPathIn7.txt");
		Assert.assertEquals(algo.getShortestTransformPathLength(), 15);
	}
	
	@Test
	public void test8()
	{
		algo.clear();
		chargeData("ShortestTransformPathIn8.txt");
		Assert.assertEquals(algo.getShortestTransformPathLength(), 9);
	}
	
	@Test
	public void test9()
	{
		algo.clear();
		chargeData("ShortestTransformPathIn9.txt");
		Assert.assertEquals(algo.getShortestTransformPathLength(), 15);
	}
	
	@Test
	public void test10()
	{
		algo.clear();
		chargeData("ShortestTransformPathIn10.txt");
		Assert.assertEquals(algo.getShortestTransformPathLength(), 5);
	}
}
