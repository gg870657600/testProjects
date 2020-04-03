package contacts;

import java.io.IOException;
import java.net.MalformedURLException;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import util.AssertListener;
import util.Assertion;
import util.BaseAppium;
import util.ScreenshotListener;
@Listeners({ScreenshotListener.class})
public class ContactsTest {
	public static AndroidDriver androidDriver;
	public static String addContactId = "com.android.contacts:id/contact_list_add_contact";
	public static String contactNameEdit = "//*[@text = '姓名']";
	public static String contactNumberEdit = "//*[@text = '号码']";
	public static String saveContactId = "com.android.contacts:id/menu_done";
	public static String editContactButtonId = "com.android.contacts:id/menu_edit_contact_detail";
	public static String testContactName1 = "//*[@text = '张三']";
	public static String testContactName2 = "//*[@text = '张三2']";
	public static String moreClassName = "android.widget.ImageButton";
	public static String deleteButton = "//*[@text = '删除']";
	public static String enterId = "android:id/button1";
	@Test
    public void addContacts() throws InterruptedException {
    	BaseAppium.id(addContactId).click();
    	BaseAppium.xpath(contactNameEdit).sendKeys("张三");
    	BaseAppium.xpath(contactNumberEdit).sendKeys("17400000016");
    	BaseAppium.id(saveContactId).click();
    	Thread.sleep(2000);
    	BaseAppium.back();
    	Thread.sleep(1000);
    	String expected = "张三";
    	String actual = "";
    	try{
    		actual =  BaseAppium.xpath(testContactName1).getText();
    	}catch(Exception e){
    		System.out.println("没有找到联系人:"+ expected);
    	}
    	Assert.assertEquals(actual, expected,"添加联系人失败");
	    Thread.sleep(2000);
    }
    @Test(priority = 1)
    public void editContacts() throws InterruptedException {
    	BaseAppium.xpath(testContactName1).click();
    	BaseAppium.id(editContactButtonId).click();
    	BaseAppium.xpath(testContactName1).clear();
    	BaseAppium.xpath(contactNameEdit).sendKeys("张三2");
    	BaseAppium.id(saveContactId).click();
    	
    	Thread.sleep(2000);
    	BaseAppium.back();
    	Thread.sleep(1000);
    	String expected = "张三2";
    	String actual = "";
    	try{
    		actual =  BaseAppium.xpath(testContactName2).getText();
    	}catch(Exception e){
    		System.out.println("没有找到联系人:"+ expected);
    	}
    	Assert.assertEquals(actual, expected,"编辑联系人失败");
		Thread.sleep(2000);
    }
    @Test(priority = 2)
    public void deleteContacts() throws InterruptedException {
    	BaseAppium.xpath(testContactName2).click();
    	BaseAppium.className(moreClassName).click();
    	BaseAppium.xpath(deleteButton).click();
    	BaseAppium.id(enterId).click();
    	Thread.sleep(2000);
    	String expected = "";
    	String actual = "";
    	try{
    		actual =  BaseAppium.xpath(testContactName2).getText();
    	}catch(Exception e){
    		System.out.println("没有找到联系人:"+ expected);
    	}
    	Assert.assertEquals(actual, expected,"删除联系人失败");
		Thread.sleep(2000);
    }
    
    @BeforeTest
    public void beforeTest() throws IOException, InterruptedException {
    	
    	androidDriver = BaseAppium.init("com.android.contacts", ".activities.PeopleActivity");
    	ScreenshotListener.driver = androidDriver;
    	try{
    		BaseAppium.keepClickElement(BaseAppium.id("android:id/app_ops_checkbox"),BaseAppium.id("android:id/button1"));
    	}catch (Exception e) {
//    		System.out.println("找不到元素");
    	}
    }

    @AfterTest
    public void afterTest() {
    	
    	androidDriver.quit();
    }

}
