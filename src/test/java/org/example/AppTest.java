package org.example;

//import org.junit.Test;
//import org.junit.Assert;

import com.alibaba.fastjson.JSONObject;
import netscape.javascript.JSObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @BeforeClass
    public void beforeClass(){
        System.out.println("类方法开始执行之前执行一遍");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("所有类方法执行完成之后执行一遍");
    }

    @Test
    public void test001() {
        Assert.assertTrue(true);
        System.out.println("111");
    }

    @Test
    public void test002(){
        String string = "hello";
        Assert.assertEquals(string, "hello");
        System.out.println("222");
    }

    @Test
    public void test003(){
        String string = "world";
        Assert.assertNotEquals(string, "word");
        System.out.println("333");
    }

    @Test
    public void test004(){
        Map<String, Integer> map = new HashMap<>();
        map.put("panhui", 18);
        int age = map.get("panhui");
        Assert.assertEquals(age,18);
        System.out.println("333");
    }

    @Test
    public void test005(){
        Object ob = null;
        Assert.assertNull(ob);
//        try {
//            a = a;
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
    }

    @Test
    public void testGet() throws IOException {
        String url = "https://www.baidu.com";
        // 创建httpclient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建HttpGet
        HttpGet httpGetTest1 = new HttpGet(url);
        // 请求执行，获取响应
        CloseableHttpResponse response =  httpclient.execute(httpGetTest1);
        // 获取响应实体，这里获取的是一个对象
        HttpEntity entity = response.getEntity();
        // 用EntityUtils工具转化为文本
        String text = EntityUtils.toString(entity);
        System.out.println(text);
        // 获取响应码
        int code = response.getStatusLine().getStatusCode();
        Assert.assertEquals(code, 200);
        Assert.assertTrue(text.contains("www"));
        response.close();
        httpclient.close();
    }

    @Test
    public void testPost() throws IOException{
        // post请求，json提交
        // 声明要请求的url
        String url = "http://api.kr-cell.net/login-register/login-by-phone";
        // 创建httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建一个post请求实例
        HttpPost httpPostTest1 = new HttpPost(url);
        // 为请求添加头信息
        httpPostTest1.addHeader("Content-Type", "application/json;charset=utf-8");
        // 创建参数
        JSONObject json = new JSONObject();
        json.put("phone", "10000000185");
        json.put("password", "111111");
        // 格式化参数内容并且添加
        httpPostTest1.setEntity(new StringEntity(json.toString(), "UTF-8"));
        // 发送请求
        CloseableHttpResponse response = httpclient.execute(httpPostTest1);
        HttpEntity entity = response.getEntity();
        String text = EntityUtils.toString(entity);
        System.out.println(text);
        response.close();
        httpclient.close();
    }


}
