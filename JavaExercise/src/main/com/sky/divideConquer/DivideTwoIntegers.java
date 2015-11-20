package com.sky.divideConquer;

/**
 * Divide two integers without using multiplication, division and mod operator.
 * 
 * @link https://leetcode.com/problems/divide-two-integers/
 */
public class DivideTwoIntegers
{
	public int divide(int a, int b)
    {
        if(b == 0)
        {
            return Integer.MAX_VALUE;
        }
        if(b == 1)
        {
            return a;
        }
        if(b == -1)
        {
            return a == Integer.MIN_VALUE ? Integer.MAX_VALUE : -a;
        }
        if(a == 0)
        {
            return 0;
        }
        if(b == Integer.MIN_VALUE)
        {
            return a == Integer.MIN_VALUE ? 1 : 0;
        }

        boolean negative = (a < 0 && b > 0) || (a > 0 && b < 0);
        if(a == Integer.MIN_VALUE)
        {
        	int[] p = divideIntern(Integer.MAX_VALUE, Math.abs(b));
        	int result = p[0];
        	if(1 + p[1] >= Math.abs(b))
        	{
        		result ++;
        	}
        	return negative ? - result : result;
        }
        else 
        {
        	int[] p = divideIntern(Math.abs(a), Math.abs(b));
            return  negative ? - p[0] : p[0];
		}
    }

    private int[] divideIntern(int a, int b)
    {
        if(a == b)
        {
        	System.out.println(a+" / "+b+" = "+1+" ... "+0);
            return new int[] {1, 0};
        }
        if(a < b)
        {
        	System.out.println(a+" / "+b+" = "+0+" ... "+a);
            return new int[] {0, a};
        }
        int mid = a >>> 1;
        int[] result = divideIntern(mid, b);

        int quotient, remainder;
        if((a & 0x01) == 0) //even
        {
            quotient = (result[0] << 1);
            remainder = result[1] << 1;
        }
        else //odd
        {
            quotient = (result[0] << 1);
            remainder = (result[1] << 1) + 1;
        }
        while(remainder >= b)
        {
            quotient += 1;
            remainder -= b;
        }
        result[0] = quotient;
        result[1] = remainder;
        System.out.println(a+" / "+b+" = "+quotient+" ... "+remainder);
        return result;
    }
}
