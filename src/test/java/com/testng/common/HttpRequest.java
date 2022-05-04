package com.testng.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    // get请求不带参数
    public static String httpGet(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            return EntityUtils.toString(entity);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (httpResponse != null){
                httpResponse.close();
            }
            httpClient.close();
        }
        return null;
    }

    // get请求带参数
    public static String httpGet(String url, String param) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        url = url + "?" + param;
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            return EntityUtils.toString(entity);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (httpResponse != null){
                httpResponse.close();
            }
            httpClient.close();
        }
        return null;
    }

    // post请求
    public static JSONObject HttpPost(String url, Map<String, Object> data, Map<String, Object> header) throws IOException{
        // httpclient 客户端对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // post对象
        HttpPost httpPost = new HttpPost(url);
        // 默认http响应设置为null
        CloseableHttpResponse httpResponse = null;
        // 如果传入的头信息不为空，遍历头信息的这个map，然后依次添加到头信息里面
        if (header != null && !header.isEmpty()){
            for (String key:header.keySet()){
                httpPost.addHeader(key, header.get(key).toString());
            }
        }
        // 创建一个json对象，用来存储传参数据
        JSONObject json = new JSONObject();
        // 遍历传入的data对象，依次存入json里面
        if (data != null && !data.isEmpty()) {
            for (String key : data.keySet()) {
                json.put(key, data.get(key).toString());
            }
        }
        // post请求给json对象格式化参数内容
        httpPost.setEntity(new StringEntity(json.toString(), "UTF-8"));
        try {
            // 发送请求，得到实体内容，转化为json对象返回
            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            return JSON.parseObject(EntityUtils.toString(entity));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (httpResponse != null){
                httpResponse.close();
            }
            httpClient.close();
        }
        return null;
    }

    public static Object httpRequest(String method, String url, Object data, Object headers){
//        if (method.toLowerCase().equals("get")){
//
//        }
        return null;
    }

    public static void main(String[] args) throws IOException {
//        String get_response = httpGet("http://139.224.2.208:8020/tool_api/get_sid",
//                                    "uid=86a5ae90-acff-4096-9a2e-9eea047b5461");
//        JSONObject jsonText = JSON.parseObject(get_response);
        String get_response = httpGet("https://www.baidu.com");
        System.out.println(get_response);
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=utf-8");
        Map<String, Object> data = new HashMap<>();
        data.put("phone", "10000000185");
        data.put("password", "111111");
        JSONObject post_response = HttpPost("http://api.kr-cell.net/login-register/login-by-phone", data, header);
        System.out.println(post_response);
    }
}
