package com.example.springdemo.utils;

import com.vividsolutions.jts.io.ParseException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class postGis {
    public static void main(String[] args) throws ParseException {
        List<String> data = new ArrayList<>();
        long startTime = System.currentTimeMillis();   //获取开始时间
//        String sql = "select st_area(t.geom) from gis.osm_buildings_a_free_1 t where t.gid<100000;";//返回的是geometry编码
//        String sql = "select st_perimeter(t.geom) from gis.osm_buildings_a_free_1 t where t.gid<10000;";
        String sql = "select st_buffer(t.geom,10) from gis.osm_buildings_a_free_1 t where t.gid<10000;";
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager
                    .getConnection("jdbc:postgresql://10.101.16.66:5432/test",
                            "gpadmin", "7cFe8Hjf9mX");
            System.out.println("Opened database successfully");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String val = rs.getString(1);
                data.add(val);
            }
//            System.out.println(data);
            st.close();
            conn.close();
            System.out.println("Close database successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        long endTime = System.currentTimeMillis(); //获取结束时间
        double time = endTime - startTime;
        System.out.println("程序运行时间： " + time + "ms");

    }

}
