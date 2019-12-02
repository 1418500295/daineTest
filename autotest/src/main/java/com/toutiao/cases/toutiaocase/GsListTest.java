package com.toutiao.cases.toutiaocase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toutiao.config.TestConfig;
import com.toutiao.model.toutiaomd.GsListCase;
import com.toutiao.utils.DatabaseUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
@Log4j2
public class GsListTest {

    private SqlSession session = DatabaseUtil.getSqlsession();

    public GsListTest() throws IOException {

    }

    @Test(dependsOnGroups = "loginTrue",dataProvider = "getGsListData",description = "头条/高手榜列表")
    public void getGsList(GsListCase gsListCase) throws IOException {
        String result = getResult(gsListCase);
        log.info("实际结果："+result);
        JSONObject jsonObject = JSON.parseObject(result);
        Assert.assertEquals(gsListCase.getExpResult(),jsonObject.get("status"));
    }

    @Test(dependsOnGroups = "loginTrue",dataProvider = "getGsListData2",description = "高手榜/错误的period")
    public void getGsList2(GsListCase gsListCase) throws IOException {
        String result = getResult(gsListCase);
        log.info("实际结果："+result);
        JSONObject jsonObject = JSON.parseObject(result);
        Assert.assertEquals(gsListCase.getExpResult(),jsonObject.get("status"));

    }

    private String getResult(GsListCase gsListCase) {

        String result = null;
        try {
            String url = TestConfig.gsListUrl+"key="+TestConfig.key+"&user_id="+gsListCase.getUser_id()
                    +"&p="+gsListCase.getP()+"&type="+gsListCase.getType()+"&period="+gsListCase.getPeriod();
            HttpGet get = new HttpGet(url);
            System.out.println(url);
            HttpResponse response = TestConfig.defaultHttpClient.execute(get);
            result = EntityUtils.toString(response.getEntity(),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    @DataProvider(name = "getGsListData")
    private Object[][] getGsData() throws IOException {

        Object[][] result = new Object[][]{
                {session.selectOne("gsListCase","1")}
        };
        return result;
    }

    @DataProvider(name = "getGsListData2")
    private  Object[][] getGsData2(){
        Object[][] result = {
                {session.selectOne("gsListCase","2")}
        };
        return result;

    }
}
