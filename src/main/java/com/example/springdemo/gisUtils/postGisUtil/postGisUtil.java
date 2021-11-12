package com.example.springdemo.gisUtils.postGisUtil;

import com.vividsolutions.jts.io.ParseException;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.springdemo.gisUtils.dbUtil.databaseUtil.*;
import static com.example.springdemo.gisUtils.postGisUtil.pointWktToWkb.wtkToWkb;

public class postGisUtil {
    public static void add_area(String tbName) {
        String drop_areaCol = "ALTER TABLE " + tbName + " DROP COLUMN IF EXISTS new_area";
        executeSql(drop_areaCol);
        String add_areaCol = "ALTER TABLE " + tbName + " ADD new_area double precision DEFAULT 0";
        executeSql(add_areaCol);
        String update_areaCol = "UPDATE " + tbName + " d set new_area=(select st_area(t.geom) from " + tbName + " t where t.gid=d.gid)";
        executeSql(update_areaCol);
    }

    public static void add_perimeter(String tbName) {
        String drop_perimeterCol = "ALTER TABLE " + tbName + " DROP COLUMN IF EXISTS new_perimeter";
        executeSql(drop_perimeterCol);
        String add_perimeterCol = "ALTER TABLE " + tbName + " ADD new_perimeter double precision DEFAULT 0";
        executeSql(add_perimeterCol);
        String update_perimeterCol = "UPDATE " + tbName + " d set new_perimeter=(select st_perimeter(t.geom) from " + tbName + " t where t.gid=d.gid)";
        executeSql(update_perimeterCol);
    }

    public static void add_length(String tbName) {
        String drop_lengthCol = "ALTER TABLE " + tbName + " DROP COLUMN IF EXISTS new_length";
        executeSql(drop_lengthCol);
        String add_lengthCol = "ALTER TABLE " + tbName + " ADD new_length double precision DEFAULT 0";
        executeSql(add_lengthCol);
        String update_lengthCol = "UPDATE " + tbName + " d set new_length=(select st_length(t.geom) from " + tbName + " t where t.gid=d.gid)";
        executeSql(update_lengthCol);
    }

    public static void csv_add_geom(String tbName, String longitude, String latitude) throws ParseException {
        String drop_lengthCol = "ALTER TABLE " + tbName + " DROP COLUMN IF EXISTS new_geom";
        updateGPSql(drop_lengthCol);
        String add_lengthCol = "ALTER TABLE " + tbName + " ADD new_geom geometry(Point,4326);";
        updateGPSql(add_lengthCol);
        String sql = "select " + longitude + ", " + latitude + " from " + tbName + " order by _record_id_;";
        List<String> points = db_coordinates_to_wkt(sql);
        List<String> wkb_list = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            String point = points.get(i);
            String point_wkb = wtkToWkb(point);
            wkb_list.add(point_wkb);
        }
        //生成对应的record_id
        List<Integer> record_ids = Stream.iterate(1, item -> item + 1).limit(wkb_list.size()).collect(Collectors.toList());
        for (int x = 0; x < wkb_list.size(); x++) {
            String insertSql = "UPDATE " + tbName + " set new_geom=('SRID=4326;" + wkb_list.get(x) + "') where _record_id_ = " + record_ids.get(x);
            updateGPSql(insertSql);
        }

    }


}
