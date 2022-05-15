package com.testng.testcases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.testng.utils.ExcelUtils;
import com.testng.utils.HttpRequestUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DemoCase {
    @Test(dataProvider = "datas", timeOut = 3000, alwaysRun = true)
    public void testDemo(Object[] testdata) throws Exception {
        // 发送请求
        Object response = HttpRequestUtils.httpRequest(testdata[2].toString(), testdata[3].toString(),
                testdata[4].toString(), testdata[5].toString());
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
        ExcelUtils excelUtils = new ExcelUtils("test_demo.xlsx", "test_demo");
        return excelUtils.readExcel();
    }
}
