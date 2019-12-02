package com.toutiao.cases.luntancase;

import com.toutiao.config.TestConfig;
import com.toutiao.model.luntanmd.GcListCase;
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
public class GcListTest {
    @Test(dependsOnGroups = "loginTrue",description = "心水论坛/广场列表")
    public void gcList() throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();
        GcListCase gcListCase = session.selectOne("gcListCase",1);
        String result = getResult(gcListCase);
        log.info("实际结果："+result);
        JSONObject jsonObject = new JSONObject(result);
        Assert.assertEquals(1,jsonObject.get("status"));

}

    private String getResult(GcListCase gcListCase) throws IOException {
        String result = null;
        try {
            String url = TestConfig.gcListUrl+"&key="+TestConfig.key+"&user_id="+gcListCase.getUser_id();
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
