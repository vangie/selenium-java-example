package com.aliyun.fc.selenium;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.StreamRequestHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ChromeDemo implements StreamRequestHandler {


    public void handleRequest(InputStream inputStream,
                              OutputStream outputStream,
                              Context context) throws IOException {

        System.setProperty("webdriver.chrome.driver", "/code/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.setBinary("/code/headless-chromium");
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://ide.fc.aliyun.com");

        outputStream.write(("Page title is: " + driver.getTitle() + "\n").getBytes());

        driver.quit();

    }

}
