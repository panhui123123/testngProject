package com.testng.example;

//import org.junit.Test;
//import org.junit.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Unit test for simple App.
 */
@Epic("测试大类")
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @BeforeTest
    public void beforeClass(){
        System.out.println("类方法开始执行之前执行一遍");
    }

    @AfterTest
    public void afterClass(){
        System.out.println("所有类方法执行完成之后执行一遍");
    }

    @Test
    @Feature("test001测试")
    @Description("这个是test001的测试描述")
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
        Assert.assertNull(null);
//        try {
//            a = a;
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
    }

    @Test(description = "get请求", timeOut = 2000)
    public void testGet() throws IOException {
        // 声明url
        String url = "https://www.baidu.com";
        // 创建get请求实例
        HttpGet httpGetTest1 = new HttpGet(url);
        // 创建httpclient客户端对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //请求执行，获取响应，这里的response是一大坨东西，类似于你用python response = requests.get(url)之后得到的这个response对象
        CloseableHttpResponse response =  httpclient.execute(httpGetTest1);
        // 获取响应实体，这里获取的是一个对象
        HttpEntity entity = response.getEntity();
        // 用EntityUtils工具转化为文本
        String text = EntityUtils.toString(entity);
        System.out.println(text);
        // 获取响应码，类似于python response.status_code
        int code = response.getStatusLine().getStatusCode();
        // 断言，这个写法很像python unittest里面的断言写法
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
        // 创建一个post请求实例
        HttpPost httpPostTest1 = new HttpPost(url);
        // 为post请求添加头信息
        httpPostTest1.addHeader("Content-Type", "application/json;charset=utf-8");
        // 创建参数
        JSONObject json = new JSONObject();
        json.put("phone", "10000000185");
        json.put("password", "111111");
        // 为post请求格式化参数内容并且添加
        httpPostTest1.setEntity(new StringEntity(json.toString(), "UTF-8"));
        // 创建httpclient客户端对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 发送post请求得到response，这里的response是一大坨东西
        CloseableHttpResponse response = httpclient.execute(httpPostTest1);
        // response.getEntity()是响应实体内容
        HttpEntity entity = response.getEntity();
        // 转为为字符串
        String text = EntityUtils.toString(entity);
        // 创建json对象，将响应实体内容的字符串对象序列化为json对象
        JSONObject responseJson = JSON.parseObject(text);
        //
        System.out.println(responseJson.getClass().getName());
        System.out.println(responseJson);
        // 断言
        Assert.assertTrue((Boolean) responseJson.get("result"));
        Assert.assertEquals(responseJson.get("errorCode"), 0);
        Assert.assertEquals(responseJson.get("errorMsg"), "成功");
        Assert.assertEquals(responseJson.getJSONObject("avatarInfo").get("isGif"), false);
        response.close();
        httpclient.close();
    }

    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
        System.out.println(simpleDateFormat.format(date));
        System.out.println("ceshi,ceshi");
        }

}
