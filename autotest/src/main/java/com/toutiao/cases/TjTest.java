package com.toutiao.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toutiao.config.TestConfig;
import com.toutiao.model.TjCase;
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
public class TjTest {
    @Test(dependsOnGroups = "loginTrue",dataProvider = "getData",description = "头条/推荐")
    public void getTj(TjCase tjCase) throws IOException {
        String result = getResult(tjCase);
        log.info("实际结果："+result);
        Assert.assertTrue(result.contains("\"status\":1"));

    }

    private String getResult(TjCase tjCase) throws IOException {
        String url = TestConfig.tjUrl+"key="+TestConfig.key+"&user_id="+tjCase.getUser_id()+"&type="+tjCase.getType()
                +"&signature="+tjCase.getSignature();
        HttpGet get = new HttpGet(url);
        HttpResponse response = TestConfig.defaultHttpClient.execute(get);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        return result;
    }
    @Test(dependsOnGroups = "loginTrue",dataProvider = "getData",description = "头条/user_id为空")
    public void getTj2(TjCase tjCase) throws IOException {
        String result = getResult(tjCase);
        log.info("实际结果："+result);
        JSONObject jsonObject = JSON.parseObject(result);
        Assert.assertEquals(1,jsonObject.get("status"));

    }
    @Test(dependsOnGroups = "loginTrue",dataProvider = "getData",description = "头条/type为空")
    public void getTj3(TjCase tjCase) throws IOException {
        String result = getResult(tjCase);
        log.info("实际结果："+result);
        Assert.assertTrue(result.contains("\"status\":1"));

    }
    @Test(dependsOnGroups = "loginTrue",dataProvider = "getData",description = "头条/signature为空")
    public void getTj4(TjCase tjCase) throws IOException {
        String result = getResult(tjCase);
        log.info("实际结果："+result);
        JSONObject jsonObject = JSON.parseObject(result);
        Assert.assertNotEquals(1,jsonObject.get("status"));

    }
    @DataProvider(name = "getData")
    private Object[][] getTjData(Method method) throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();

        Object[][] result = null;
        if (method.getName().equals("getTj")) {
            result = new Object[][]{
                    {session.selectOne("tjCase", 1)}
            };
        } else if (method.getName().equals("getTj2")) {
            result = new Object[][]{
                    {session.selectOne("tjCase", 2)}
            };

        } else if (method.getName().equals("getTj3")) {
            result = new Object[][]{
                    {session.selectOne("tjCase", 3)}

            };
        }else if (method.getName().equals("getTj4")){
            result = new Object[][]{
                    {session.selectOne("tjCase",4)}
            };
        }
        return result;
    }
}
