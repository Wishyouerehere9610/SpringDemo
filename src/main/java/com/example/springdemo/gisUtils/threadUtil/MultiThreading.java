package com.example.springdemo.gisUtils.threadUtil;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MultiThreading {
    private static final ThreadPoolExecutor EXECUTOR =
            new ThreadPoolExecutor(10,10,0L, TimeUnit.MINUTES,new LinkedBlockingQueue<>());
    public static ThreadPoolExecutor getThreadPool(){
        return EXECUTOR;
    }

}
