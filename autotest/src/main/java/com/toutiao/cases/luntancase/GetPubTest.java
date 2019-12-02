package com.toutiao.cases.luntancase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toutiao.config.TestConfig;
import com.toutiao.model.luntanmd.GetPubCase;
import com.toutiao.utils.DatabaseUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

@Slf4j
public class GetPubTest {

    @Test(dependsOnGroups = "loginTrue",dataProvider = "getPubData",description = "论坛/发图文")
    public void getPub(GetPubCase getPubCase){
        String result = getResult(getPubCase);
        JSONObject jsonObject = JSON.parseObject(result);
        log.info("实际结果：{}",jsonObject);
        Assert.assertEquals(getPubCase.getExpResult(),jsonObject.get("status"));


    }

    private String getResult(GetPubCase getPubCase) {
        String result = null;
        try {
            OkHttpClient client = new OkHttpClient();
            String url = TestConfig.getPubUrl+TestConfig.urlConstant+
                    "&key="+TestConfig.key+"&user_id="+getPubCase.getUser_id();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    @DataProvider(name = "getPubData")
    private Object[][] getData() throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();
        Object[][] result = new Object[][]{
                {session.selectOne("getPubCase",1)}
        };
        return result;
    }

}
