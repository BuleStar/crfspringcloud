package com.crfspringcloud.testconfigclient;

import java.util.HashMap;
import java.util.Map;

/**
 * 打印一个数组中和为K的连续子数组
 */
public class Test5 {
    /**
     * 用一个哈希表来建立连续子数组之和跟其出现次数之间的映射，初始化要加入{0,1}这对映射，
     * 我们建立哈希表的目的是为了让我们可以快速的查找sum-k是否存在，即是否有连续子数组的和为sum-k，
     * 如果存在的话，那么和为k的子数组一定也存在，这样当sum刚好为k的时候，那么数组从起始到当前位置的这段子数组的和就是k，
     * 满足题意，如果哈希表中事先没有m[0]项的话，这个符合题意的结果就无法累加到结果res中，这就是初始化的用途。
     */
    public static int subarraySum(int[] nums, int k) {

        int sum = 0;
        int res = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k)) {
                res += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return res;
    }

    public static int subarraySum2(int[] nums, int k) {
        int n = nums.length;
        int res = 0;
        int[] sum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + nums[i - 1];
        }
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (sum[j + 1] - sum[i] == k) {
                    res++;
                }
            }
        }
        return res;
    }


    public static void main(String[] args) {
        int[] nums = {1, 1, 3, 4, 1};
        int k = 5;
        int res = subarraySum(nums, k);
        System.out.println(res);
    }
}
