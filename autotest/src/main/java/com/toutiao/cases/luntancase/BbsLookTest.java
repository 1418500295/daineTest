package com.toutiao.cases.luntancase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.toutiao.config.TestConfig;
import com.toutiao.model.luntanmd.BbsLookCase;
import com.toutiao.utils.DatabaseUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
@Log4j2
public class BbsLookTest {

    @Test(dependsOnGroups = "loginTrue",description = "关注列表")
    public void bbsLook() throws IOException {
        try {
            SqlSession session = DatabaseUtil.getSqlsession();
            BbsLookCase bbsLookCase = session.selectOne("bbsLookCase",1);
            String result = getResult(bbsLookCase);
            log.info("实际结果："+result);
            JSONObject jsonObject = JSON.parseObject(result);

            Assert.assertEquals(1,jsonObject.get("status"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String getResult(BbsLookCase bbsLookCase) throws IOException {
        String result = null;
        try {
            String url = TestConfig.bbsLookUrl+"&key="+TestConfig.key+"&user_id="+bbsLookCase.getUser_id();
            HttpGet get = new HttpGet(url);
            HttpResponse response = TestConfig.defaultHttpClient.execute(get);
            result = EntityUtils.toString(response.getEntity(),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
