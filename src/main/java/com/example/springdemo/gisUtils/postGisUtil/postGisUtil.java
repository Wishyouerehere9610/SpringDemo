package com.example.springdemo.gisUtils.postGisUtil;

import com.alibaba.fastjson.JSONObject;
import com.vividsolutions.jts.io.ParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.springdemo.gisUtils.dbUtil.databaseUtil.executeSql;
import static com.example.springdemo.gisUtils.dbUtil.databaseUtil.getConnection;

public class postGisUtil {
    public static void add_area(String tbName) {
        String drop_areaCol = "ALTER TABLE " + tbName + " DROP COLUMN IF EXISTS st_area";
        executeSql(drop_areaCol);
        String create_new = "CREATE TABLE " + tbName + "_new AS " +
                "(SELECT t.*, st_area(t.geom) from " + tbName + " t);";
        executeSql(create_new);
        String drop_old = "DROP TABLE " + tbName + ";";
        executeSql(drop_old);
    }

    public static void add_perimeter(String tbName) {
        String drop_perimeterCol = "ALTER TABLE " + tbName + " DROP COLUMN IF EXISTS st_perimeter";
        executeSql(drop_perimeterCol);
        String create_new = "CREATE TABLE " + tbName + "_new AS " +
                "(SELECT t.*, st_perimeter(t.geom) from " + tbName + " t);";
        executeSql(create_new);
        String drop_old = "DROP TABLE " + tbName + ";";
        executeSql(drop_old);
    }

    public static void add_length(String tbName) {
        String drop_lengthCol = "ALTER TABLE " + tbName + " DROP COLUMN IF EXISTS st_length";
        executeSql(drop_lengthCol);
//        String add_lengthCol = "ALTER TABLE " + tbName + " ADD new_length double precision DEFAULT 0";
//        executeSql(add_lengthCol);
//        String update_lengthCol = "UPDATE " + tbName + " d set new_length=(select st_length(t.geom) from " + tbName + " t where t.gid=d.gid)";
//        executeSql(update_lengthCol);
        String create_new = "CREATE TABLE " + tbName + "_new AS " +
                "(SELECT t.*, st_length(t.geom) from " + tbName + " t);";
        executeSql(create_new);
        String drop_old = "DROP TABLE " + tbName + ";";
        executeSql(drop_old);

    }

    public static void csv_add_geom(String tbName, String longitudeColName, String latitudeColName) throws ParseException {
        String drop_lengthCol = "ALTER TABLE " + tbName + " DROP COLUMN IF EXISTS new_geom";
        executeSql(drop_lengthCol);
        String create_new = "CREATE TABLE " + tbName + "_new AS " +
                "(SELECT t.*, ST_SetSRID( ST_Point(t." + longitudeColName + ", t." + latitudeColName + "),4326) as new_geom from " + tbName + " t);";
        executeSql(create_new);
        String drop_old = "DROP TABLE " + tbName + ";";
        executeSql(drop_old);
    }

    public static JSONObject geojsonConverter(String tbName) throws ParseException {
        String geojsonSql = "with a as(select t.*, st_asgeojson(geom)::json as geometry from " + tbName + " t)," +
                "feature as(select 'Feature' as type,(select row_to_json(fields) from (select a.*) as fields) as properties from a)," +
                "features as(select 'FeatureCollection' as type,array_to_json(array_agg(feature.*)) as features from feature)" +
                "select row_to_json(features.*) from features;";
        List<String> data = getConnection(geojsonSql);
        JSONObject json = JSONObject.parseObject(data.get(0));
        //循环json处理并保存
        Iterator<Object> features = json.getJSONArray("features").iterator();
        while (features.hasNext()) {
            JSONObject next = (JSONObject) features.next();
            next.getJSONObject("properties").remove("geom");
            next.getJSONObject("properties").remove("geometry");
        }
        return json;
    }

    public static Float addAll(String unit) {
        String sql = "select sum(t.st_perimeter) from gis.osm_buildings_a_free_1w_new t;";
        String result = getConnection(sql).get(0);
        Float new_result = Float.valueOf(result);
        int rate = 0;
        switch (unit) {
            case "km":
                rate = 1000;
                return new_result / rate;
            case "m":
            case "m2":
                rate = 1;
                return new_result / rate;
            case "km2":
                rate = 1000000;
                return new_result / rate;
        }
        return new_result;
    }

