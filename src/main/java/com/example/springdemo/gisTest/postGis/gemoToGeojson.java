package com.example.springdemo.gisTest.postGis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vividsolutions.jts.io.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import static com.example.springdemo.gisUtils.dbUtil.databaseUtil.getConnection;
import static com.example.springdemo.gisUtils.postGisUtil.postGisUtil.geojsonConverter;

public class gemoToGeojson {
    public static void main(String[] args) throws ParseException, IOException {

        String[] tableObjects = new String[]{"gis.osm_buildings_a_free_1w",
                "gis.osm_buildings_a_free_10w","gis.osm_buildings_a_free_100w",
                "gis.cn_sheng_data","gis.cn_shi_data"};
        HashMap map = new HashMap<>();
        map.put("gis.osm_buildings_a_free_1w", "测试转1w面数据geojson");
        map.put("gis.osm_buildings_a_free_10w", "测试转10w面数据geojson");
        map.put("gis.cn_sheng_data", "测试转cn_sheng面数据geojson");
        map.put("gis.cn_shi_data", "测试转cn_shi面数据geojson");

        System.out.println("--------------------------postGis测试转geojson----------------------------------------------------");
        for (String tableName : tableObjects) {
            long startTime = System.currentTimeMillis();   //获取开始时间
            geojsonConverter(tableName);
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println("使用postGis测试转geojson" + map.get(tableName) + "耗时： " + time + "ms");
        }
    }

}


