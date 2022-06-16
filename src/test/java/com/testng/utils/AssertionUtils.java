package com.testng.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;

import java.util.Arrays;

public class AssertionUtils {
    private final Object[] testData;
    private final String excelPath;
    private final String excelSheetName;

    // 构造方法，需要传入测试数据的数组，excel的文件名路径、excel的表格名
    public AssertionUtils(Object[] testData, String excelPath, String excelSheetName){
        this.testData = testData;
        this.excelPath = excelPath;
        this.excelSheetName = excelSheetName;
    }

    public Object sendRequest() throws Exception {
        try{
            Object response = HttpRequestUtils.httpRequest(this.testData[2].toString(), this.testData[3].toString(),
                    this.testData[4].toString(), this.testData[5].toString());
            ExcelUtils excelUtils = new ExcelUtils(this.excelPath, this.excelSheetName);
            String responseText = String.valueOf(response);
            if (responseText.length() > 30000){
                responseText = responseText.substring(0, 30000);
            }
            excelUtils.writeExcel((int)testData[0]+1, 9, responseText);
            return response;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void assertResult() {
        String result = "FAILED";
        try {
            Object response = this.sendRequest();
            if (response instanceof String) {
                Assert.assertTrue(((String) response).contains(this.testData[6].toString().substring(4)));
                Assert.assertTrue(((String) response).contains(this.testData[7].toString().substring(4)));
                result = "PASS";
            } else {
                JSONObject responseJson = JSON.parseObject(String.valueOf(response));
                System.out.println(this.testData[6]);
                Object[] object1 = this.testData[6].toString().split("==");
                Object[] object2 = this.testData[7].toString().split("==");
                Assert.assertEquals(responseJson.get(object1[0].toString()).toString(), object1[1]);
                Assert.assertEquals(responseJson.get(object2[0].toString()).toString(), object2[1]);
                result = "PASS";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ExcelUtils excelUtils = new ExcelUtils(this.excelPath, this.excelSheetName);
            excelUtils.writeExcel((int) testData[0] + 1, 10, result);
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 1};
        System.out.println(a.getClass().getName());
    }
}
