package setting;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.events.EventFiringWebDriverFactory;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import util.AssertListener;
import util.Assertion;
import util.BaseAppium;
import util.ElementListener;
import util.ScreenshotListener;

@Listeners({ScreenshotListener.class})
public class SettingTest {
	public static AndroidDriver androidDriver;
	public static String closeText = "//*[@text = '关闭']";
	public static String switchButton = "android.widget.Switch";
	public static String enterButtonId = "android:id/button1";
	public static String longitudeId = "com.android.settings:id/longitude_info";
	public static String latitudeId = "com.android.settings:id/latitude_info";
	public static String altitudeId = "com.android.settings:id/altitude_info";
	BaseAppium baseAppium;
//	  @Test
//	  public void f() throws InterruptedException {
//		  androidDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
//		  Thread.sleep(2000);
//		  //向下滑动
//		  Swipe swipe = new Swipe(androidDriver);
////		  swipe.slideUp();
//		  
//		  baseAppium.slideUp();
//		  Assert.assertEquals(1, 1, "断言成功");
//		  Thread.sleep(1000);
//	  }
	  //位置信息
	  @Test
	  public void location() throws InterruptedException {
		  
//		  androidDriver.findElementByXPath("//*[@text='Views']").click();
		  androidDriver.findElementByAndroidUIAutomator(BaseAppium.scrollTo("位置信息", "TEXT")).click();
		  //如果未开启定位则开启
		  try{
			  if(BaseAppium.elementIsExist(BaseAppium.xpath(closeText))){
				  BaseAppium.className(switchButton).click();
				  BaseAppium.id(enterButtonId).click();
			  }
		  }catch(Exception e){
			  
		  }
		  Thread.sleep(5000);
		  String longitude = BaseAppium.id(longitudeId).getText();
		  System.out.println("经度定位:" + longitude);
		  //循环判断经纬度数据是否有变化，有变化则定位成功
		  int i = 0;
		  while(i < 120){
			  String longitude2 = BaseAppium.id(longitudeId).getText();
			  System.out.println("定位中....");
			  if(!longitude.equals(longitude2)){
				  System.out.println("定位成功:" + longitude2);
				  Assert.assertEquals(1, 1, "定位成功");
				  break;
			  }
			  //超过120还未定位则退出循环
			  Thread.sleep(1000);
			  i++;
			  if(i == 120){
				  System.out.println("超过120s还未定位");
				  Assert.assertEquals(1, 2, "定位超时");
				  break;
			  }
		  }
		  Thread.sleep(2000);
	  }
	  
	
	  @BeforeTest
	  public void beforeTest() throws IOException, InterruptedException {
	  	    androidDriver = BaseAppium.init("com.android.settings", ".Settings"); 

	  	    ScreenshotListener.driver = androidDriver;
	  	    baseAppium = new BaseAppium(androidDriver);
//	  	    androidDriver.switchTo().alert().accept();
	  }
	
	  @AfterTest
	  public void afterTest() {
		  //当测试用例运行完毕销毁驱动
	      androidDriver.quit();
	  }
	
	  @BeforeSuite
	  public void beforeSuite() {
	  }
	
	  @AfterSuite
	  public void afterSuite() {
	  }

}
