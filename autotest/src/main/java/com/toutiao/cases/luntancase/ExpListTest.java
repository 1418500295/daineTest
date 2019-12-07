package com.toutiao.cases.luntancase;

import com.toutiao.config.TestConfig;
import com.toutiao.model.luntanmd.ExpListCase;
import com.toutiao.utils.DatabaseUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.io.IOException;
@Log4j2
public class ExpListTest {
    @Test(dependsOnGroups = "loginTrue",description = "预测贴列表")
    public void expList() throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();
        ExpListCase expListCase = session.selectOne("expListCase",1);
        String result = getResult(expListCase);
        log.info("实际结果："+result);
        JSONObject jsonObject = new JSONObject(result);
        Assert.assertEquals(1,jsonObject.get("status"));
    }

    private String getResult(ExpListCase expListCase) throws IOException {
        String result = null;
        try {
            String url = TestConfig.expListUrl+"&key="+TestConfig.key+"&class_id="
            +expListCase.getClass_id()+"&user_id="+expListCase.getUser_id()+"&period="+expListCase.getPeriod();
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
