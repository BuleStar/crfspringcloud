package com.crfspringcloud.testconfigclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 3.文件系统中按逗号分割保存了1亿个正整数(一行10个数，1000万行)，找出其中最大的100个数
 *
 */
public class Test3 {
    /**
     * 用PriorityQueue默认是自然顺序排序，要选择最大的k个数，构造小顶堆，
     * 每次取数组中剩余数与堆顶的元素进行比较，如果新数比堆顶元素大，则删除堆顶元素，并添加这个新数到堆中。
     */
    public static void main(String[] args) {
        System.out.println(getTopK("C:\\Users\\Administrator\\Desktop\\note\\TopKTest.txt", 6));
    }

    public static List<Integer> getTopK(String filePath, int k) {
        List<Integer> list = new ArrayList<>();
        Queue<Integer> queue = new PriorityQueue<>(k);
        File file = new File(filePath);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                String[] strings = tmp.split(",");
                for (String str : strings) {
                    int num = Integer.parseInt(str);
                    if (queue.size() < k) {
                        queue.offer(num);
                    } else if (queue.peek() < num) {
                        queue.poll();
                        queue.offer(num);
                    }
                }
            }
            while (k-- > 0) {
                list.add(queue.poll());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
