package com.toutiao.utils;

import com.toutiao.model.InterfaneName;
import lombok.extern.log4j.Log4j2;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFile {
    private static ResourceBundle bundle =ResourceBundle.getBundle("application", Locale.CHINA);

    public static String getUrl(InterfaneName name){
        String address= bundle.getString("test.url");
        String uri = "" ;
        String testUrl;
        if(name == InterfaneName.LOGIN){
            uri = bundle.getString("get.url");
        }
        if (name == InterfaneName.POSTDEMO){
            uri = bundle.getString("post.url");
        }
        if (name == InterfaneName.GETPARAM){
            uri = bundle.getString("getparam.url");
        }
        if (name == InterfaneName.GETLIST){
            uri = bundle.getString("getjson.url");
        }

        if (name == InterfaneName.MYLOOK ){
            uri = bundle.getString("mylook.url");

        }
        if (name == InterfaneName.GCURL){
            uri = bundle.getString("gc.url");
        }
        if (name == InterfaneName.EXPURL){
            uri = bundle.getString("exp.url");
        }
        if (name == InterfaneName.BBSLOOK){
            uri = bundle.getString("look.url");
        }
        if (name == InterfaneName.REPLYURL){
           uri = bundle.getString("reply.url");
        }
        testUrl = address + uri;
        return testUrl;


}

}
