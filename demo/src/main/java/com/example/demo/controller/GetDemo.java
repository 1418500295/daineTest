package com.example.demo.controller;

import com.example.demo.model.Person;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/getPath/{name}/{age}",method = RequestMethod.GET)
    public Map<String,String > getMap(@PathVariable String name,
                                      @PathVariable String age){
        Map<String,String> map = new HashMap<>();
        map.put("name","tom");
        map.put("age","13");
        return map;

    }

    @RequestMapping(value = "/getPerson",method = RequestMethod.POST)
    public Person getPerson(@RequestParam String name,
                            @RequestParam int age
    ){
        Person p = new Person();
        p.setSize(12);
        p.setHeight(182);
        p.setNumber("15");
        return p;

    }

    @RequestMapping(value = "/getMy",method = RequestMethod.POST)
    public Person getMy(@RequestBody Person person){

        Person p = new Person();
        p.setNumber("18");
        p.setHeight(184);
        p.setSize(78);
        return p;

    }






}
