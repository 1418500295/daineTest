package com.toutiao.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toutiao.config.TestConfig;
import com.toutiao.model.ReplyCase;
import com.toutiao.utils.DatabaseUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
@Log4j2
public class ReplyTest {
    @Test(dependsOnGroups = "loginTrue",description = "做出评论")
    public void replyTest() throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();
        ReplyCase replyCase = session.selectOne("replyCase",1);
        String result = getResult(replyCase);
       // log.info("实际结果："+result);
        JSONObject jsonObject = JSON.parseObject(result);
        Assert.assertEquals(1,jsonObject.get("status"));

        //Assert.assertTrue(result.contains("\"status\":1"));
    }

    private String getResult(ReplyCase replyCase) throws IOException {

        String url = TestConfig.bbsLookUrl+"key="+TestConfig.key+"&user_id="+replyCase.getUser_id();
        HttpPost post =new HttpPost(url);
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        param.add(new BasicNameValuePair("id",replyCase.getId()));
        param.add(new BasicNameValuePair("content",replyCase.getContent()));
        post.setEntity(new UrlEncodedFormEntity(param));
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        return result;
    }
}
