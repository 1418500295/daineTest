package com.toutiao.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toutiao.config.TestConfig;
import com.toutiao.model.ExpListCase;
import com.toutiao.model.ExpertHomeCase;
import com.toutiao.utils.DatabaseUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
@Log4j2
public class ExpertHomeTest {
    @Test(dependsOnGroups = "loginTrue",dataProvider = "getExpertHomeData",description = "专家详情页/正确")
    public void getExpertHome1(ExpertHomeCase expertHomeCase) throws IOException {
        String result = getResult(expertHomeCase);
        log.info("实际结果："+result);
        Assert.assertTrue(result.contains("\"status\":1"));


    }

    @Test(dependsOnGroups = "loginTrue",dataProvider = "getExpertHomeData",description = "专家详情页/错误的member_id")
    public void getExpertHome2(ExpertHomeCase expertHomeCase) throws IOException {
        String result = getResult(expertHomeCase);
        log.info("实际结果："+result);
        Assert.assertFalse(result.contains("\"status\":1"));
    }

    @Test(dependsOnGroups = "loginTrue",dataProvider = "getExpertHomeData",description = "专家详情页/非法的user_id")
    public void getExpertHome3(ExpertHomeCase expertHomeCase) throws IOException {
        String result = getResult(expertHomeCase);
        log.info("实际结果："+result);
        JSONObject jsonObject = JSON.parseObject(result);
        Assert.assertNotEquals(1,jsonObject.getString("status"));

    }

    private String getResult(ExpertHomeCase expertHomeCase) throws IOException {
        String url = TestConfig.expertHomeUrl+"key="+TestConfig.key+"&user_id="+expertHomeCase.getUser_id()
                +"&member_id="+expertHomeCase.getMember_id();
        HttpGet get = new HttpGet(url);
        HttpResponse response = TestConfig.defaultHttpClient.execute(get);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        return result;
    }

    @DataProvider(name = "getExpertHomeData")
    private Object[][] getExpertHome(Method method) throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();
        Object[][] result = null;
        if (method.getName().equals("getExpertHome1")){
            result = new Object[][]{
                    {session.selectOne("expertHomeCase",1)}
            };

        }else if (method.getName().equals("getExpertHome2")){
            result = new Object[][]{
                    {session.selectOne("expertHomeCase",2)}
            };

        }else if (method.getName().equals("getExpertHome3")){
            result = new Object[][]{
                    {session.selectOne("expertHomeCase",3)}
            };
        }

        return result;

    }
}
