package sos;

import java.io.IOException;
import java.net.MalformedURLException;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import util.BaseAppium;
import util.ScreenshotListener;

public class SosTest {
	AndroidDriver androidDriver;
	String close = "//*[@text = '关闭']";
	String open = "//*[@text = '开启']";
	String switchButton = "android.widget.Switch";
	String enterButtonId = "android:id/button1";
	String openLocation = "//*[@text = '请打开定位进行设置']";
	String sosButtonId = "com.gh.sos:id/sos_button";
	String beforeSend = "//*[contains(@text, '点击按钮')]";
	String sending = "//*[contains(@text, '发送中')]";
	String sendSuccess = "//*[contains(@text, '信息发送成功')]";
	String longitudeId = "com.gh.sos:id/edt_longitude";
	String latitudeId = "com.gh.sos:id/edt_latitude";
	String altitudeId = "com.gh.sos:id/edt_altitude";
	//自动定位发送sos
    @Test
    public void locationSendSos() {
    	System.out.println("sos初始化中。。。");
    	int expected = 1;
    	int actual = 0;
    	//发送中，等待信关站确认
    	WebElement el1 = BaseAppium.webDriverWait(By.xpath(beforeSend), 30);
    	
    	if(el1 != null){
    		System.out.println("sos初始化完成");
    		BaseAppium.id(sosButtonId).click();
    		//点击sos按钮后出现"发送中，等待信关站确认"弹窗
        	if(BaseAppium.elementIsExist(BaseAppium.xpath(sending))){
        		System.out.println("sos发送中...");
        		BaseAppium.id(sosButtonId).click();
        	}else{
        		//手动输入经纬度信息
        		BaseAppium.id(longitudeId).sendKeys("66");
        		BaseAppium.id(latitudeId).sendKeys("66");
        		BaseAppium.id(altitudeId).sendKeys("66");
        		BaseAppium.id(enterButtonId).click();
        		System.out.println("手动发送sos中...");
        	}
        	//收到信息发送成功弹窗则成功
    		try{
        		BaseAppium.webDriverWait(By.xpath(sendSuccess), 60);
        		BaseAppium.id(enterButtonId).click();
        		System.out.println("发送sos成功");
        		actual = 1;
        	}catch(Exception e){
        		System.out.println("发送sos失败");
        	}
    	}else{
    		System.out.println("sos一直初始化中。。。");
    	}
    	Assert.assertEquals(actual, expected, "sos发送失败");
    	
    }
    //手动定位发送sos
    @Test
    public void handLocationSendSos() {
    }
    @BeforeTest
    public void beforeTest() throws IOException, InterruptedException {
    	
    	androidDriver = BaseAppium.init("com.gh.sos", ".MainActivity");
    	ScreenshotListener.driver = androidDriver;
    	
    	//打开位置信息
//    	System.out.println(androidDriver.location().getAltitude());
//    	Activity activity = new Activity("com.android.settings",".SubSettings");
//    	androidDriver.startActivity(activity);
    	try{
    		if(BaseAppium.xpath(openLocation).isDisplayed()){
    			BaseAppium.id(enterButtonId).click();
        		BaseAppium.className(switchButton).click();
        		BaseAppium.id(enterButtonId).click();
        		BaseAppium.back();
        		System.out.println("定位已开启");
    	    }   		
    	}catch(Exception e){
    		
    	}
    	
    	
    }

    @AfterTest
    public void afterTest() {
    }

}