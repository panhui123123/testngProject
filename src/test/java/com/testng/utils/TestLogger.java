package com.testng.utils;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestLogger {
    private final Logger logger;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
    private final String logFileName = this.simpleDateFormat.format(new Date()) + ".txt";
    private final String logPath = System.getProperty("user.dir") + "/src/test/java/com/testng/logs/" + this.logFileName;

    public TestLogger(Logger logger) {
        this.logger = logger;
    }

    public void writeLogMessage(String logLevel, String message) throws IOException {
        //    private static final Logger logger = LoggerFactory.getLogger(TestLogger.class);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try{
            File file = new File(this.logPath);
            if (!file.exists()){
                file.createNewFile();
            }
            fileWriter = new FileWriter(this.logPath, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(this.simpleDateFormat.format(new Date())).append("]")
              .append(": ")
              .append(this.logger)
              .append(": ")
              .append(logLevel)
              .append(": ")
              .append(message)
              .append("\n");
            bufferedWriter.write(sb.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (bufferedWriter != null){
                    bufferedWriter.close();
                }
                if (fileWriter != null){
                    fileWriter.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Logger logger1 = LoggerFactory.getLogger(TestLogger.class);
        TestLogger testLogger = new TestLogger(logger1);
        testLogger.writeLogMessage("debug", "debug信息");
        Thread.sleep(3000);
        testLogger.writeLogMessage("info", "info信息");
    }
}
