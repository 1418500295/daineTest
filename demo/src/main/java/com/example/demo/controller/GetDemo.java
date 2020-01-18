package com.example.demo.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping(value = "v1")
@RestController
public class GetDemo {

    @RequestMapping(value = "/v2/getDemo",method = RequestMethod.GET)
    public Map<String,String> getList(@RequestParam String name,
                                      @RequestParam Integer age){
        Map<String,String> mapList =new HashMap<>();
        mapList.put("name","daine");
        mapList.put("age","13");

        return mapList;



    }




}
