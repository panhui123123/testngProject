package com.testng.utils;

public class AssertionUtils {
    private final Object[] testData;
    private Object excelUtils;

    public AssertionUtils(Object[] testData, Object excelUtils){
        this.testData = testData;
        this.excelUtils = excelUtils;
    }

    public Object sendRequest() throws Exception {
        try{
            Object response = HttpRequestUtils.httpRequest(this.testData[0].toString(), this.testData[1].toString(),
                    this.testData[2].toString(), this.testData[3].toString());
            return response;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {

        }
        return null;
    }
}
