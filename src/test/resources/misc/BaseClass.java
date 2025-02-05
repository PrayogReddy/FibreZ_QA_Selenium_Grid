package Utilities;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseClass {

    public WebDriver startBrowser(String browserName) throws MalformedURLException {
        WebDriver driver;
        URL gridUrl = URI.create("http://localhost:4444/wd/hub").toURL();

        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            driver = new RemoteWebDriver(gridUrl, options);
        } else if (browserName.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            driver = new RemoteWebDriver(gridUrl, options);
        } else if (browserName.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            driver = new RemoteWebDriver(gridUrl, options);
        } else {
            throw new IllegalArgumentException("Invalid browser name: " + browserName);
        }

        return driver;
    }
}
