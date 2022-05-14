package com.testng.testcases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.testng.utils.ExcelUtils;
import com.testng.utils.HttpRequestUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginCase {
    // 注解表示参数化为datas，超时时间为3000
    @Test(dataProvider = "datas", timeOut = 3000, alwaysRun = true)
    public void testLogin(String method, String url, String header, String params) throws Exception {
        // 发送请求
        Object response = HttpRequestUtils.httpRequest(method, url, header, params);
        System.out.println(response);
        if (response instanceof String){
            Assert.assertTrue(((String) response).contains("www"));
        }
        else {
            JSONObject responseJson = JSON.parseObject(String.valueOf(response));
            Assert.assertEquals(responseJson.get("result"), true);
        }
    }

    @DataProvider(name = "datas")
    public Object[][] datas(){
        ExcelUtils excelUtils = new ExcelUtils("test_login.xlsx", "test_login");
        return excelUtils.readExcel();
    }
    
}