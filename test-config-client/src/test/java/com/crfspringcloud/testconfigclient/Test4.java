package com.crfspringcloud.testconfigclient;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 启动3个线程打印递增的数字, 线程1先打印1,2,3,4,5,
 * 然后是线程2打印6,7,8,9,10, 然后是线程3打印11,12,13,14,15.
 * 接着再由线程1打印16,17,18,19,20….以此类推, 直到打印到75
 *
 */

public class Test4 {
    private int no = 1;
    private final Lock lock = new ReentrantLock();
    private final Condition con1 = lock.newCondition();
    private final Condition con2 = lock.newCondition();
    private final Condition con3 = lock.newCondition();
    private int curNum = 1;

    private void printNum() {
        if (curNum > 75) {
            Thread.currentThread().interrupt();
            return;
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "  " + (curNum++));
        }
    }

    public void process1() {
        lock.lock();
        try {
            while (no != 1) {
                con1.await();
            }
            printNum();
            no = 2;
            con2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void process2() {
        lock.lock();
        try {
            while (no != 2) {
                con2.await();
            }
            printNum();
            no = 3;
            con3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void process3() {
        lock.lock();
        try {
            while (no != 3) {
                con3.await();
            }
            printNum();
            no = 1;
            con1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Test4 p = new Test4();
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                p.process1();
            }
        }, "A").start();

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                p.process2();
            }
        }, "B").start();

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                p.process3();
            }
        }, "C").start();

    }
}
