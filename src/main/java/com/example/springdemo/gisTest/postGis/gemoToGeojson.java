package com.example.springdemo.gisTest.postGis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vividsolutions.jts.io.ParseException;

import java.io.IOException;
import java.util.Iterator;

import static com.example.springdemo.gisUtils.postGisUtil.postGisUtil.geojsonConverter;

public class gemoToGeojson {
    public static void main(String[] args) throws ParseException, IOException {
        JSONObject json = JSONObject.parseObject(geojsonConverter("gis.geojsonTest t").get(0));
        //循环json处理并保存
        Iterator<Object> features = json.getJSONArray("features").iterator();
        while (features.hasNext()) {
            JSONObject next = (JSONObject) features.next();
            next.getJSONObject("properties").remove("geom");
        }
        System.out.println(json);
    }
}


