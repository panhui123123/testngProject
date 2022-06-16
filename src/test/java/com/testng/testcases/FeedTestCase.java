package com.testng.testcases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.testng.utils.AssertionUtils;
import com.testng.utils.ExcelUtils;
import com.testng.utils.HttpRequestUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

public class FeedTestCase {
    @BeforeClass
    public Object[] getUserInfo() throws Exception {
        try {
            Object userInfo = HttpRequestUtils.httpRequest("post", "http://api.kr-cell.net/login-register/login-by-phone",
                    "{\"Content-Type\": \"application/json;charset=utf-8\"}",
                    "{\"phone\":\"10000000185\", \"password\":\"111111\"}");
            JSONObject jsonObject = JSON.parseObject(String.valueOf(userInfo));
            int userID = (int) jsonObject.get("userID");
            String userKey = jsonObject.get("userKey").toString();
            return new Object[]{userID, userKey};
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Test(dataProvider = "datas")
    public void testFeed(Object[] testdata) throws Exception {
        int userID = (int) getUserInfo()[0];
        String userKey = getUserInfo()[1].toString();
        String param = (String) testdata[5];
        System.out.println(param);
        if (param.contains("${userID}")){
            param = param.replace("${userID}", String.valueOf(userID));
        }
        if (param.contains("${userKey}")){
            param = param.replace("${userKey}", "\"" + userKey + "\"");
        }
        testdata[5] = param;
        AssertionUtils assertionUtils = new AssertionUtils(testdata, "test_feed.xlsx", "test_feed");
        assertionUtils.sendRequest();
        assertionUtils.assertResult();
    }

    @DataProvider(name = "datas")
    public Object[][] datas(){
        ExcelUtils excelUtils = new ExcelUtils("test_feed.xlsx", "test_feed");
        return excelUtils.readExcel();
    }

}
