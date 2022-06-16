package com.testng.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlUtils {
    private final String url;
    private final String user;
    private final String password;

    public MysqlUtils(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public List<Object> getSqlResult(String sql){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(this.url, this.user, this.password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            List<Object> resultList = new ArrayList<>();
            while (resultSet.next()){
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    list.add(resultSet.getString(i));
                }
                resultList.add(list);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (resultSet !=null){
                    resultSet.close();
                }
                if (statement !=null ){
                    statement.close();
                }
                if (connection != null){
                    connection.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        MysqlUtils mysqlUtils = new MysqlUtils("jdbc:mysql://rds7r40uo8u5s5xy0y9p0public.mysql.rds.aliyuncs.com",
                "timing_backend", "timing_backend_gyag2s");
        List<Object> list;
        list = mysqlUtils.getSqlResult("select * from timing.t_user where phone in (\"10000000177\", \"10000000178\")");
        System.out.println(list);
//        Connection connection = null;
//        Statement statement = null;
//        ResultSet resultSet = null;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://rds7r40uo8u5s5xy0y9p0public.mysql.rds.aliyuncs.com",
//                    "timing_backend", "timing_backend_gyag2s");
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery("select * from timing.t_user where phone in (\"10000000185\", \"10000000186\")");
//            List<Object> resultList = new ArrayList<>();
//            while (resultSet.next()){
//                List<String> list = new ArrayList<>();
//                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
//                    list.add(resultSet.getString(i));
//                }
//                resultList.add(list);
//            }
//            System.out.println(resultList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            try {
//                if (resultSet !=null){
//                    resultSet.close();
//                }
//                if (statement !=null ){
//                    statement.close();
//                }
//                if (connection != null){
//                    connection.close();
//                }
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }
}
