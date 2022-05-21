package com.testng.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtils {
    private final String excelPath;
    private final String excelSheetName;

    // 初始化方法，传入excel文件名和表格名
    public ExcelUtils(String excelPath, String excelSheetName){
        this.excelPath = excelPath;
        this.excelSheetName = excelSheetName;
    }

    public Object[][] readExcel() {
        FileInputStream fis = null;
        Object[][] datas = null;
        // 项目绝对路径
        String projectPath = System.getProperty("user.dir");
        try {
            //1.读取excel
            fis = new FileInputStream(projectPath + "/src/test/java/com/testng/testdata/" + this.excelPath);
            // 创建workbook对象
            Workbook workbook = WorkbookFactory.create(fis);
            // 获取我们需要操作的表格
            Sheet sheet = workbook.getSheet(this.excelSheetName);
            // 创建一个二维数组，用来存放测试数据
            datas = new Object[sheet.getLastRowNum()][11];
            // 遍历表格数据，忽略表头，从第二行开始
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                // 获取当前行
                Row row = sheet.getRow(i);

                /* caseId如果excel里面是数字是double类型，这里如果用getStringCellValue()会导致cell对象对空,
                 所以用getNumericCellValue(),不过我们还是转化为int存到数组中 */
                Cell caseIdCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                int caseIdCellValue = (int)caseIdCell.getNumericCellValue();

                Cell caseNameCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String caseNameCellValue = caseNameCell.getStringCellValue();

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

                Cell expected1Cell = row.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String expected1CellValue = expected1Cell.getStringCellValue();

                Cell expected2Cell = row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String expected2CellValue = expected2Cell.getStringCellValue();

                Cell responseBodyCell = row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String responseBodyCellValue = responseBodyCell.getStringCellValue();

                Cell resultCell = row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String resultCellValue = resultCell.getStringCellValue();

                Cell relyCell = row.getCell(10, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String relyCellValue = relyCell.getStringCellValue();

                // 二维数组赋值
                datas[i-1][0] = caseIdCellValue;
                datas[i-1][1] = caseNameCellValue;
                datas[i-1][2] = methodCellValue;
                datas[i-1][3] = urlCellValue;
                datas[i-1][4] = headerCellValue;
                datas[i-1][5] = paramCellValue;
                datas[i-1][6] = expected1CellValue;
                datas[i-1][7] = expected2CellValue;
                datas[i-1][8] = responseBodyCell;
                datas[i-1][9] = resultCellValue;
                datas[i-1][10] = relyCellValue;
            }
            return datas;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return datas;
    }

    public void writeExcel(Integer row, Integer column, String stringValue){
        FileInputStream fis = null;
        FileOutputStream fos;
        String projectPath = System.getProperty("user.dir");
        try {
            //1.读取excel
            fis = new FileInputStream(projectPath + "/src/test/java/com/testng/testdata/" + this.excelPath);
            // 创建workbook对象
            Workbook workbook = WorkbookFactory.create(fis);
            // 获取我们需要操作的表格
            Sheet sheet = workbook.getSheet(this.excelSheetName);
            // 获取行
            Cell cell = sheet.getRow(row-1).getCell(column-1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellValue(stringValue);

            fos = new FileOutputStream(projectPath + "/src/test/java/com/testng/testdata/" + this.excelPath);
            workbook.write(fos);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ExcelUtils excelUtils = new ExcelUtils("test_login.xlsx", "test_login");
        // 数组不能直接打印，用Arrays转一下
        System.out.println(Arrays.deepToString(excelUtils.readExcel()));

    }
}