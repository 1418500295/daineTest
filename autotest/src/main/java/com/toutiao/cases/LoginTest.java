package com.toutiao.cases;

import com.toutiao.config.TestConfig;
import com.toutiao.model.InterfaneName;
import com.toutiao.model.LoginCase;
import com.toutiao.utils.ConfigFile;
import com.toutiao.utils.DatabaseUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;




import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class LoginTest {
    private static final String host = "http://api.toutiao.353386.com/?C=passport&A=login&";

    @BeforeTest(groups = "loginTrue",description = "测试之前的准备工作")
    public void beforeTest() throws IOException {

        TestConfig.gcListUrl = ConfigFile.getUrl(InterfaneName.GCURL);
        TestConfig.expListUrl = ConfigFile.getUrl(InterfaneName.EXPURL);
        TestConfig.bbsLookUrl = ConfigFile.getUrl(InterfaneName.BBSLOOK);
        TestConfig.replyUrl = ConfigFile.getUrl(InterfaneName.REPLYURL);
        TestConfig.defaultHttpClient = new DefaultHttpClient();


    }
    @Test(groups = "loginTrue",description = "用户登陆成功")
    public void loginTrue() throws IOException {

        SqlSession session = DatabaseUtil.getSqlsession();
        LoginCase loginCase = session.selectOne("loginCase",1);
        String result = getResult(loginCase);
        log.info("响应结果类型："+result.getClass());
        log.info("实际结果"+result);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject param = (JSONObject) jsonObject.get("result");
        TestConfig.key = param.get("Safety").toString();
        log.info("登陆key值："+TestConfig.key);
        Assert.assertEquals(1,jsonObject.get("status"));

    }

    private String getResult(LoginCase loginCase) throws IOException {
        String url = "device="+loginCase.getDevice()+"&device_Id="+loginCase.getDevice_Id()+
                "&version="+loginCase.getVersion();
        HttpPost post =new HttpPost(host+url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("account",loginCase.getAccount()));
        params.add(new BasicNameValuePair("password",loginCase.getPassword()));
        post.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        return result;

    }
}






