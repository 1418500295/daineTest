package com.toutiao.cases.luntancase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toutiao.config.TestConfig;
import com.toutiao.model.luntanmd.MyItemCase;
import com.toutiao.utils.DatabaseUtil;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MyItemTest {

    @Test(dependsOnGroups = "loginTrue",description = "论坛/我的帖子")
    public void getMyItem() throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();
        MyItemCase myItemCase = session.selectOne("myItemCase","1");
        String result = getResult(myItemCase);
        JSONObject jsonObject = JSON.parseObject(result);
        log.info("实际结果：{}",jsonObject);

        Assert.assertEquals(jsonObject.get("status"),1);

    }

    private String getResult(MyItemCase myItemCase) {
        String result = null;
        try {
            String url = TestConfig.myItemUrl+"&key="+TestConfig.key+TestConfig.urlConstant;
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("bbs_type",myItemCase.getBbs_type()));
            list.add(new BasicNameValuePair("uuid",myItemCase.getUuid()));
            list.add(new BasicNameValuePair("user_id",myItemCase.getUser_id()));
            list.add(new BasicNameValuePair("p",myItemCase.getP()));
            String params = EntityUtils.toString(new UrlEncodedFormEntity(list),"utf-8");
            HttpGet get = new HttpGet(url+params);
            HttpResponse response = TestConfig.defaultHttpClient.execute(get);
            result = EntityUtils.toString(response.getEntity(),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }
}
