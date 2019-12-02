package com.toutiao.cases.toutiaocase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toutiao.config.TestConfig;
import com.toutiao.model.toutiaomd.ShareCase;
import com.toutiao.utils.DatabaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
@Slf4j
public class ShareTest {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Test(dependsOnGroups = "loginTrue",dataProvider = "getShareTestData",description = "头条/分享")
    public void getShare(ShareCase shareCase){
        String result = getResult(shareCase);
        log.info("实际结果是：{}",result);
        JSONObject jsonObject = JSON.parseObject(result);
        Assert.assertEquals(shareCase.getExpResult(),jsonObject.get("status"));

    }
    @Test(dependsOnGroups = "loginTrue",dataProvider = "getShareTestData",description = "分享/错误的user_id")
    public void getShare2(ShareCase shareCase){
        String result = getResult(shareCase);
        Assert.assertTrue(result.contains("\"status\":1"));
    }

    @Test(dependsOnGroups = "loginTrue",dataProvider = "getShareTestData",description = "分享/错误的id")
    public void getShare3(ShareCase shareCase){
        String result = getResult(shareCase);
        Assert.assertTrue(result.contains("\"status\":1"));
    }

    private String getResult(ShareCase shareCase) {
        String result = null;
        try {
            String url = TestConfig.shareUrl+"key="+TestConfig.key+"&id="+shareCase.getId()+
                    "&type="+shareCase.getType()+"&user_id="+shareCase.getUser_id();
            HttpGet get = new HttpGet(url);
            HttpResponse response = TestConfig.defaultHttpClient.execute(get);
            result = EntityUtils.toString(response.getEntity(),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @DataProvider(name = "getShareTestData")
    private Object[][] getShareData(Method method) throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();
        Object[][] result = null;

        if (method.getName().equals("getShare")){

             result = new Object[][]{
                    {session.selectOne("shareCase",1)}
            };
        }else if (method.getName().equals("getShare2")){
             result = new Object[][]{
                    {session.selectOne("shareCase",2)}
            };
        }
        else if (method.getName().equals("getShare3")){
            result = new Object[][]{
                    {session.selectOne("shareCase",3)}
            };
        }
        return result;
    }
}
