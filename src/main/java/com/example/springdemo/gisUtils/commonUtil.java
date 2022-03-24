package com.example.springdemo.gisUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class commonUtil {

    private static final Logger logger = LoggerFactory.getLogger(commonUtil.class);


    public static CoordinateReferenceSystem readPrjFile(String shpFilePath) throws Exception {
        ShapefileDataStoreFactory factory = new ShapefileDataStoreFactory();
        ShapefileDataStore dataStore = (ShapefileDataStore) factory.createDataStore(new File(shpFilePath).toURI().toURL());
        CoordinateReferenceSystem coordinateReferenceSystem = dataStore.getFeatureReader().getFeatureType().getCoordinateReferenceSystem();
        return coordinateReferenceSystem;
    }


    public static String getCoordinateSystemWKT(String path) {
        ShapefileDataStoreFactory factory = new ShapefileDataStoreFactory();
        ShapefileDataStore dataStore = null;
        try {
            dataStore = (ShapefileDataStore) factory.createDataStore(new File(path).toURI().toURL());
            return dataStore.getSchema().getCoordinateReferenceSystem().toWKT();
        } catch (UnsupportedOperationException | IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            dataStore.dispose();
        }
        return "";
    }


    public static ShapefileDataStore buildDataStore(String shpFilePath) {
        ShapefileDataStoreFactory factory = new ShapefileDataStoreFactory();
        try {
            ShapefileDataStore dataStore = (ShapefileDataStore) factory
                    .createDataStore(new File(shpFilePath).toURI().toURL());
            if (dataStore != null) {
                dataStore.setCharset(Charset.forName("UTF-8"));
            }
            return dataStore;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getSrid(String shpFilePath) throws IOException, FactoryException {
        ShapefileDataStore dataStore = buildDataStore(shpFilePath);
        String code = null;
        String wkt = dataStore.getSchema().getCoordinateReferenceSystem().toWKT();
        CoordinateReferenceSystem crsTarget = CRS.parseWKT(wkt);

        Integer epsg = CRS.lookupEpsgCode(crsTarget, true);
        code = "EPSG:" + epsg; //$NON-NLS-1$
        if (epsg == null) {
            // try non epsg
            code = CRS.lookupIdentifier(crsTarget, true);
        }
        dataStore.dispose();
        return code;

    }

    public static boolean isStartWithNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str.charAt(0)+"");
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public void recursion(Object oldObj, Object currentObj) {
        if (oldObj instanceof JSONArray && currentObj instanceof JSONArray) {
            JSONArray oldJsonArray = (JSONArray) oldObj;
            JSONArray currentJsonArray = (JSONArray) currentObj;
            int length1 = oldJsonArray.size();
            int length2 = currentJsonArray.size();
            int i = 0;
            int j = 0;
            while (i < length1 && j < length2) {
                if (!(oldJsonArray.get(i) instanceof JSONArray || oldJsonArray
                        .get(i) instanceof JSONObject)) {
                    oldJsonArray.set(i, currentJsonArray.get(j));
                } else {
                    recursion(oldJsonArray.get(i), currentJsonArray.get(j));
                }
                ++i;
                ++j;
            }
            int m = i;
            while (i < length1) {
                oldJsonArray.remove(m);
                ++i;
            }
            while (j < length2) {
                oldJsonArray.add(currentJsonArray.get(j));
                ++j;
            }
        } else if (oldObj instanceof JSONObject && currentObj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) currentObj;
            JSONObject oldJsonObject = (JSONObject) oldObj;
            Set<String> keySet = jsonObject.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (!oldJsonObject.containsKey(key)) {
                    oldJsonObject.put(key, jsonObject.get(key));
                } else {
                    if (oldJsonObject.get(key) instanceof JSONObject && jsonObject
                            .get(key) instanceof JSONObject) {
                        recursion(oldJsonObject.get(key), jsonObject.get(key));
                    } else if (oldJsonObject.get(key) instanceof JSONArray && jsonObject
                            .get(key) instanceof JSONArray) {
                        recursion(oldJsonObject.get(key), jsonObject.get(key));
                    } else {
                        oldJsonObject.put(key, jsonObject.get(key));
                    }
                }
            }
        }
    }

    public static String getPrefixNameOne(String filename){
        String caselsh = filename.substring(0,filename.lastIndexOf("."));
        return caselsh;
    }



}
