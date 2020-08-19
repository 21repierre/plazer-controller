package fr.repierre.plazer.server.navigator.services;

import fr.repierre.plazer.server.navigator.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Deezer extends Service {

    public Deezer() {
        super("Deezer");
    }

    @Override
    public void init() {
        driver.get("https://deezer.com/fr");
    }

    @Override
    public void loaded() {
        // Cookie accept
        try {
            WebElement cookie = driver.findElementByXPath("//a[@class='cookie-btn']/span");
            cookie.click();
        } catch (NoSuchElementException ignored) {
        }
    }

    @Override
    public void login() {
        WebElement mail = navigator.waitForElement("//input[@id='login_mail']");
        mail.sendKeys("user");
        WebElement password = driver.findElementByXPath("//input[@id='login_password']");
        password.sendKeys("password");
        WebElement loginButton = driver.findElementByXPath("//button[@id='login_form_submit']/span");
        loginButton.click();
    }

    @Override
    public boolean needLogin() {
        boolean login = false;
        try {
            //WebElement loginTo = navigator.waitForElement("//a[@id='topbar-login-button']/span");
            WebElement loginTo = driver.findElementByXPath("//a[@id='topbar-login-button']/span");
            login = true;
            loginTo.click();
        } catch (NoSuchElementException ignored) {
        }

        return login;
    }

    @Override
    public void play(boolean status) {
        if (!ready) return;
        WebElement parent = navigator.waitForElement("//button[@class='svg-icon-group-btn is-highlight']");
        WebElement statusSvg = parent.findElement(By.tagName("svg"));
        boolean currentPlay = true;
        if (statusSvg.getAttribute("class").contains("play")) currentPlay = false;
        if (currentPlay != status) {
            parent.click();
        }
    }

    @Override
    public boolean playing() {
        WebElement parent = navigator.waitForElement("//button[@class='svg-icon-group-btn is-highlight']");
        WebElement statusSvg = parent.findElement(By.tagName("svg"));
        return !statusSvg.getAttribute("class").contains("play");
    }

    @Override
    public void setSong(String identifier, int id) {
        if (!ready) return;
        WebElement searchBar = driver.findElementsByXPath("//input[@id='topbar-search']").get(0);
        searchBar.sendKeys(Keys.CONTROL + "a");
        searchBar.sendKeys(Keys.BACK_SPACE);
        searchBar.sendKeys(identifier);
        searchBar.submit();

        WebElement songParent = Navigator.getInstance().waitForElement("//div[@data-key='" + id + "']");
        WebElement link = songParent.findElement(By.tagName("a"));
        link.click();
    }

    @Override
    public String search(String s) {
        try {
            URLConnection searchUrl = new URL("https://api.deezer.com/search?q=" + URLEncoder.encode(s, StandardCharsets.UTF_8)).openConnection();
            searchUrl.setRequestProperty("Accept-Charset", Charset.defaultCharset().name());
            InputStream searchStream = searchUrl.getInputStream();

            try (Scanner scanner = new Scanner(searchStream)) {
                return scanner.useDelimiter("\\A").next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String getTitle() {
        if (!ready) return "";
        WebElement element = driver.findElementsByXPath("//div[@class='marquee-content']/a").get(0);
        return element.getText();
    }

    @Override
    public String getAuthor() {
        if (!ready) return "";
        WebElement element = driver.findElementsByXPath("//div[@class='marquee-content']/a").get(1);
        return element.getText();
    }

    @Override
    public int getTime() {
        if (!ready) return 0;
        WebElement element = driver.findElementByClassName("slider-counter-current");
        String timeText = element.getText();
        return timeText.equals("") ? 0 :strToTime(timeText);
    }

    @Override
    public int getDuration() {
        if (!ready) return 0;
        WebElement element = driver.findElementByClassName("slider-counter-max");
        String timeText = element.getText();
        return timeText.equals("") ? 0 :strToTime(timeText);
    }
}
