package mms;

import java.io.IOException;
import java.net.MalformedURLException;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import util.BaseAppium;
import util.ScreenshotListener;

@Listeners({ScreenshotListener.class})
public class MmsTest {
	public static AndroidDriver androidDriver;
	public static String newMmsButtonId = "com.android.mms:id/action_compose_new";
	public static String recipientId = "com.android.mms:id/recipients_editor";
	public static String inputBoxId = "com.android.mms:id/embedded_text_editor";
	//信息发送失败有一个感叹号标识
	public static String smsSendFailId = "com.android.mms:id/delivered_indicator";
	public static String smsSendButtonId = "com.android.mms:id/send_button_sms";
	public static String smsContent = "//*[@text = '测试短信']";
	public static String smsSendState = "//*[contains(@text, '发送中')]";
	public static String testNumber = "17400000016";
	public static String deleteContent = "//*[@text = '删除会话']";
	public static String enterButton = "//*[@text = '确定']";
    @Test
    public void sendSms() throws InterruptedException {
    	BaseAppium.id(newMmsButtonId).click();
    	BaseAppium.id(recipientId).sendKeys(testNumber);
    	BaseAppium.id(inputBoxId).sendKeys("测试短信");    	
    	BaseAppium.id(smsSendButtonId).click();  	
    	
    	int expected = 2;
    	int actual = 1;
    	
    	WebElement webElement = null;
    	WebElement webElement2 = null;
    	
    	String error = "";
		try{
			//显式等待
//			WebDriverWait wait = new WebDriverWait(androidDriver,90);
//			WebElement webElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(smsSendFailId)));
			webElement = BaseAppium.webDriverWait(By.id(smsSendFailId), 60);
			webElement2 = BaseAppium.xpath(smsSendState);
			
			if(webElement != null){
				error = "发送短信出现失败标识";
				System.out.println("发送短信出现失败标识");
			}else if(webElement2 != null){
				error = "短信一直显示发送中。。。";
				System.out.println("短信一直显示发送中。。。");
			}else{
				System.out.println("发送短信成功");
				actual = 2;
			}
		}catch(Exception e){
    		
    	}
		//发送短信给本机,如果成功本机会显示两条内容
//		actual = androidDriver.findElements(By.xpath(smsContent)).size();
//		System.out.println("短信计数:" + actual);
		Assert.assertEquals(actual, expected, error);
		Thread.sleep(2000);
    }
    @BeforeTest
    public void beforeTest() throws IOException, InterruptedException {
    	androidDriver = BaseAppium.init("com.android.mms", ".ui.ConversationList");
    	BaseAppium.androidDriver = androidDriver;
    	ScreenshotListener.driver = androidDriver;
    	//先删除测试号码发送的短信
    	try{
    		if(BaseAppium.xpath("//*[@text = " + testNumber + "]").isDisplayed()){
        		BaseAppium.longPress(BaseAppium.xpath("//*[@text = " + testNumber + "]"), 1);
        		BaseAppium.xpath(deleteContent).click();
        		BaseAppium.xpath(enterButton).click();
        	}
    	}catch(Exception e){
    		
    	}
    	
    	
    	
    }

    @AfterTest
    public void afterTest() {
    	androidDriver.quit();
    }

}
