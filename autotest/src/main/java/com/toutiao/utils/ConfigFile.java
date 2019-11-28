package com.toutiao.utils;

import com.toutiao.model.InterfaneName;
import lombok.extern.log4j.Log4j2;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFile {
    private static ResourceBundle bundle =ResourceBundle.getBundle("application", Locale.CHINA);

    public static String getUrl(InterfaneName host,InterfaneName name) {

        String address ;
        String uri = "";
        String testUrl = null;
        if (host == InterfaneName.LTHOST) {

            address = bundle.getString("lt.host");

            if (name == InterfaneName.REPLYURL) {
                uri = bundle.getString("reply.url");
            } else if (name == InterfaneName.SEARCHURL) {
                uri = bundle.getString("search.url");

            } else if (name == InterfaneName.BBSLOOK) {
                uri = bundle.getString("look.url");
            } else if (name == InterfaneName.EXPERTHOME) {

                uri = bundle.getString("expert_home.url");
            } else if (name == InterfaneName.EXPURL) {

                uri = bundle.getString("exp.url");

            } else if (name == InterfaneName.FAVORITE) {

                uri = bundle.getString("favorite.url");

            } else if (name == InterfaneName.GCURL) {

                uri = bundle.getString("gc.url");
            } else if (name == InterfaneName.LIKEURL) {

                uri = bundle.getString("like.url");
            }
            testUrl = address + uri;

        } else if (host == InterfaneName.TTHOST) {

            address = bundle.getString("tt.host");

            if (name == InterfaneName.LOGIN) {
                uri = bundle.getString("login.url");

            }else if (name == InterfaneName.TJURL){
                uri = bundle.getString("tj.url");
            }

            testUrl = address + uri;
        }
        return testUrl;
    }

}
