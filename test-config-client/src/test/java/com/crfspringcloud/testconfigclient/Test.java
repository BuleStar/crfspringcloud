package com.crfspringcloud.testconfigclient;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1.实现两个线程，使之交替打印1-100,
 * 如：两个线程分别为：Printer1和Printer2,
 * 最后输出结果为： Printer1 — 1 Printer2 一 2 Printer1 一 3 Printer2 一 4
 */


public class Test {
    private int number = 1;

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();

    public void loopA() {
        lock.lock();

        try {
            if (number % 2 == 0) {
                condition1.await();
            }

            System.out.println(Thread.currentThread().getName() + "-" + number + " ");

            number++;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopB() {
        lock.lock();

        try {
            if (number % 2 != 0) {
                condition2.await();
            }

            System.out.println(Thread.currentThread().getName() + "-" + number + " ");

            number++;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Test ad = new Test();

        new Thread(() -> {

            for (int i = 1; i <= 50; i++) {
                ad.loopA();
            }

        }, "Printer1").start();

        new Thread(() -> {

            for (int i = 1; i <= 50; i++) {
                ad.loopB();
            }

        }, "Printer2").start();

    }


}
