package fr.repierre.plazer.server.navigator;

import fr.repierre.plazer.server.navigator.services.Service;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Navigator {

    private static Navigator instance;
    private final ChromeDriver driver;
    private Service currentService;

    public Navigator() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("user-data-dir=C:\\Users\\" + System.getenv("USERNAME") + "\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 1");
        //options.setHeadless(true);

        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        this.driver = new ChromeDriver(options);
        instance = this;
    }

    public static Navigator getInstance() {
        return instance;
    }

    public ChromeDriver getDriver() {
        return driver;
    }

    public Service getCurrentService() {
        return currentService;
    }

    public void setCurrentService(Service currentService) {
        this.currentService = currentService;
        this.currentService.init();

        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState === 'complete'"));

        this.currentService.loaded();

        if (this.currentService.needLogin()) this.currentService.login();
        this.currentService.ready = true;
        //this.currentService.search("lalaland");
    }

    public ExpectedCondition<Boolean> waitUntil() {
        return webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
    }

    public WebElement waitForElement(String s) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(driver -> driver.findElement(By.xpath(s)));
        return driver.findElementByXPath(s);
    }
}
