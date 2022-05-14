package com.testng.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.*;


public class ExcelUtils {
    private final String excelPath;
    private final String excelSheetName;

    // 初始化方法，传入excel文件名和表格名
    public ExcelUtils(String excelPath, String excelSheetName){
        this.excelPath = excelPath;
        this.excelSheetName = excelSheetName;
    }

    public Object[][] readExcel() {
        FileInputStream files = null;
        Object[][] datas = null;
        // 项目绝对路径
        String projectPath = System.getProperty("user.dir");
        try {
            //1.读取excel
            files = new FileInputStream(projectPath + "/src/test/java/com/testng/testdata/" + this.excelPath);
            // 创建workbook对象
            Workbook workbook = WorkbookFactory.create(files);
            // 获取我们需要操作的表格
            Sheet sheet = workbook.getSheet(this.excelSheetName);
            // 创建一个二维数组，用来存放测试数据
            datas = new Object[sheet.getLastRowNum()][4];
            // 遍历表格数据，忽略表头，从第二行开始
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                // 获取当前行
                Row row = sheet.getRow(i);
                // 获取当前行的单元格
                Cell methodCell = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                // 获取当前单元格的value
                String methodCellValue = methodCell.getStringCellValue();

                Cell urlCell = row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String urlCellValue = urlCell.getStringCellValue();

                Cell headerCell = row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String headerCellValue = headerCell.getStringCellValue();

                Cell paramCell = row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String paramCellValue = paramCell.getStringCellValue();

                // 二维数组赋值
                datas[i-1][0] = methodCellValue;
                datas[i-1][1] = urlCellValue;
                datas[i-1][2] = headerCellValue;
                datas[i-1][3] = paramCellValue;
            }
            return datas;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (files != null) {
                    files.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return datas;
    }

    public static void main(String[] args) {
        ExcelUtils excelUtils = new ExcelUtils("test_login.xlsx", "test_login");
        // 数组不能直接打印，用Arrays转一下
        System.out.println(Arrays.deepToString(excelUtils.readExcel()));
    }
}