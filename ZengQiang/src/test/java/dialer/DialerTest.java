package dialer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import util.BaseAppium;
import util.ScreenshotListener;
@Listeners({ScreenshotListener.class})
public class DialerTest {
	public static AndroidDriver androidDriver;
//	public static String oneId = "com.android.dialer:id/one";
//	public static String twoId = "com.android.dialer:id/two";
//	public static String threeId = "com.android.dialer:id/three";
//	public static String fourId = "com.android.dialer:id/four";
//	public static String fiveId = "com.android.dialer:id/five";
//	public static String sixId = "com.android.dialer:id/six";
//	public static String sevenId = "com.android.dialer:id/seven";
//	public static String eightId = "com.android.dialer:id/eight";
//	public static String nineId = "com.android.dialer:id/nine";
//	public static String zeroId = "com.android.dialer:id/zero";
//	public static String starId = "com.android.dialer:id/star";
//	public static String poundId = "com.android.dialer:id/pound";
//	public static String addToContactId = "com.android.dialer:id/add_contact_text";
	public static String tabRecord = "//*[@text = '记录']";
	public static String tabContact = "//*[@text = '联系人']";
	public static String deleteButtonId = "com.android.dialer:id/deleteButton";
	public static String inputBoxId = "com.android.dialer:id/digits";
	public static String dialButtonId = "com.android.dialer:id/dialButton";
	public static String addToContactId = "com.android.dialer:id/add_contact";
	public static String createNewContactId = "com.android.contacts:id/create_new_contact";
	public static String contactNameEdit = "//*[@text = '姓名']";
	public static String saveContactId = "com.android.contacts:id/menu_done";
	public static String testContactName = "//*[@text = '测试联系人']";
	public static String noNetwork = "//*[@text = '无法访问移动网络']";
	public static String enterButtonId = "android:id/button1";
	public static String callStateDialing = "//*[@text = '正在拨号']";
	public static String callStateEndCall = "//*[@text = '已结束通话']";
	public static String callTimeId = "com.android.dialer:id/elapsedTime";
	public static String endCallButtonId = "com.android.dialer:id/endButton";
	public static String dialButton2Id = "com.android.contacts:id/first_action_button";
	public static String deleteButton = "//*[@text = '删除']";
	public static String callRecordItemId = "com.android.dialer:id/call_log_list_item";
	@Test
    public void addNumToContact() throws InterruptedException {
    	BaseAppium.inputNumember("1234");
    	BaseAppium.id(addToContactId).click();
    	BaseAppium.id(createNewContactId).click();
    	BaseAppium.xpath(contactNameEdit).sendKeys("测试联系人");
    	BaseAppium.id(saveContactId).click();
    	
    	String expected = "测试联系人";
    	String actual = null;
    	try{
    		actual = BaseAppium.xpath(testContactName).getText();
    		System.out.println("actual:" + actual);
    	}catch(Exception e){
    		
    	}
    	Assert.assertEquals(actual, expected, "拨号界面添加联系人失败");
    	BaseAppium.back();
    	Thread.sleep(2000);
    }
	//invocationCount 即表示该用例循环执行多少次
    @Test(enabled = true)
    public void handDialing() throws InterruptedException {
    	BaseAppium.xpath(tabRecord).click();
    	try{
    		BaseAppium.id(inputBoxId).clear();
    	}catch(Exception e){
    		
    	}
    	BaseAppium.inputNumember("13281170087");
    	BaseAppium.id(dialButtonId).click();
    	
    	//没网络
  	    if(BaseAppium.elementIsExist(BaseAppium.xpath(noNetwork))){
  		    BaseAppium.id(enterButtonId).click();
  		    System.out.println("没有网络");
  		    Assert.assertEquals(1, 0, "没有网络");
  	    }
  	    //接通成功
  	    else{
  		    if(BaseAppium.webDriverWait(By.id(callTimeId), 60) != null){
  				System.out.println("通话成功");
  				Thread.sleep(5000);
  				BaseAppium.id(endCallButtonId).click();
  				Assert.assertEquals(1, 1);
  		    }else{
  			    Assert.assertEquals(1, 0, "超时未接通或中途被挂断");
  		    }
  	    }
    	
    }
    @Test
    public void contactDialing() throws InterruptedException {
    	BaseAppium.xpath(tabContact).click();
    	BaseAppium.xpath(testContactName).click();
    	BaseAppium.id(dialButton2Id).click();
    	//没网络
  	    if(BaseAppium.elementIsExist(BaseAppium.xpath(noNetwork))){
  		    BaseAppium.id(enterButtonId).click();
  		    System.out.println("没有网络");
  		    BaseAppium.back();
  		    Assert.assertEquals(1, 0, "没有网络");
  	    }
  	    //接通成功
  	    else{
  		    if(BaseAppium.webDriverWait(By.id(callTimeId), 60) != null){
  				System.out.println("通话成功");
  				Thread.sleep(5000);
  				BaseAppium.id(endCallButtonId).click();
  				BaseAppium.back();
  				Assert.assertEquals(1, 1);
  		    }else{
  		    	BaseAppium.back();
  			    Assert.assertEquals(1, 0, "超时未接通或中途被挂断");
  		    }
  	    }
  	    
    }
    @BeforeTest
    public void beforeTest() throws IOException, InterruptedException {
    	androidDriver = BaseAppium.init("com.android.dialer", ".DialtactsActivity");
    	ScreenshotListener.driver = androidDriver;
    	//删除通话记录
    	BaseAppium.deleteList(BaseAppium.elList(By.id(callRecordItemId)));
    }

    @AfterTest
    public void afterTest() {
    	//删除测试联系人
    	BaseAppium.back();
    	BaseAppium.xpath(tabContact).click();
    	BaseAppium.longPress(BaseAppium.xpath(testContactName), 2);
    	BaseAppium.xpath(deleteButton).click();
    	BaseAppium.id(enterButtonId).click();
    	androidDriver.quit();
    }

}
