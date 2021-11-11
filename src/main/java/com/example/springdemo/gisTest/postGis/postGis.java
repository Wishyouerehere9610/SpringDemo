package com.example.springdemo.gisTest.postGis;

import com.vividsolutions.jts.io.ParseException;

import java.util.HashMap;
import java.util.List;

import static com.example.springdemo.gisUtils.dbUtil.databaseUtil.getConnection;

public class postGis {
    public static void main(String[] args) throws ParseException {
        String[] tableObjects = new String[]{"gis.osm_buildings_a_free_1w t",
                "gis.osm_buildings_a_free_10w t",
                "gis.osm_buildings_a_free_100w t"};
        HashMap map = new HashMap<>();
        map.put("gis.osm_buildings_a_free_1w t", "1w面数据");
        map.put("gis.osm_buildings_a_free_10w t", "10w面数据");
        map.put("gis.osm_buildings_a_free_100w t", "100w面数据");
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
        System.out.println("--------------------------postGis测试几何中心----------------------------------------------------");
        for (String tableName : tableObjects) {
            String sql = "select st_astext(ST_Centroid(t.geom)) from " + tableName;
            long startTime = System.currentTimeMillis();   //获取开始时间
            getConnection(sql);
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println("使用postGis几何中心算法计算" + map.get(tableName) + "耗时： " + time + "ms");
        }
        System.out.println("--------------------------postGis测试buffer----------------------------------------------------");
        for (String tableName : tableObjects) {
            String sql = "select st_astext(st_buffer(t.geom,10)) from " + tableName;
            long startTime = System.currentTimeMillis();   //获取开始时间
            getConnection(sql);
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println("使用postGis缓冲区算法计算" + map.get(tableName) + "耗时： " + time + "ms");
        }
        System.out.println("--------------------------postGis测试dbscan----------------------------------------------------");
        for (String tableName : tableObjects) {
            //0.002是度，约200米。
            String sql = "select ST_ClusterDBSCAN(t.geom, eps:= 0.002, minpoints := 2) over () AS cid from " + tableName;
            long startTime = System.currentTimeMillis();   //获取开始时间
            List<String> result = (getConnection(sql));
            System.out.println(result.size());
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println("使用postGis dbscan算法计算" + map.get(tableName) + "耗时： " + time + "ms");
        }
        System.out.println("--------------------------postGis测试K-means----------------------------------------------------");
        for (String tableName : tableObjects) {
            String sql = "select ST_ClusterKMeans(t.geom,1000) over () AS cid from " + tableName;
            long startTime = System.currentTimeMillis();   //获取开始时间
            getConnection(sql);
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println("使用postGis K-means算法计算" + map.get(tableName) + "耗时： " + time + "ms");
        }
        System.out.println("--------------------------postGis测试Intersecting----------------------------------------------------");
        for (String tableName : tableObjects) {
            String sql = "select st_astext(unnest(ST_ClusterIntersecting(t.geom))) from " + tableName;
            long startTime = System.currentTimeMillis();   //获取开始时间
            getConnection(sql);
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println("使用postGis Intersecting算法计算" + map.get(tableName) + "耗时： " + time + "ms");
        }
        System.out.println("--------------------------postGis测试within----------------------------------------------------");
        for (String tableName : tableObjects) {
            String sql = "select ST_AsText(unnest(ST_ClusterWithin(t.geom, 0.002))) from " + tableName;
            long startTime = System.currentTimeMillis();   //获取开始时间
            getConnection(sql);
            long endTime = System.currentTimeMillis(); //获取结束时间
            double time = endTime - startTime;
            System.out.println("使用postGis within算法计算" + map.get(tableName) + "耗时： " + time + "ms");
        }

    }

}
