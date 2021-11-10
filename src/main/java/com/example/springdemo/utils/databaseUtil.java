package com.example.springdemo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class databaseUtil {
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

}
