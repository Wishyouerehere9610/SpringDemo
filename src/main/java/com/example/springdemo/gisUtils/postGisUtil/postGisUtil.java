package com.example.springdemo.gisUtils.postGisUtil;

import static com.example.springdemo.gisUtils.dbUtil.databaseUtil.executeSql;

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

    public static void csv_add_geom(String tbName) {
        String drop_lengthCol = "ALTER TABLE " + tbName + " DROP COLUMN IF EXISTS new_geom";
        executeSql(drop_lengthCol);
        String add_lengthCol = "ALTER TABLE " + tbName + " ADD new_geom geometry(Point,4326);";
        executeSql(add_lengthCol);
        String update_lengthCol = "UPDATE " + tbName + " d set new_length=(select st_length(t.geom) from " + tbName + " t where t.gid=d.gid)";
        executeSql(update_lengthCol);
    }


}
