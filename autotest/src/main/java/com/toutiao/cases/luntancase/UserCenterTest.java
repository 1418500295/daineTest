package com.toutiao.cases.luntancase;

import com.toutiao.config.TestConfig;
import com.toutiao.model.luntanmd.UserCenterCase;
import com.toutiao.utils.DatabaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserCenterTest {

    @Test(dependsOnGroups = "loginTrue",dataProvider = "getUserTest",description = "论坛/个人中心")
    public void getUserCenter(UserCenterCase userCenterCase){
        String result = getResult(userCenterCase);
        log.info("实际结果：{}",result);
        Assert.assertTrue(result.contains("\"status\":1"));

    }

    private String getResult(UserCenterCase userCenterCase) {

        String result = null;
        try {
            List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
            list.add(new BasicNameValuePair("user_id",userCenterCase.getUser_id()));
            String param = EntityUtils.toString(new UrlEncodedFormEntity(list));
            String url = TestConfig.userCenterUrl+"&key="+TestConfig.key+TestConfig.urlConstant;
            HttpGet get = new HttpGet(url+param);
            HttpResponse response = TestConfig.defaultHttpClient.execute(get);
            result = EntityUtils.toString(response.getEntity(),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @DataProvider(name = "getUserTest")
    private Object[][] getData() throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();
        Object[][] result = new Object[][]{
                {session.selectOne("userCenterCase",1)}
        };
        return result;
    }
}
