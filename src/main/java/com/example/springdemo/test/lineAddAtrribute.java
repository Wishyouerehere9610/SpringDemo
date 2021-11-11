package com.example.springdemo.test;

import com.vividsolutions.jts.io.ParseException;

import java.util.HashMap;

import static com.example.springdemo.utils.postGisUtil.*;

public class lineAddAtrribute {
    public static void main(String[] args) throws ParseException {
        String[] tableObjects = new String[]{"gis.gis_osm_roads_free_1w",
                "gis.gis_osm_roads_free_10w",
                "gis.gis_osm_roads_free_100w"};
        HashMap map = new HashMap<>();
        map.put("gis.gis_osm_roads_free_1w", "1w数据");
        map.put("gis.gis_osm_roads_free_10w", "10w数据");
        map.put("gis.gis_osm_roads_free_100w", "100w数据");
        System.out.println("--------------------------postGis测试线数据length属性生成----------------------------------------------------");
        for (String tableName : tableObjects) {
            long startTime = System.currentTimeMillis();   //获取开始时间
            add_length(tableName);
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println(map.get(tableName) + "length属性生成耗时： " + time + "ms");
        }

    }
}
