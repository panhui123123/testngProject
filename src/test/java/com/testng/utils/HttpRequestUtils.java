package com.testng.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class HttpRequestUtils {
    // get请求不带参数
    public static String httpGet(String url) throws Exception {
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
    public static JSONObject httpGet(String url, String params) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // params是一个符合json格式的字符串，这里转化为map
        Map<String, Object> paramMaps = JSON.parseObject(params);
        URIBuilder uriBuilder = new URIBuilder(url);
        for (Map.Entry<String, Object> entry: paramMaps.entrySet()){
            uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
        }
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
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

    // post请求
    public static Object httpPost(String url, String header, String data) throws IOException{
        // httpclient 客户端对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // post对象
        HttpPost httpPost = new HttpPost(url);
        // 默认http响应设置为null
        CloseableHttpResponse httpResponse = null;
        // 将请求头字符串转化为json字符串，然后再转化为map
        Map<String, Object> headerMaps = JSON.parseObject(header);
        // 如果传入的头信息不为空，遍历头信息的这个map，然后依次添加到头信息里面
        if ( headerMaps!= null && !headerMaps.isEmpty()){
            for (Map.Entry<String, Object> entry: headerMaps.entrySet()){
                httpPost.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        // 将data字符串转为json对象
        JSONObject dataJson = JSON.parseObject(data);
        // 遍历传入的data对象，依次存入json里面
        if (dataJson != null && !dataJson.isEmpty()) {
            for (String key : dataJson.keySet()) {
                dataJson.put(key, dataJson.get(key).toString());
            }
        }
        // post请求给json对象格式化参数内容
        httpPost.setEntity(new StringEntity(dataJson.toString(), "UTF-8"));
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

    public static Object httpRequest(String method, String url, String header, String data) throws Exception {
        if (method.equalsIgnoreCase("get")){
            if (data == null || data.isEmpty()){
                return httpGet(url);
            }
            else {
                return httpGet(url, data);
            }
        }

        if (method.equalsIgnoreCase("post")){
            return httpPost(url, header, data);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Object get_response = httpRequest("get", "https://www.baidu.com", null, null);
        System.out.println(get_response);


        String getParams = "{\"uid\":\"86a5ae90-acff-4096-9a2e-9eea047b5461\"}";
        Object get_response2 = httpRequest("get", "http://139.224.2.208:8020/tool_api/get_sid", null, getParams);
        System.out.println(get_response2);



        String header = "{\"Content-Type\": \"application/json;charset=utf-8\"}";
        String data = "{\"phone\":\"10000000185\", \"password\":\"111111\"}";
        JSONObject post_response = (JSONObject) httpRequest("post", "http://api.kr-cell.net/login-register/login-by-phone", header, data);
        System.out.println(post_response);
    }
}
