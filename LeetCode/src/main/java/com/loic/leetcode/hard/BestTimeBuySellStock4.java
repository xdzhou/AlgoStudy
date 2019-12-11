package com.loic.leetcode.hard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 188. Best Time to Buy and Sell Stock IV
 * <p>
 * Say you have an array for which the i-th element is the price of a given stock on day i.
 * <p>
 * Design an algorithm to find the maximum profit. You may complete at most k transactions.
 * <p>
 * Note:
 * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 * <p>
 * Example 1:
 * <p>
 * Input: [2,4,1], k = 2
 * Output: 2
 * Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
 * Example 2:
 * <p>
 * Input: [3,2,6,5,0,3], k = 2
 * Output: 7
 * Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4.
 * Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 */
public class BestTimeBuySellStock4 {

  public static int maxProfit(int k, int... prices) {
    if (prices.length < 2 || k == 0) {
      return 0;
    }
    List<Ascending> ascendings = retrieveAscending(prices);
    k = Math.min(k, ascendings.size());
    if (k == ascendings.size()) {
      return ascendings.stream().mapToInt(Ascending::profit).sum();
    }
    // dp[n][k] (n>=k): the max profit could got when doing k transactions within first n ascendings
    // dp[n][k] = 0; if n==0 or k==0
    /* dp[n][k] is the max of dp[n-1][k] (transaction NOT include n-th ascending)
     * and all the transactions including n-th ascending (buy at one of the previous ascendings' low point and sell at n-th ascending high point)
     */
    int[][] dp = new int[ascendings.size() + 1][k + 1];

    for (int n = 1; n <= ascendings.size(); n++) {
      for (int count = 1; count <= k; count++) {
        if (n == ascendings.size()) {
          // as dp[n][k] doesn't depend on dp[n][k-1], dp[n][k-2]...
          count = k;
        }
        if (n == count) {
          dp[n][count] = ascendings.subList(0, n).stream().mapToInt(Ascending::profit).sum();
        } else {
          int max = dp[n - 1][count];
          int curHigh = ascendings.get(n - 1).high;
          int curLow = ascendings.get(n - 1).low;
          for (int subN = n - 1; subN >= count - 1; subN--) {
            int low = ascendings.get(subN).low;
            if (subN == n - 1 || low < curLow) {
              max = Math.max(max, dp[subN][count - 1] + curHigh - low);
            }
          }
          dp[n][count] = max;
        }
      }
    }
    return dp[ascendings.size()][k];
  }

  private static List<Ascending> retrieveAscending(int... prices) {
    List<Ascending> result = new ArrayList<>();
    Boolean isRaising = null;
    Integer preLow = null;
    for (int i = 1; i < prices.length; i++) {
      Boolean curIsRaising = isRaising;
      if (prices[i] != prices[i - 1]) {
        curIsRaising = prices[i] > prices[i - 1];
      }
      if (!Objects.equals(curIsRaising, isRaising)) {
        if (curIsRaising) {
          // found low point
          preLow = prices[i - 1];
        } else if (preLow != null) {
          result.add(new Ascending(preLow, prices[i - 1]));
        }
      }
      isRaising = curIsRaising;
      if (i == prices.length - 1 && preLow != null && isRaising) {
        result.add(new Ascending(preLow, prices[i]));
      }
    }
    return result;
  }

  private static final class Ascending {
    private final int low, high;

    private Ascending(int low, int high) {
      this.low = low;
      this.high = high;
    }

    public int profit() {
      return high - low;
    }
  }

}
