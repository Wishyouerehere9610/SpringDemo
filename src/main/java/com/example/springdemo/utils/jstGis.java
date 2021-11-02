package com.example.springdemo.utils;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class jstGis {

    public static void main(String[] args) throws ParseException {
        GeometryFactory geometryFactory = new GeometryFactory();
        WKTReader reader = new WKTReader(geometryFactory);
        Polygon polygon = (Polygon) reader.read("POLYGON((20 10, 30 0, 40 10, 30 20, 20 10))");
        System.out.println(polygon.getArea());
    }


//    private static Geometry lonlat2WebMactor(Geometry geom) {
//        Geometry res = null;
//        try {
//            CoordinateReferenceSystem crsTarget = CRS.decode("EPSG:3857");
//            // 投影转换
//            MathTransform transform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, crsTarget);
//            res = JTS.transform(geom, transform);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return res;
//    }

//    public static double getAreaByGeoJson(String geojson) {
//        JSONObject obj = JSONObject.parseObject(geojson);
//        JSONArray arr = obj.getJSONArray("features");
//        JSONObject nodeObj = JSONObject.parseObject(arr.getString(0));
//        double area = 0;
//        try {
//            GeometryJSON gjson = new GeometryJSON(15);
//            Reader reader = new StringReader(nodeObj.toString());
//            Geometry geom = gjson.read(reader);
//            geom = lonlat2WebMactor(geom);
//            if(geom != null) {
//                area = geom.getArea();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return area;
//    }

}
