package simpleMode;

import java.awt.List;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import util.AssertListener;
import util.Assertion;
import util.BaseAppium;
import util.ScreenshotListener;
@Listeners({ScreenshotListener.class})
public class SimpleModeTest {
	public static AndroidDriver androidDriver;
	String dialerButton = "//*[@text = '电话']";
	String smsButton = "//*[@text = '短消息']";
	String contactsButton = "//*[@text = '联系人']";
	String outButton = "//*[@text = '退出']";
	String button = "com.gh.simplelauncher:id/icon";
	String dialerActivity = ".DialtactsActivity";
	String smsActivity = ".ui.ConversationList";
	String contactsActivity = ".activities.PeopleActivity";

    @Test
    public void openSimpleMode() {
    	if(
			BaseAppium.xpath(dialerButton).isDisplayed() &&
			BaseAppium.xpath(smsButton).isDisplayed() &&
			BaseAppium.xpath(contactsButton).isDisplayed() &&
			BaseAppium.xpath(outButton).isDisplayed() 
			){
    		System.out.println("进入极简模式成功");
    		Assert.assertEquals(1, 1);
    	}else{
    		System.out.println("进入极简模式失败");
    		Assert.assertEquals(0, 1);
    	}
    }
    
    @Test(invocationCount = 1)
    public void simpleModeOpenApp() throws InterruptedException {
    	ArrayList<WebElement> list = (ArrayList) BaseAppium.elList(By.id(button));
    	System.out.println("list:" + list.size());
    	
    	list.get(0).click();
    	Thread.sleep(2000);
    	String activity1 = androidDriver.currentActivity();
    	System.out.println("activity1:" + activity1);
    	BaseAppium.back();
    	list.get(1).click();
    	Thread.sleep(2000);
    	String activity2 = androidDriver.currentActivity();
    	System.out.println("activity2:" + activity2);
    	BaseAppium.back();
    	list.get(2).click();
    	Thread.sleep(2000);
    	String activity3 = androidDriver.currentActivity();
    	System.out.println("activity3:" + activity3);
    	BaseAppium.back();
    	if(
			activity1.equals(dialerActivity) &&
			activity2.equals(smsActivity) &&
			activity3.equals(contactsActivity) 
			){
    		Assert.assertEquals(1, 1);
    	}else{
    		Assert.assertEquals(0, 1);
    	}
    }
    @BeforeTest
    public void beforeTest() throws IOException, InterruptedException {
    	androidDriver = BaseAppium.init("com.gh.simplelauncher", ".LauncherActivity");
    	ScreenshotListener.driver = androidDriver;
    }

    @AfterTest
    public void afterTest() {
    	androidDriver.quit();
    }

}
