package com.toutiao.utils;

import com.toutiao.model.InterfaneName;
import lombok.extern.log4j.Log4j2;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFile {

    private static ResourceBundle bundle =ResourceBundle.getBundle("application", Locale.CHINA);

    public static String getUrl(InterfaneName host,InterfaneName urlName) {

        String address ;
        String uri = "";
        String testUrl = null;
        try {
            if (host == InterfaneName.LTHOST) {

                address = bundle.getString("lt.host");

                if (urlName == InterfaneName.REPLYURL) {
                    uri = bundle.getString("reply.url");
                } else if (urlName == InterfaneName.SEARCHURL) {
                    uri = bundle.getString("search.url");

                } else if (urlName == InterfaneName.BBSLOOK) {
                    uri = bundle.getString("look.url");
                } else if (urlName == InterfaneName.EXPERTHOME) {

                    uri = bundle.getString("expert_home.url");
                } else if (urlName == InterfaneName.EXPURL) {

                    uri = bundle.getString("exp.url");

                } else if (urlName == InterfaneName.FAVORITE) {

                    uri = bundle.getString("favorite.url");

                } else if (urlName == InterfaneName.GCURL) {

                    uri = bundle.getString("gc.url");
                } else if (urlName == InterfaneName.LIKEURL) {

                    uri = bundle.getString("like.url");
                }else if (urlName == InterfaneName.GETPUB){

                    uri = bundle.getString("get_pub.url");
                }else if (urlName == InterfaneName.GETPREDICT){

                    uri = bundle.getString("get_predict.url");

                }else if (urlName == InterfaneName.USERCENTER){
                    uri  = bundle.getString("user.url");
                }else if (urlName == InterfaneName.MYITEM){
                    uri = bundle.getString("my_item.url");
                }else if (urlName == InterfaneName.MYFAVOR){

                    uri = bundle.getString("my_favor.url");

                }

                testUrl = address + uri;

            } else if (host == InterfaneName.TTHOST) {

                address = bundle.getString("tt.host");

                if (urlName == InterfaneName.LOGIN) {
                    uri = bundle.getString("login.url");

                }else if (urlName == InterfaneName.TJURL){
                    uri = bundle.getString("tj.url");

                }else if (urlName == InterfaneName.GSLIST){
                    uri = bundle.getString("gs_list.url");
                }else if (urlName == InterfaneName.SHARE){

                    uri = bundle.getString("share.url");
                }

                testUrl = address + uri;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return testUrl;
    }

}
