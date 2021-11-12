package com.example.springdemo.gisTest.postGis;

import com.vividsolutions.jts.io.ParseException;

import java.io.IOException;

import static com.example.springdemo.gisUtils.postGisUtil.postGisUtil.csv_add_geom;

public class coordinatesToGemo {
    public static void main(String[] args) throws ParseException, IOException {
        long startTime = System.currentTimeMillis();   //获取开始时间
        csv_add_geom("dataset.j_322_1636684973666", "x", "y");
        long endTime = System.currentTimeMillis(); //获取结束时间
        double time = endTime - startTime;
        System.out.println("生成耗时: " + time + "ms");

    }
}
