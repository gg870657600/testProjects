package util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import calendar.CalendarTest;
/**
 * testNG测试失败自动截图
 * 参数：无
 * 使用方式：在脚本类前面加入一个注解   @Listeners({ScreenshotListener.class})
 **/
public class ScreenshotListener extends TestListenerAdapter{
	public static AndroidDriver driver;
	@Override
    public void onTestFailure(ITestResult tr){
//        AppiumDriver driver = CalendarTest.getDriver();
        File location = new File("screenshots1");
        String screenShotName = location.getAbsolutePath()+File.separator+tr.getMethod().getMethodName()+".png";
        File screenShot = driver.getScreenshotAs(OutputType.FILE);
        try{
            FileUtils.copyFile(screenShot, new File(screenShotName));    
        }
        catch(IOException e){
            e.printStackTrace();
        }
//        Reporter.log("<a href=" + screenShotName + " target=_blank>Failed Screen Shot</a>", true);
        Reporter.log("<失败截图路径 = " + screenShotName + " />", true);
    }
}