    public static String getTbColunmnName(String sourceTbName) {
//        String tbName = sourceTbName.substring(9);
        String getTbColunmnNameSql = "select column_name from INFORMATION_SCHEMA.Columns where table_name = " + sourceTbName + ";";
        return getTbColunmnNameSql;
    }

    public static String createTabsName(List<String> names, String name) {
        String newName;
        if (names.size() == 0) {
            newName = name;
        } else {
            List<String> defaultName = names.stream()
                    .filter(n -> n.matches("^" + name + "(\\([0-9]+\\))?$"))
                    .collect(Collectors.toList());
            List<Integer> order = defaultName.stream()
                    .map(s -> s.equals(name) ? "0" : s.substring(name.length() + 1, s.length() - 1))
                    .map(Integer::parseInt)
                    .sorted()
                    .collect(Collectors.toList());
            if (order.size() == 0) {
                newName = name;
            } else {
                newName = String.format(name + "(%d)", order.get(order.size() - 1) + 1);
            }
        }
        return newName;
    }


    public static String checkColName(String sourceTbName, String columnName) {
        String countSql = getTbColunmnName(sourceTbName);
        List<String> columns = getConnection(countSql);
        String newName = createTabsName(columns, columnName);
        return newName;
    }

    public static int check() {
        List<String> sourceCol = new ArrayList<String>();
        sourceCol.add("1");
        sourceCol.add("2");
        sourceCol.add("3");
        List<String> viewCol = new ArrayList<String>();
        viewCol.add("1");
        viewCol.add("2");
        viewCol.add("3");

        viewCol.removeAll(sourceCol);
        if (viewCol.size() == 0) {
            return 0;
        } else {
            return 1;
        }


    }

    public static String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return list.isEmpty() ? "" : sb.toString().substring(0, sb.toString().length() - 1);
    }


    public static void test() {
        List<String> abc = new ArrayList<>();
        abc.add("gid");
        abc.add("name");
        abc.add("geom");
        if (abc.contains("geom") == true) {
            abc.remove("geom");
        }
        String cols = listToString(abc, ',');
        String sql = "CREATE VIEW view_test_list3 " +
                "AS SELECT " + cols +
                " from gis.geojsonpolygon_100;";
        executeSql(sql);
    }


//    public static void csv_add_geom(String tbName, String longitude, String latitude) throws ParseException {
//        String drop_lengthCol = "ALTER TABLE " + tbName + " DROP COLUMN IF EXISTS new_geom";
//        executeSql(drop_lengthCol);
//        String add_lengthCol = "ALTER TABLE " + tbName + " ADD new_geom geometry(Point,4326);";
//        executeSql(add_lengthCol);
//        //测试保存
//        String sql = "select " + longitude + ", " + latitude + " from " + tbName + " order by id;";
//        long startTime1 = System.currentTimeMillis();   //获取开始时间
//        List<String> points = db_coordinates_to_wkt(sql);
//        long endTime1 = System.currentTimeMillis(); //获取结束时间
//        double time1 = endTime1 - startTime1;
//        System.out.println("链接数据库执行select sql（遍历）耗时： " + time1 + "ms");
//
//        long startTime2 = System.currentTimeMillis();   //获取开始时间
//        List<String> wkb_list = new ArrayList<>();
//        for (int i = 0; i < points.size(); i++) {
//            String point = points.get(i);
//            String point_wkb = wtkToWkb(point);
//            wkb_list.add(point_wkb);
//        }
//        long endTime2 = System.currentTimeMillis(); //获取结束时间
//        double time2 = endTime2 - startTime2;
//        System.out.println("wkt转wkb（遍历）耗时： " + time2 + "ms");
//
//        //生成对应的ids
//        long startTime3 = System.currentTimeMillis();
//        List<Integer> ids = Stream.iterate(0, item -> item + 1).limit(wkb_list.size() + 1).collect(Collectors.toList());
//        for (int x = 0; x < wkb_list.size(); x++) {
//            //现在是自创id,集成在平台上后改成根据record_id来写
//            String insertSql = "UPDATE " + tbName + " set new_geom=('SRID=4326;" + wkb_list.get(x) + "') where id = " + ids.get(x);
//            executeSql(insertSql);
//        }
//        long endTime3 = System.currentTimeMillis(); //获取结束时间
//        double time3 = endTime3 - startTime3;
//        System.out.println("update表（遍历）： " + time3 + "ms");
//
//    }


}
