package com.crfspringcloud.consumerfeginhystrixstream.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;

/**
 * @author caoruifeng
 * @since
 * 限流器
 */
public class RateLimiter {

    private volatile int token;
    private final int originToken;

    private static Unsafe unsafe = null;
    private static final long valueOffset;
    private final Object lock = new Object();

    static {
        try {
            // 应用开发中使用unsafe对象必须通过反射获取
            Class<?> clazz = Unsafe.class;
            Field f = clazz.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(clazz);
            valueOffset = unsafe.objectFieldOffset(RateLimiter.class.getDeclaredField("token"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    public RateLimiter(int token) {
        this.originToken = token;
        this.token = token;
    }

    /**
     * 获取一个令牌
     */
    public boolean acquire() {
        int current = token;
        if (current <= 0) {
            // 保证在token已经用光的情况下依然有获取竞争的能力
            current = originToken;
        }

        long expect = 1000;// max wait 1s
        long future = System.currentTimeMillis() + expect;
        while (current > 0) {
            if (compareAndSet(current, current - 1)) {
                return true;
            }
            current = token;
            if (current <= 0 && expect > 0) {
                // 在有效期内等待通知
                synchronized (lock) {
                    try {
                        lock.wait(expect);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                current = token;
                if (current <= 0) {
                    current = originToken;
                }
                expect = future - System.currentTimeMillis();
            }
        }
        return false;
    }

    private boolean compareAndSet(int expect, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }

    /**
     * 补充令牌
     */
    public void supplement(final ExecutorService executorService) {
        this.token = originToken;
        executorService.execute(() -> {
            synchronized (lock) {
                lock.notifyAll();
            }
        });
    }

}
