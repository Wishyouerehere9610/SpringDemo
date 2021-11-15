package com.example.springdemo.gisTest.threading;

import com.example.springdemo.gisUtils.threadUtil.MultiThreading;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        //单例模式创建线程池
        ThreadPoolExecutor threadPool = MultiThreading.getThreadPool();
        //计数器设置为10个，用来调度主线程和子线程之间关系
        CountDownLatch downLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            threadPool.submit(() -> {
                try {
                    while (true) {
                        //处理逻辑 先去数据库更新1000条数据
                        //查询数据，若查询出的数据为空，直接break
                        List<String> datas = new ArrayList<>();
                        if (CollectionUtils.isEmpty(datas)) {
                            break;
                        }
                    }
                } finally {
                    downLatch.countDown();
                }
            });
        }

        try {
            //阻碍主线程，等所有子线程完成 再去执行下面操作
            downLatch.await();
            //处理逻辑
        } catch (Exception e) {
            Thread.interrupted();
            e.printStackTrace();
        }

    }
}


