package com.example.springdemo.utils;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class jtsGis {

    public static void main(String[] args) throws ParseException {
        GeometryFactory geometryFactory = new GeometryFactory();
        WKTReader reader = new WKTReader(geometryFactory);
        List<String> data = new ArrayList<>();
        List<Geometry> result = new ArrayList<>();
        String sql = "select st_astext(t.geom) from gis.osm_buildings_a_free_1 t where t.gid<10000;";
        long startTime = System.currentTimeMillis();   //获取开始时间
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
            System.out.println("Close database successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        for (int i = 0; i < data.size(); i++) {
            MultiPolygon multipolygon = (MultiPolygon) reader.read(data.get(i));
            //计算面积
//            double area = multipolygon.getArea();
//            BigDecimal bd1 = new BigDecimal(area);
            //计算周长
//            double perimeter = multipolygon.getLength();
//            BigDecimal bd1 = new BigDecimal(perimeter);
            //计算缓冲区
            Geometry buffer = multipolygon.buffer(10);
            result.add(buffer);
//            result.add(bd1.toPlainString());
        }
//        System.out.println(result);
        long endTime = System.currentTimeMillis(); //获取结束时间
        double time = endTime - startTime;
        System.out.println("程序运行时间： " + time + "ms");


    }
}


