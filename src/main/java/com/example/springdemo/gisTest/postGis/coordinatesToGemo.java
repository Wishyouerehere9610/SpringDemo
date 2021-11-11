package com.example.springdemo.gisTest.postGis;

import com.vividsolutions.jts.io.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.springdemo.gisUtils.dbUtil.databaseUtil.db_coordinates_to_wkt;
import static com.example.springdemo.gisUtils.postGisUtil.pointWktToWkb.wtkToWkb;

public class coordinatesToGemo {
    public static void main(String[] args) throws ParseException, IOException {
        String tableName = "dataset.p_322_1636598778900";
        String longitude = "x";
        String latitude = "y";
        String sql = "select " + longitude + ", " + latitude + " from " + tableName + " order by _record_id_;";
        long startTime = System.currentTimeMillis();   //获取开始时间
        List<String> points = db_coordinates_to_wkt(sql);
        List<String> wkb_list = new ArrayList<>();
        for (int i = 0; i < points.size(); i = i + 1) {
            String point = points.get(i);
            String point_wkb = wtkToWkb(point);
            wkb_list.add(point_wkb);
        }
        long endTime = System.currentTimeMillis(); //获取结束时间
        double time = endTime - startTime;
        System.out.println("生成耗时: " + time + "ms");
        System.out.println(wkb_list.get(4));
    }
}
