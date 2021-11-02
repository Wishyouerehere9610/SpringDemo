package com.example.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GisInfoController {

    @Autowired
    private JdbcTemplate jdbc;

    @RequestMapping("/geomData")
    public Object get() {
        String sql = "select st_area(t.geom) from gis.cn_sheng_data t where t.gid=798;";
        return jdbc.queryForList(sql);
    }

}
