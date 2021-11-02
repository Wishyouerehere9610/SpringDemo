package com.example.springdemo.utils;

import com.vividsolutions.jts.io.ParseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class postGis {
    public static void main(String[] args) throws ParseException {
        List<String> data = new ArrayList<>();
        String sql = "select st_area(t.geom) from gis.cn_sheng_data t where t.gid=798;";
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
            System.out.println(data);
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


//        String sql = "select st_area(t.geom) from gis.cn_sheng_data t where t.gid=798;";
//        List<Map<String, Object>> result = jdbc.queryForList(sql);
//        System.out.println(result);
    }

}
