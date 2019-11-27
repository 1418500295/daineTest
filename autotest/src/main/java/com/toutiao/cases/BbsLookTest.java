package com.toutiao.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toutiao.config.TestConfig;
import com.toutiao.model.BbsLookCase;
import com.toutiao.utils.DatabaseUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
@Log4j2
public class BbsLookTest {

    @Test(dependsOnGroups = "loginTrue",description = "关注列表")
    public void bbsLook() throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();
        BbsLookCase bbsLookCase = session.selectOne("bbsLookCase",1);
        String result = getResult(bbsLookCase);
        log.info("实际结果："+result);
        JSONObject jsonObject = JSON.parseObject(result);
        Assert.assertEquals(1,jsonObject.get("status"));

//        Assert.assertTrue(result.contains("\"status\":1"));


    }

    private String getResult(BbsLookCase bbsLookCase) throws IOException {
        String url = TestConfig.bbsLookUrl+"&key="+TestConfig.key+"&user_id="+bbsLookCase.getUser_id();
        HttpGet get = new HttpGet(url);
        HttpResponse response = TestConfig.defaultHttpClient.execute(get);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        return result;
    }
}
