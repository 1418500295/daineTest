package com.toutiao.cases.luntancase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toutiao.config.TestConfig;
import com.toutiao.model.luntanmd.LikeCase;
import com.toutiao.utils.DatabaseUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Log4j2
public class LikeTest {

    @Test(dependsOnGroups = "loginTrue",description = "论坛/点赞")
    public void getLike() throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();
        LikeCase likeCase = session.selectOne("likeCase",1);
        String result = getResult(likeCase);
        log.info("实际结果："+result);
        Assert.assertTrue(result.contains("\"status\":1"));

    }
    @Test(dependsOnGroups = "loginTrue",description = "点赞/错误的id")
    public void getLike2() throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();
        LikeCase likeCase = session.selectOne("likeCase",2);
        String result = getResult(likeCase);
        log.info("实际结果："+result);
        JSONObject jsonObject = JSON.parseObject(result);
        Assert.assertNotEquals(1,jsonObject.getString("status"));

    }

    private String getResult(LikeCase likeCase) throws IOException {
        String result = null;
        try {
            String url = TestConfig.likeUrl+"key="+TestConfig.key+"&user_id="+likeCase.getUser_id();
            System.out.println(url);
            HttpPost post = new HttpPost(url);
            List<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("id",likeCase.getId()));
            post.setEntity(new UrlEncodedFormEntity(param));
            HttpResponse response = TestConfig.defaultHttpClient.execute(post);
            result = EntityUtils.toString(response.getEntity(),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;


    }
}
