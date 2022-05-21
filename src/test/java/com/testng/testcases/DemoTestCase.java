package com.testng.testcases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.testng.utils.AssertionUtils;
import com.testng.utils.ExcelUtils;
import com.testng.utils.HttpRequestUtils;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Epic("这是demo测试大类")
@Owner("作者：潘大帅哥")
public class DemoTestCase {

    @Feature("这是批量测试网页请求")
    @Description("这是我的描述")
    @Test(dataProvider = "datas", timeOut = 3000, alwaysRun = true)
    // 这里我会在测试方法参数里面传一个数组，数组就是通过excel读取，放在dataProvider里面，从数组里面提取相应参数
    public void testDemo(Object[] testdata) throws Exception {
        // 发送请求
        Allure.story("ceshi");
        AssertionUtils assertionUtils = new AssertionUtils(testdata, "test_demo.xlsx", "test_demo");
        Allure.step("发送请求");
        assertionUtils.sendRequest();
        Allure.step("断言");
        assertionUtils.assertResult();

//        if (response instanceof String){
//            Assert.assertTrue(((String) response).contains("www"));
//        }
//        else {
//            JSONObject responseJson = JSON.parseObject(String.valueOf(response));
//            Assert.assertEquals(responseJson.get("result"), true);
//        }
    }

    @DataProvider(name = "datas")
    public Object[][] datas(){
        ExcelUtils excelUtils = new ExcelUtils("test_demo.xlsx", "test_demo");
        return excelUtils.readExcel();
    }
}
