package util;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

//Appium1.6.3开始支持识别toast，且是基于uiautomator2
public class Toast {
	public static AndroidDriver androidDriver;
	public Toast(AndroidDriver androidDriver){
		this.androidDriver = androidDriver;
	}
	public void assertToast(String message){
        final WebDriverWait wait = new WebDriverWait(androidDriver, 10);
        Assert.assertNotNull(wait.until(
              ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@text,'"+message+"')]"))));
    }
}
