package com.example.springdemo.gisTest.postGis;

import com.vividsolutions.jts.io.ParseException;

import java.io.IOException;
import java.util.HashMap;

import static com.example.springdemo.gisUtils.postGisUtil.postGisUtil.add_length;
import static com.example.springdemo.gisUtils.postGisUtil.postGisUtil.csv_add_geom;

public class coordinatesToGemo {
    public static void main(String[] args) throws ParseException, IOException {
        String[] tableObjects = new String[]{"gis.csv_1k", "gis.csv_1w"};
//                "gis.csv_1w","gis.csv_4w"};
        HashMap map = new HashMap<>();
        map.put("gis.csv_1k", "1kCSV数据");
        map.put("gis.csv_1w", "1wCSV数据");
//        map.put("gis.csv_4w", "4wCSV数据");
        System.out.println("--------------------------csv入库转带有geometry字段表----------------------------------------------------");
        for (String tableName : tableObjects) {
            long startTime = System.currentTimeMillis();   //获取开始时间
            csv_add_geom(tableName, "x", "y");
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println(map.get(tableName) + "转换表耗时： " + time + "ms");
        }


    }
}
