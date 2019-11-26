package com.toutiao.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

import java.io.IOException;
import java.io.Reader;

public class DatabaseUtil {
    public static SqlSession getSqlsession() throws IOException {
        //获取配置的资源文件
        Reader reader = Resources.getResourceAsReader("databaseConfig.xml");
        //得到SqlSessionFactory,使用类加载器加载xml
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        //得到session对象，用于执行sql语句
        SqlSession session = sqlSessionFactory.openSession();
        return  session;
        }


    }


