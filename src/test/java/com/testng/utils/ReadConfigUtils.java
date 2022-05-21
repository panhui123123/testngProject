package com.testng.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class ReadConfigUtils {
    private final String configName;

    public ReadConfigUtils(String configName){
        this.configName = configName;
    }

    public Object readConfigValue(String key) throws Exception {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("src\\test\\java\\com\\testng\\config\\" + this.configName);
        properties.load(fis);
        return properties.getProperty(key);
    }

    public static void main(String[] args) throws Exception {
        ReadConfigUtils readConfigUtils = new ReadConfigUtils("config.properties");
        System.out.println(readConfigUtils.readConfigValue("password"));
    }
}
