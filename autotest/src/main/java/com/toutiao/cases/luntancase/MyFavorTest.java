package com.toutiao.cases.luntancase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.internal.$Gson$Preconditions;
import com.toutiao.config.TestConfig;
import com.toutiao.model.luntanmd.MyFavorCase;
import com.toutiao.utils.DatabaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MyFavorTest {


//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;

    @Test(dependsOnGroups = "loginTrue",dataProvider = "getMyFavorData",description = "论坛/我的收藏")
    public void getMyFavor(MyFavorCase myFavorCase){
        String result = getResult(myFavorCase);
        JSONObject jsonObject = JSON.parseObject(result);
        log.info("实际结果：{}",result);
        Assert.assertEquals(1,jsonObject.get("status"));

    }

    private String getResult(MyFavorCase myFavorCase) {
        String result = null;

        try {
            String url = TestConfig.myFavorUrl+"&key="+TestConfig.key+TestConfig.urlConstant;
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("p",myFavorCase.getP()));
            list.add(new BasicNameValuePair("user_id",myFavorCase.getUser_id()));
            list.add(new BasicNameValuePair("type",myFavorCase.getType()));
            String params = EntityUtils.toString(new UrlEncodedFormEntity(list),"utf-8");
            HttpGet get = new HttpGet(url+params);
            HttpResponse response = TestConfig.defaultHttpClient.execute(get);
            result = EntityUtils.toString(response.getEntity(),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;


    }

    @DataProvider(name = "getMyFavorData")
    private Object[][] getData() throws IOException {

        SqlSession session = DatabaseUtil.getSqlsession();
        Object[][] result = new Object[][]{
                {session.selectOne("myFavorCase",1)}
        };
        return result;
    }
}
