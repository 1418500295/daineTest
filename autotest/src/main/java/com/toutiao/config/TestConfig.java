package com.toutiao.config;

import com.toutiao.model.InterfaneName;
import com.toutiao.utils.ConfigFile;
import lombok.Data;
import org.apache.http.client.CookieStore;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.omg.CORBA.INITIALIZE;

@Data
public class TestConfig {

    //url中固定请求参数
    public static  String urlConstant;

    public static String loginUrl;

    public static String getLoginUrl;

    public static CookieStore store;

    public static String gcListUrl;

    public static String expListUrl;

    public static String bbsLookUrl;

    public static String replyUrl;

    public static String searchUrl;

    public static String likeUrl;

    public static String favoriUrl;

    public static String expertHomeUrl;

    public static String tjUrl;

    public static String gsListUrl,shareUrl,getPubUrl,getPreUrl,userCenterUrl,myItemUrl;




    //存储登陆返回的key值，相当于token
    public static String key;

    public static DefaultHttpClient defaultHttpClient;







}
