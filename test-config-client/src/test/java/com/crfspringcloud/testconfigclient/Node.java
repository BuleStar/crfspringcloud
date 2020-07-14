package com.crfspringcloud.testconfigclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * 已知一个树的节点定义如下，求返回该节点的所有孩子的静态方法
 * <p>
 * A
 * B     C
 * E   F       G
 * <p>
 * <p>
 * 则C的孩子 G A节点的所有孩子["B","C","E","F","G"]
 */
public class Node {

    public String name;
    public List<Node> children;

    /**
     * 非递归实现二叉树的前序遍历
     * @param root
     * @return
     */
    public static List<String> allChildren(Node root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;
        Stack<Node> tmp = new Stack<>();
        tmp.push(root);
        while (!tmp.isEmpty()) {
            Node node = tmp.pop();
            if (node == null) continue;
            result.add(node.name);
            if (node.children != null) {
                for (Node n : node.children) {
                    tmp.push(n);
                }
            }
        }

        return result;
    }

    /**
     * 非递归实现二叉树的后序遍历
     * @param root
     * @return
     */
    public List<String> postorderTraversal(Node root) {
        List<String> ret = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node == null) continue;
            ret.add(node.name);
            if (node.children != null) {
                for (Node n : node.children) {
                    stack.push(n);
                }
            }
        }
        Collections.reverse(ret);
        return ret;
    }


    public static void main(String[] args) {
        Node node = new Node();
        node.name = "A";

        List<Node> list = new ArrayList<>();
        List<Node> blist = new ArrayList<>();
        List<Node> clist = new ArrayList<>();

        Node b = new Node();
        b.name = "B";
        Node c = new Node();
        c.name = "C";

        Node e = new Node();
        e.name = "E";
        Node f = new Node();
        f.name = "F";
        Node g = new Node();
        g.name = "G";


        blist.add(e);
        blist.add(f);
        clist.add(g);
        b.children = blist;
        c.children = clist;

        list.add(b);
        list.add(c);
        node.children = list;
        List<String> result = allChildren(node);

        System.out.println(result);
    }
}

