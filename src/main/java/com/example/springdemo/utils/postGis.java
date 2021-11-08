package com.example.springdemo.utils;

import com.vividsolutions.jts.io.ParseException;

import java.util.HashMap;
import java.util.List;

import static com.example.springdemo.utils.databaseUtil.getConnection;

public class postGis {
    public static void main(String[] args) throws ParseException {
        String[] tableObjects = new String[]{"gis.osm_buildings_a_free_1w t",
                "gis.osm_buildings_a_free_10w t",
                "gis.osm_buildings_a_free_100w t"};
        HashMap map = new HashMap<>();
        map.put("gis.osm_buildings_a_free_1w t", "1w面数据");
        map.put("gis.osm_buildings_a_free_10w t", "10w面数据");
        map.put("gis.osm_buildings_a_free_100w t", "100w面数据");
        System.out.println("--------------------------postGis测试几何中心----------------------------------------------------");
        for (String tableName : tableObjects) {
            String sql = "select ST_Centroid(t.geom) from " + tableName;
            long startTime = System.currentTimeMillis();   //获取开始时间
            List<String> result = getConnection(sql);
            System.out.println(result.size());
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println("使用postGis几何中心算法计算" + map.get(tableName) + "耗时： " + time + "ms");
        }
        System.out.println("--------------------------postGis测试面积----------------------------------------------------");
        for (String tableName : tableObjects) {
            String sql = "select st_area(t.geom) from " + tableName;
            long startTime = System.currentTimeMillis();   //获取开始时间
            getConnection(sql);
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println("使用postGis面积算法计算" + map.get(tableName) + "耗时： " + time + "ms");
        }
        System.out.println("--------------------------postGis测试周长----------------------------------------------------");
        for (String tableName : tableObjects) {
            String sql = "select st_perimeter(t.geom) from " + tableName;
            long startTime = System.currentTimeMillis();   //获取开始时间
            getConnection(sql);
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println("使用postGis周长算法计算" + map.get(tableName) + "耗时： " + time + "ms");
        }
        System.out.println("--------------------------postGis测试buffer----------------------------------------------------");
        for (String tableName : tableObjects) {
            String sql = "select st_buffer(t.geom,10) from " + tableName;
            long startTime = System.currentTimeMillis();   //获取开始时间
            getConnection(sql);
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println("使用postGis缓冲区算法计算" + map.get(tableName) + "耗时： " + time + "ms");
        }


    }

}
