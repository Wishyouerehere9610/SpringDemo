package com.example.springdemo.gisTest.udftest;

import static com.example.springdemo.gisUtils.dbUtil.databaseUtil.executeSql;

public class sqlUdfTest {
    public static void main(String[] args) {
        String sql_udf = "CREATE FUNCTION dbo.getGeom()\n" +
                "　　RETURNS TABLE\n" +
                "　　AS\n" +
                "　　RETURN (\n" +
                "        　　SELECT t.new_geom  \n" +
                "        　　FROM gis.csv_1w_new t where t.id=2" +
                "        　 )";
        executeSql(sql_udf);
        String test = "select * from dbo.getGeom;";
        executeSql(test);
    }
}
