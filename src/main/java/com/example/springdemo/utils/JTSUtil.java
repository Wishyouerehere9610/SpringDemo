package com.example.springdemo.utils;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import java.util.ArrayList;
import java.util.List;


import java.math.BigDecimal;

public class JTSUtil {
    public static List<BigDecimal> calArea(List<String> data) throws ParseException {
        GeometryFactory geometryFactory = new GeometryFactory();
        WKTReader reader = new WKTReader(geometryFactory);
        List<BigDecimal> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            MultiPolygon multipolygon = (MultiPolygon) reader.read(data.get(i));
            double area = multipolygon.getArea();
            BigDecimal bd1 = new BigDecimal(area);
            result.add(bd1);
        }
        return result;
    }

    public static List<BigDecimal> calPerimeter(List<String> data) throws ParseException {
        GeometryFactory geometryFactory = new GeometryFactory();
        WKTReader reader = new WKTReader(geometryFactory);
        List<BigDecimal> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            MultiPolygon multipolygon = (MultiPolygon) reader.read(data.get(i));
            double perimeter = multipolygon.getLength();
            BigDecimal bd1 = new BigDecimal(perimeter);
            result.add(bd1);
        }
        return result;
    }

    public static List<Geometry> getBuffer(List<String> data) throws ParseException {
        GeometryFactory geometryFactory = new GeometryFactory();
        WKTReader reader = new WKTReader(geometryFactory);
        List<Geometry> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            MultiPolygon multipolygon = (MultiPolygon) reader.read(data.get(i));
            Geometry buffer = multipolygon.buffer(10);
            result.add(buffer);
        }
        return result;
    }
}

