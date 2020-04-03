package dataService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.util.Map;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.asserts.SoftAssert;

import util.AssertListener;
import util.Assertion;
import util.BaseAppium;
import util.ScreenshotListener;
@Listeners({ScreenshotListener.class})
public class DataServiceTest {
	AndroidDriver androidDriver;
	String upstream = "//*[@text = '上行速率最大']";
	String down = "//*[@text = '下行速率最大']";
	String six = "//*[@text = '6']";
	String moreButton = "//*[@content-desc='更多选项']";
	String saveButton = "//*[@text = '保存']";
	String dataSwitchButton = "//*[@text = '移动数据']";
	String noNetwork = "//*[contains(@text , '无网络')]";
	String enterButtonId = "android:id/button1";
	String dataServiceOpenSuccess = "//*[@text = '数据开关打开成功']";
	String editUrlBoxId = "com.android.browser:id/url";
	String webpageText = "//*[@content-desc='请点击您想要浏览的内容 Heading']";
    @Test
    public void openDataService() throws IOException, InterruptedException {
    	//判断数据业务是否打开,只能判断开关是否打开，不能确定数据是通的
    	System.out.println( "数据业务是否开启:" + androidDriver.getConnection().isDataEnabled());   	
    	//如果已经打开则先关闭后再设置上下行速率
    	if(androidDriver.getConnection().isDataEnabled()){
//    		androidDriver.setConnection(new ConnectionState(0b000));
    		androidDriver.openNotifications();
    		BaseAppium.xpath(dataSwitchButton).click();
    		BaseAppium.id(enterButtonId).click();
    		BaseAppium.back();
    	}
    	//设置上下行
    	BaseAppium.xpath(upstream).click();
    	BaseAppium.xpath(six).click();
    	BaseAppium.xpath(down).click();
    	BaseAppium.xpath(six).click();
    	BaseAppium.xpath(moreButton).click();
    	BaseAppium.xpath(saveButton).click();
    	
    	//开启下拉状态栏移动数据
        androidDriver.openNotifications();
    	//打开数据业务，见ConnectionState源代码，参数0b代表二进制，100代表4
//		androidDriver.setConnection(new ConnectionState(0b100));
        BaseAppium.xpath(dataSwitchButton).click();
        //软断言，不影响断言失败后代码执行
        SoftAssert assertion = new SoftAssert();
        if(BaseAppium.xpath(noNetwork) != null){        	
        	System.out.println("没有网络");
        	assertion.assertEquals(1, 0, "没有网络");
        	Thread.sleep(1000);
        	BaseAppium.id(enterButtonId).click();
        }
        else if(BaseAppium.webDriverWait(By.xpath(dataServiceOpenSuccess), 90) != null){        		
        		assertion.assertEquals(1, 1);        		
        		BaseAppium.id(enterButtonId).click();
        }
        else{
    		Assert.assertEquals(1, 0, "数据业务打开失败");
    	}
        
        BaseAppium.back();
        assertion.assertAll();
    }
    //打开网页
    @Test(dependsOnMethods={"openDataService"})
    public void openWebpage() throws IOException, InterruptedException{
//    	androidDriver.startActivity(new Activity("com.android.browser",".BrowserActivity"));
//    	BaseAppium.id(editUrlBoxId).sendKeys("192.168.0.163");
//    	androidDriver.pressKey(new KeyEvent(AndroidKey.SEARCH));
    	//清除浏览器数据和缓存
    	BaseAppium.run("adb shell pm clear com.android.browser");
    	//打开特定网页
    	BaseAppium.run("adb shell am start -a android.intent.action.VIEW -d http://192.168.0.163");
    	if(BaseAppium.webDriverWait(By.xpath(webpageText), 30) != null){
    		System.out.println("打开网页成功");
    		Assert.assertEquals(1, 1);
    	}else{
    		Assert.assertEquals(1, 0, "打开网页失败");
    	}
    }
    
    @BeforeTest
    public void beforeTest() throws IOException, InterruptedException {
    	androidDriver = BaseAppium.init("com.leadcore.assistant", ".MATSPsSettings");
    	ScreenshotListener.driver = androidDriver;
    }

    @AfterTest
    public void afterTest() {
    	//关闭数据业务
    	androidDriver.openNotifications();
    	BaseAppium.xpath(dataSwitchButton).click();
    	BaseAppium.id(enterButtonId).click();
    	androidDriver.quit();
    }

}
