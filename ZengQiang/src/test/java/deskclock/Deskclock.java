package deskclock;


import java.io.IOException;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import io.appium.java_client.touch.offset.PointOption;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import util.BaseAppium;
import util.ScreenshotListener;

public class Deskclock {
	AndroidDriver androidDriver;
	String moreButton = "//*[@content-desc='更多选项']";
	String dateAndTimeButton = "//*[@text='日期和时间']";
	String automaticTimeButton = "//*[@text='自动确定日期和时间']";
	String setTimeButton = "//*[@text='设置时间']";
	String morning = "//*[@text='上午']";
	String nine = "//*[@text='9']";
	String fiftyNine = "//*[@text='59']";
	String enterButtonId = "android:id/button1";
	String alarmClockTab = "//*[@text='闹钟']";
	//定位前面的兄弟节点
	String switchButton = "//android.widget.TextView[@content-desc='上午10:00']/following-sibling::android.widget.Switch";
	//闹钟响起特有的元素
	String alarmClockId = "com.android.deskclock:id/glow_pad_view";
	String deskClock = "//*[@text='时钟']";
	public static boolean automaticDateAndTime;
	

	WebElement element1 = null;
	WebElement element2 = null;
	//闹钟
    @Test
    public void alarmClock() {
    	BaseAppium.xpath(alarmClockTab).click();
    	BaseAppium.xpath(switchButton).click();
    	if(BaseAppium.webDriverWait(By.id(alarmClockId), 60) != null){
    		System.out.println("currentActivity:" + androidDriver.currentActivity() + ";闹钟响了");
    		//向右滑动关闭闹钟
    		new TouchAction(androidDriver).press(PointOption.point(372, 924))
          	.moveTo(PointOption.point(644 , 924)).release().perform();
    		Assert.assertEquals(1, 1);
    	}else{
    		System.out.println("闹钟没响哦");
    		Assert.assertEquals(1, 0, "闹钟未响");
    	}
    	
    }
    @BeforeTest
    public void beforeTest() throws IOException, InterruptedException {
    	androidDriver = BaseAppium.init("com.android.deskclock", ".DeskClock");
    	ScreenshotListener.driver = androidDriver;

    	BaseAppium.xpath(moreButton).click();
    	BaseAppium.xpath(dateAndTimeButton).click();
    	//自动确定日期和时间是否打开
    	if(BaseAppium.elList(By.id("android:id/checkbox")).get(0).getAttribute("checked").equals("true")){
    		BaseAppium.xpath(automaticTimeButton).click();
    		automaticDateAndTime = true;
    	}else{
    		automaticDateAndTime = false;
    	}    	
    	BaseAppium.xpath(setTimeButton).click();
    	//设置时间
    	scoll2(element2);
    	scoll(element1);   	

    	//选择上午   	
    	BaseAppium.xpath(morning).click();
    	BaseAppium.id(enterButtonId).click();
    	BaseAppium.back();
    }

    @AfterTest
    public void afterTest() {
    	//关闭测试闹钟
//    	BaseAppium.xpath(alarmClockTab).click();
    	BaseAppium.xpath(switchButton).click();
    	//自动确定日期和时间设置恢复
    	if(automaticDateAndTime){
    		BaseAppium.xpath(deskClock).click();
    		BaseAppium.xpath(moreButton).click();
        	BaseAppium.xpath(dateAndTimeButton).click();
        	BaseAppium.xpath(automaticTimeButton).click();
        	BaseAppium.back();
    	}
    	androidDriver.quit();
    }
    //循环点击，选择时间
    public void scoll(WebElement el) throws InterruptedException{
    	while(true){
    		try{
    			el = BaseAppium.xpath(nine);
    		}catch(Exception e){
    			
    		}
    		if(el != null && el.getAttribute("focusable").equals("true")){
    			break;
    		}
    		new TouchAction(androidDriver).press(PointOption.point(364, 517)).release().perform();
    		//需要加个延时，资源没释放
    		Thread.sleep(500);
    	}
    }
    public void scoll2(WebElement el) throws InterruptedException{
    	while(true){
    		try{
    			el = BaseAppium.xpath(fiftyNine);
    		}catch(Exception e){
    			
    		}
    		if(el != null && el.getAttribute("focusable").equals("true")){
    			break;
    		}
    		new TouchAction(androidDriver).press(PointOption.point(531, 508)).release().perform();
    		Thread.sleep(500);
    	}
    }

}
