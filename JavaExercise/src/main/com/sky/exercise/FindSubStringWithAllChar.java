package com.sky.exercise;

/**
 * 一串首尾相连的珠子(m 个)，有N 种颜色(N<=10)，
 * 设计一个算法，取出其中一段，要求包含所有N 中颜色，并使长度最短。
 * 并分析时间复杂度与空间复杂度。
 *
 */
public class FindSubStringWithAllChar
{

	public String findAllChar(String content, int N)
	{
		if(content == null || content.isEmpty())
		{
			return null;
		}
		else if(N == 1)
		{
			return content.substring(0, 1);
		}
		//the number occur times in substring
		int[] occurTimes = new int[N];
		//find the substring with all chars
		int cacheTail = -1, cacheHead = -1;
		//the 2 cursor of substring
		int tail = 0, head = 0;
		for(; head<content.length(); head++)
		{
			int curNum = getNumber(head, content);
			occurTimes[curNum] ++;
			for(; tail <= head; tail++)
			{
				int tailNum = getNumber(tail, content);
				if(occurTimes[tailNum] > 1)
				{
					occurTimes[tailNum] --;
				}
				else
				{
					break;
				}
			}
			if(head - tail + 1 >= N && isComplet(occurTimes))
			{
				if(cacheTail == -1 || (head - tail) < (cacheHead - cacheTail))
				{
					cacheHead = head;
					cacheTail = tail;
					if(cacheHead - cacheTail + 1 == N) //the shortest substring have found
					{
						break;
					}
				}
			}
		}
		if(cacheHead != -1)
		{
			return content.substring(cacheTail, cacheHead + 1);
		}
		else
		{
			return null;
		}
	}
	
	private boolean isComplet(int[] num)
	{
		for(int value : num)
		{
			if(value <= 0)
			{
				return false;
			}
		}
		return true;
	}
	
	private int getNumber(int index, String content)
	{
		return content.charAt(index) - '0';
	}
}