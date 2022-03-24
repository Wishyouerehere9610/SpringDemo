package com.example.springdemo.gisTest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static com.example.springdemo.gisUtils.commonUtil.getPrefixNameOne;
import static com.example.springdemo.gisUtils.commonUtil.isStartWithNumber;

public class randomTest {
    public static void main(String[] args) throws Exception {
//      入参
        List<String> fileList = new ArrayList<>();
        fileList.add("城市快速路_polyline.dbf");
        fileList.add("城市快速路_polyline.prj");
        fileList.add("城市快速路_polyline.shp");
        fileList.add("城市快速路_polyline.shx");
        fileList.add("城市快速路_polyline.dbf");
        fileList.add("地铁轻轨_polyline.shp");
        fileList.add("城市快速路_polyline.dbf");

//      开始判断有没有重复上传的文件
        Set<String> fileSet = new HashSet<>(fileList);
        if (fileList.size() != fileSet.size()) {
            System.out.println("请不要上传重复的元素！");
        }

//      判断有没有上传不是同一份的文件
        List<String> fileNames = new ArrayList<>();
        fileList.forEach(e -> {
            fileNames.add(getPrefixNameOne(e));
        });
        Set<String> fileNamesSet = new HashSet<>(fileNames);
        if (fileNamesSet.size() != 1) {
            System.out.println("请上传同一份gis文件！");
        }

        System.out.println(fileList);

    }


}
