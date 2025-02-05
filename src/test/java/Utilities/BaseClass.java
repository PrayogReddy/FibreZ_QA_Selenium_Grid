package Utilities;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;

public class BaseClass extends ExcelUtils {
    public WebDriver driver;
    public static final String URL = "http://10.80.3.31/fibrez/";
    
    public static final String testDataFilePath = "D:\\Prayog\\T_connect\\Automation\\QA_Smoke_TestData.xlsx";
    public static final String testDataSheetName1 = "Book Now Creation";  
    public static final String testDataSheetName2 = "Login Types";
    
    // This method will handle browser initialization dynamically
    @Parameters("browser")
    @BeforeMethod
    public void setup(String browser) throws MalformedURLException {
        driver = startBrowser(browser);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        
        driver.get(URL);
    }
    
    // This method selects the browser and returns the driver instance
    public WebDriver startBrowser(String browserName) throws MalformedURLException {
        URL gridUrl = URI.create("http://localhost:4444/wd/hub").toURL();
        
        switch (browserName.toLowerCase()) {
            case "chrome":
                driver = new RemoteWebDriver(gridUrl, new ChromeOptions());
                break;
            case "firefox":
                driver = new RemoteWebDriver(gridUrl, new FirefoxOptions());
                break;            
            default:
                throw new IllegalArgumentException("Invalid browser name: " + browserName);
        }
        return driver;
    }

    // Login utility methods
    public void login(int rowNum) throws Throwable {
        String username = ExcelUtils.getCellData(testDataFilePath, testDataSheetName2, rowNum, 1);
        String password = ExcelUtils.getCellData(testDataFilePath, testDataSheetName2, rowNum, 2);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//p[@class='hidden text-base text-[#666666] mr-[5px] md:inline'])[1]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("robotCheck")).click();
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
        
        wait.until(ExpectedConditions.urlContains("fibrez"));
    }

    // Specific login roles
    public void superAdminLogin() throws Throwable { login(1); }
    public void adminLogin() throws Throwable { login(2); }
    public void msoLogin() throws Throwable { login(3); }
    public void lcoLogin() throws Throwable { login(4); }
    public void customerLogin() throws Throwable { login(7); }

    // Logout method
    public void Logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='text-sm cursor-pointer']"))).click();
    }

    // Close the browser after the test
    @AfterMethod
    public void closeApp() {
        if (driver != null) {
            driver.quit();
        }
    }
}
