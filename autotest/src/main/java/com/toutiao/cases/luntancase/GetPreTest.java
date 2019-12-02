package com.toutiao.cases.luntancase;

import com.toutiao.config.TestConfig;
import com.toutiao.model.luntanmd.GetPreCase;
import com.toutiao.utils.DatabaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GetPreTest {

    @Test(dependsOnGroups = "loginTrue",description = "论坛/发预测贴")
    public void getPre() throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();
        GetPreCase getPreCase = session.selectOne("getPreCase",1);
        String result = getResult(getPreCase);
        log.info("实际结果:{}",result);
        Assert.assertTrue(result.contains("\"status\":1"));
    }

    private String getResult(GetPreCase getPreCase) throws IOException {

        String result = null;
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_id",getPreCase.getUser_id()));
            String param = EntityUtils.toString(new UrlEncodedFormEntity(params),"utf-8");
            String url = TestConfig.getPreUrl+"&key="+TestConfig.key+TestConfig.urlConstant;
            HttpGet get = new HttpGet(url+param);
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
