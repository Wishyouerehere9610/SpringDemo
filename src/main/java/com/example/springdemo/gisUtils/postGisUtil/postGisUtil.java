package com.example.springdemo.gisUtils.postGisUtil;

import com.vividsolutions.jts.io.ParseException;

import java.util.List;

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

    public static List<String> geojsonConverter(String tbName) throws ParseException {
        String geojsonSql = "with a as(select t.*, st_asgeojson(geom)::json as geometry from " + tbName + ")," +
                "feature as(select 'Feature' as type,(select row_to_json(fields) from (select a.*) as fields) as properties from a)," +
                "features as(select 'FeatureCollection' as type,array_to_json(array_agg(feature.*)) as features from feature)" +
                "select row_to_json(features.*) from features;";
        List<String> data = getConnection(geojsonSql);
        return data;
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
