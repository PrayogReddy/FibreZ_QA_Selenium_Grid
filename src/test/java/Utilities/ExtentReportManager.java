package Utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

    private static final String OUTPUT_FOLDER = System.getProperty("user.dir") + "/ExtentReports/";
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static List<ExtentTest> testList = new ArrayList<>();
    private static String reportPath;
    
    private static WebDriver driver;
    
    public static void setDriver(WebDriver driverInstance) {
        driver = driverInstance;
    }
    
    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite started!");

        String suiteName = context.getSuite().getName();
        String testName = context.getName();  // This will give the name of the test

        // If suiteName or testName is "Default suite" or "Default test", update them
        if (suiteName.equalsIgnoreCase("Default suite") || testName.equalsIgnoreCase("Default test")) {
            suiteName = context.getAllTestMethods()[0].getTestClass().getRealClass().getSimpleName();
            testName = context.getAllTestMethods()[0].getMethodName();
        }

        String browserName = context.getCurrentXmlTest().getParameter("browser");
        browserName = browserName.substring(0, 1).toUpperCase() + browserName.substring(1).toLowerCase();
        String timestamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        String reportFileName = OUTPUT_FOLDER + suiteName.replace(" ", "_") + "_" + browserName + "_Report_" + timestamp + ".html";
        
        Path path = Paths.get(OUTPUT_FOLDER);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        extent = new ExtentReports();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFileName);
        reportPath = reportFileName;

        sparkReporter.config().setReportName(suiteName.replace(" ", "_") + "_" + browserName + "_Report");
        sparkReporter.config().setDocumentTitle(suiteName.replace(" ", "_") + "_Report_" + timestamp);
        sparkReporter.config().setTheme(Theme.STANDARD);
        
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("System", "Windows");
        extent.setSystemInfo("Name", "Prayog");
        extent.setSystemInfo("Build#", "1.1");
        extent.setSystemInfo("Team", "QA");
        extent.setSystemInfo("Customer Name", "FibreZ");
        extent.setSystemInfo("Test Type", "Smoke Testing");
        extent.setSystemInfo("Environment", "Selenium Grid");
    }



    @Override
    public void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        System.out.println(methodName + " started!");
        ExtentTest test = extent.createTest(methodName, result.getMethod().getDescription());
        
        extentTest.set(test);
        extentTest.get().getModel().setStartTime(getTime(result.getStartMillis()));
        testList.add(extentTest.get());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " passed!");
        ExtentTest test = extentTest.get();
        test.pass("Test passed");
        test.getModel().setEndTime(getTime(result.getEndMillis()));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " failed!");
        ExtentTest test = extentTest.get();
        test.fail(result.getThrowable());
        test.getModel().setEndTime(getTime(result.getEndMillis()));
        
        if (driver != null) {
            String screenshotPath = takeScreenshot(result.getMethod().getMethodName());
            test.addScreenCaptureFromPath(screenshotPath);
        }
    }
    
    private String takeScreenshot(String methodName) {
        String screenshotPath = OUTPUT_FOLDER + "screenshots/";
        File screenshotDir = new File(screenshotPath);
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }
        
        String filePath = screenshotPath + methodName + "_" + new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date()) + ".png";
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " skipped!");
        ExtentTest test = extentTest.get();
        test.skip(result.getThrowable());
        test.getModel().setEndTime(getTime(result.getEndMillis()));
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Suite is ending!");
        extent.flush();
        
        File extentReport = new File(reportPath);
        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }    
         // Get the ExtentTest instance for current thread
    public static ExtentTest getExtentTest() {
        return extentTest.get();
    }
}
