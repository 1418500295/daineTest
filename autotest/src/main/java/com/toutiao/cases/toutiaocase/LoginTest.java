package com.toutiao.cases.toutiaocase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toutiao.config.TestConfig;
import com.toutiao.model.InterfaneName;
import com.toutiao.model.toutiaomd.LoginCase;
import com.toutiao.utils.ConfigFile;
import com.toutiao.utils.DatabaseUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class LoginTest {

    @BeforeTest(groups = "loginTrue",description = "测试之前的准备工作")
    public void beforeTest() throws IOException {
        TestConfig.urlConstant = "&device=ios&device_Id=C9E5FEE0-2E0D-4D33-8522-9BD38A282221&version=2.3.2&";
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaneName.TTHOST,InterfaneName.LOGIN);
        TestConfig.tjUrl = ConfigFile.getUrl(InterfaneName.TTHOST,InterfaneName.TJURL);
        TestConfig.gcListUrl = ConfigFile.getUrl(InterfaneName.LTHOST,InterfaneName.GCURL);
        TestConfig.expListUrl = ConfigFile.getUrl(InterfaneName.LTHOST,InterfaneName.EXPURL);
        TestConfig.bbsLookUrl = ConfigFile.getUrl(InterfaneName.LTHOST,InterfaneName.BBSLOOK);
        TestConfig.replyUrl = ConfigFile.getUrl(InterfaneName.LTHOST,InterfaneName.REPLYURL);
        TestConfig.searchUrl = ConfigFile.getUrl(InterfaneName.LTHOST,InterfaneName.SEARCHURL);
        TestConfig.likeUrl = ConfigFile.getUrl(InterfaneName.LTHOST,InterfaneName.LIKEURL);
        TestConfig.favoriUrl = ConfigFile.getUrl(InterfaneName.LTHOST,InterfaneName.FAVORITE);
        TestConfig.expertHomeUrl = ConfigFile.getUrl(InterfaneName.LTHOST,InterfaneName.EXPERTHOME);
        TestConfig.gsListUrl = ConfigFile.getUrl(InterfaneName.TTHOST,InterfaneName.GSLIST);
        TestConfig.shareUrl = ConfigFile.getUrl(InterfaneName.TTHOST,InterfaneName.SHARE);
        TestConfig.getPubUrl = ConfigFile.getUrl(InterfaneName.LTHOST,InterfaneName.GETPUB);
        TestConfig.getPreUrl = ConfigFile.getUrl(InterfaneName.LTHOST,InterfaneName.GETPREDICT);
        TestConfig.userCenterUrl = ConfigFile.getUrl(InterfaneName.LTHOST,InterfaneName.USERCENTER);
        TestConfig.myItemUrl = ConfigFile.getUrl(InterfaneName.LTHOST,InterfaneName.MYITEM);
        TestConfig.myFavorUrl = ConfigFile.getUrl(InterfaneName.LTHOST,InterfaneName.MYFAVOR);
        TestConfig.defaultHttpClient = new DefaultHttpClient();


    }
    @Test(groups = "loginTrue",description = "用户登陆成功")
    public void loginTrue() throws IOException {

        try {
            SqlSession session = DatabaseUtil.getSqlsession();
            LoginCase loginCase = session.selectOne("loginCase",1);
            String result = getResult(loginCase);
            log.info("响应结果类型："+result.getClass());
            log.info("实际结果"+result);
            JSONObject jsonObject = JSON.parseObject(result);
            JSONObject param = (JSONObject) jsonObject.get("result");
            TestConfig.key = param.get("Safety").toString();
            log.info("登陆的key值:"+TestConfig.key);
            Assert.assertEquals(1,jsonObject.get("status"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test(groups = "loginFalse",description = "登录失败")
    public void loginFalse() throws IOException {
        try {
            SqlSession session = DatabaseUtil.getSqlsession();
            LoginCase loginCase = session.selectOne("loginCase",2);
            String result = getResult(loginCase);
            log.info("实际结果："+result);
            Assert.assertFalse(result.contains("\"status\":1"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getResult(LoginCase loginCase) throws IOException {
        String result = null;
        try {
            System.out.println(TestConfig.loginUrl);
            HttpPost post = new HttpPost(TestConfig.loginUrl);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("account",loginCase.getAccount()));
            params.add(new BasicNameValuePair("password",loginCase.getPassword()));
            post.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = TestConfig.defaultHttpClient.execute(post);
            result = EntityUtils.toString(response.getEntity(),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;

    }
}








