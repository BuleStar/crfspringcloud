package com.crfspringcloud.testconfigclient;

/**
 * 2.实现函数,给定一个字符串数组,求该数组的连续非空子集，分別打印出来各子集
 * 举例数组为[abc]，输出[a],[b],[c],[ab],[bc],[abc]
 */

public class Test2 {
    public static void main(String[] args) {
        String s = "abc";
        char[] in = s.toCharArray();

        print(in, new StringBuilder(), 0);
        System.out.println();
        print2(s, new StringBuilder());

    }


    public static void print(char[] in, StringBuilder sb, int start) {
        int len = in.length;
        for (int i = start; i < len; i++) {
            sb.append(in[i]);
            System.out.print("[" + sb + "],");
            if (i < len - 1) {
                print(in, sb, i + 1);
            }
            sb.setLength(sb.length() - 1);
        }
    }

    public static void print2(String str, StringBuilder sb) {
        for (int i = 0; i < str.length(); i++) {
            for (int j = i; j < str.length(); j++) {
                sb.append("[").append(str, i, j + 1).append("]").append(",");
            }
        }
        sb.setLength(sb.length() - 1);
        System.out.println(sb);
    }
}
