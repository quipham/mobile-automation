package com.example.booking.manage;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.example.booking.common.Helper;
import com.example.booking.common.Platform;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ExtentManager {
    private static Logger logger = LogManager.getLogger(ExtentManager.class);
    private static ExtentReports extent;
    private static Map<String, ExtentTest> extentPool = new HashMap<>();
    private static Platform platform;
    private static String reportFileName = Helper.getCurrentTime() + ".html";
    private static String macPath = System.getProperty("user.dir") + "/reports";
    private static String windowsPath = System.getProperty("user.dir") + "\\reports";
    private static String macReportFileLoc = macPath + "/" + reportFileName;
    private static String winReportFileLoc = windowsPath + "\\" + reportFileName;

    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    public static void setTest(ExtentTest test) {
        extentPool.put(Thread.currentThread().getName(), test);
    }

    public static ExtentTest getTest() {
        return extentPool.get(Thread.currentThread().getName());
    }

    static ExtentReports createInstance() {
        platform = getCurrentPlatform();
        String fileName = getReportFileLocation(platform);
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(fileName);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;
    }

    private static String getReportFileLocation(Platform platform) {
        String reportFileLocation = null;
        switch (platform) {
            case MAC:
                reportFileLocation = macReportFileLoc;
                createReportPath(macPath);
                break;
            case WINDOWS:
                reportFileLocation = winReportFileLoc;
                createReportPath(windowsPath);
                break;
            default:
                break;
        }
        return reportFileLocation;
    }

    private static void createReportPath(String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                logger.info("Directory: " + path + " is created!");
            } else {
                logger.info("Failed to create directory: " + path);
            }
        } else {
            logger.info("Directory already exists: " + path);
        }
    }

    private static Platform getCurrentPlatform() {
        if (platform == null) {
            String operationSys = System.getProperty("os.name").toLowerCase();
            if (operationSys.contains("win")) {
                platform = Platform.WINDOWS;
            } else if (operationSys.contains("nix") || operationSys.contains("nux")
                    || operationSys.contains("aix")) {
                platform = Platform.LINUX;
            } else if (operationSys.contains("mac")) {
                platform = Platform.MAC;
            }
        }
        return platform;
    }

    private ExtentManager() {
    }
}
