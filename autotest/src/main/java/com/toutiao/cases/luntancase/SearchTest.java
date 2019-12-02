package com.toutiao.cases.luntancase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toutiao.config.TestConfig;
import com.toutiao.model.luntanmd.SearchCase;
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
public class SearchTest {
    @Test(dependsOnGroups = "loginTrue",description = "论坛/搜索")
    public void getSearch() throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();
        SearchCase searchCase = session.selectOne("searchCase",1);
        String result = getResult(searchCase);
        log.info("实际结果："+result);
        JSONObject jsonObject = JSON.parseObject(result);
        Assert.assertEquals(1,jsonObject.get("status"));
    }

    private String getResult(SearchCase searchCase) throws IOException {
        String result = null;
        try {
            String url = TestConfig.searchUrl+"key="+TestConfig.key+"&user_id="+searchCase.getUser_id();
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
