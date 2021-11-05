package com.example.springdemo.utils;

import com.vividsolutions.jts.io.ParseException;

import java.util.HashMap;
import java.util.List;

import static com.example.springdemo.utils.JTSUtil.*;
import static com.example.springdemo.utils.databaseUtil.getConnection;

public class jtsGis {

    public static void main(String[] args) throws ParseException {
        String sql1w = "select st_astext(t.geom) from gis.osm_buildings_a_free_1w t;";
        String sql10w = "select st_astext(t.geom) from gis.osm_buildings_a_free_10w t;";
        String sql100w = "select st_astext(t.geom) from gis.osm_buildings_a_free_100w t;";
        String[] sqlObjects = new String[]{sql1w, sql10w};
        HashMap map = new HashMap<>();
        map.put(sql1w, "1w面数据");
        map.put(sql10w, "10w面数据");
        map.put(sql100w, "100w面数据");
        System.out.println("--------------------------JTS测试面积----------------------------------------------------");
        for (String sql : sqlObjects) {
            long startTime = System.currentTimeMillis();   //获取开始时间
            List<String> data = getConnection(sql);
            //测试面积计算
            calArea(data);
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println("使用JTS面积算法计算" + map.get(sql) + "耗时： " + time + "ms");
        }
        System.out.println("--------------------------JTS测试周长----------------------------------------------------");
        for (String sql : sqlObjects) {
            long startTime = System.currentTimeMillis();   //获取开始时间
            List<String> data = getConnection(sql);
            //测试周长计算
            calPerimeter(data);
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println("使用JTS周长算法计算" + map.get(sql) + "耗时： " + time + "ms");
        }
        System.out.println("--------------------------JTS测试缓冲区----------------------------------------------------");
        for (String sql : sqlObjects) {
            long startTime = System.currentTimeMillis();   //获取开始时间
            List<String> data = getConnection(sql);
            //测试缓冲区计算
            getBuffer(data);
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println("使用JTS缓冲区算法计算" + map.get(sql) + "耗时： " + time + "ms");
        }


    }
}
