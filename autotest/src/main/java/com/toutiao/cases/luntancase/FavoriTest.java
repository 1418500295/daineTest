package com.toutiao.cases.luntancase;

import com.toutiao.config.TestConfig;
import com.toutiao.model.luntanmd.FavoriCase;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Log4j2
public class FavoriTest {
    @Test(dependsOnGroups = "loginTrue",dataProvider = "getData",description = "论坛/收藏")
    public void getFavori(FavoriCase favoriCase) throws IOException {

        String result = getResult(favoriCase);
        log.info("实际结果："+result);
        Assert.assertTrue(result.contains("\"status\":1"));

    }
    @Test(dependsOnGroups = "loginTrue",dataProvider = "getData2",description = "收藏/错误的id")
    public void getFavori2(FavoriCase favoriCase) throws IOException {
        String result = getResult(favoriCase);
        log.info("实际结果："+result);
        Assert.assertFalse(result.contains("\"status\":1"));
    }


    private String getResult(FavoriCase favoriCase) throws IOException {
        String result = null;
        try {
            String url = TestConfig.favoriUrl+"key="+TestConfig.key+"&user_id="+favoriCase.getUser_id();
            HttpPost post = new HttpPost(url);
            List<NameValuePair>  param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("id",favoriCase.getId()));
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

    @DataProvider(name = "getData")
    private Object[][] getFavoriData() throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();
        Object[][] result = new Object[][]{
                {session.selectOne("favoriCase",1)}
        };
        return result;

    }
    @DataProvider(name = "getData2")
    private Object[][] getFavoriData2() throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();
        Object[][] result = new Object[][]{
                {session.selectOne("favoriCase",2)}
        };
        return result;

    }
}
