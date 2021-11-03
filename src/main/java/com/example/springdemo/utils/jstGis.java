package com.example.springdemo.utils;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class jstGis {

    public static void main(String[] args) throws ParseException {
        GeometryFactory geometryFactory = new GeometryFactory();
        WKTReader reader = new WKTReader(geometryFactory);
        List<String> data = new ArrayList<>();
        List<String> result = new ArrayList<>();
        String sql = "select st_astext(t.geom) from gis.cn_shi_data t where t.gid<5;";
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
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        for (int i = 0; i < data.size(); i++) {
            MultiPolygon multipolygon = (MultiPolygon) reader.read(data.get(i));
            double area = multipolygon.getArea();
            BigDecimal bd1 = new BigDecimal(area);
            result.add(bd1.toPlainString());
        }
        System.out.println(result);

    }
}


