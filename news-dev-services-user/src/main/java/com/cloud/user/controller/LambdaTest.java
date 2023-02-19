package com.cloud.user.controller;

import java.util.concurrent.*;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 22:42 2021/10/27
 * @Modified by:ycy
 */
public class LambdaTest {

    public static void main(String[] args) {
        Ceil ceil =(int a,int b) -> {
            return a + b;
        };
        System.out.println("lambda表达式:"+ceil.count(1,6));
    }

    interface Ceil{

        int count(int a,int b);
    }


    public static void main2(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService1 = Executors.newFixedThreadPool(100);
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10,
                20,0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10));
    }
}
