package com.example.springdemo.utils;

import com.vividsolutions.jts.io.ParseException;

import java.util.HashMap;
import java.util.List;

import static com.example.springdemo.utils.JTSUtil.*;
import static com.example.springdemo.utils.JTSUtil.getBuffer;
import static com.example.springdemo.utils.databaseUtil.getConnection;
import static com.example.springdemo.utils.postGisUtil.*;

public class polygonAddAttribute {
    public static void main(String[] args) throws ParseException {
        String[] tableObjects = new String[]{"gis.osm_buildings_a_free_1w",
                "gis.osm_buildings_a_free_10w"};
//                "gis.osm_buildings_a_free_100w"};
        HashMap map = new HashMap<>();
        map.put("gis.osm_buildings_a_free_1w", "1w数据");
        map.put("gis.osm_buildings_a_free_10w", "10w数据");
//        map.put("gis.osm_buildings_a_free_100w", "100w数据");
        System.out.println("--------------------------postGis测试面数据面积属性生成----------------------------------------------------");
        for (String tableName : tableObjects) {
            long startTime = System.currentTimeMillis();   //获取开始时间
            add_area(tableName);
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println(map.get(tableName) + "面积属性生成耗时： " + time + "ms");
        }
        System.out.println("--------------------------postGis测试面数据周长属性生成----------------------------------------------------");
        for (String tableName : tableObjects) {
            long startTime = System.currentTimeMillis();   //获取开始时间
            add_perimeter(tableName);
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println(map.get(tableName) + "周长属性生成耗时： " + time + "ms");
        }
    }
}
