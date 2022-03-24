package com.example.springdemo.gisUtils.postGisUtil;

import com.vividsolutions.jts.io.ParseException;
import io.github.matthieun.conversion.WktWkbConverter;

public class pointWktToWkb {
    public static String wtkToWkb(String wkt) throws ParseException {
        WktWkbConverter converter = new WktWkbConverter();
        String wkb = converter.wktToWkbHexadecimal(wkt);
        return wkb;
    }
}
