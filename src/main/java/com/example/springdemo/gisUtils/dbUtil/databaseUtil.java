package com.example.springdemo.gisUtils.dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class databaseUtil {
    //gp admin query sql
    public static List<String> getConnection(String sql) {
        List<String> data = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager
                    .getConnection("jdbc:postgresql://10.101.16.66:5432/test",
                            "gpadmin", "7cFe8Hjf9mX");
//            System.out.println("Opened database successfully");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String val = rs.getString(1);
                data.add(val);
            }
            st.close();
            conn.close();
//            System.out.println("Close database successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return data;
    }

    // gp admin update sql
    public static void executeSql(String sql) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager
                    .getConnection("jdbc:postgresql://10.101.16.66:5432/test",
                            "gpadmin", "7cFe8Hjf9mX");
//            System.out.println("Opened database successfully");
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            st.close();
            conn.close();
//            System.out.println("Close database successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    //update nebula-Gp 数据表 coordinates_to_wkt预处理 sql
    public static List<String> db_coordinates_to_wkt(String sql) {
        List<String> data = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager
                    .getConnection("jdbc:postgresql://10.5.24.18:2345/aiworks",
                            "gpadmin", "gp2020");
            System.out.println("Opened database successfully");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String longitude = rs.getString(1);
                String latitude = rs.getString(2);
                String point = "POINT(" + longitude + " " + latitude + ")";
                data.add(point);
            }
            st.close();
            conn.close();
            System.out.println("Close database successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return data;
    }

    //update nebula-Gp sql
    public static void updateGPSql(String sql) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager
                    .getConnection("jdbc:postgresql://10.5.24.18:2345/aiworks",
                            "gpadmin", "gp2020");
//            System.out.println("Opened database successfully");
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            st.close();
            conn.close();
//            System.out.println("Close database successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

}
