package gallery;

import java.io.IOException;
import java.net.MalformedURLException;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import util.BaseAppium;
import util.ScreenshotListener;
import util.Toast;
@Listeners({ScreenshotListener.class})
public class GalleryTest {
	public static AndroidDriver androidDriver;
	Toast toast;
    @Test
    public void f() throws InterruptedException {
    	toast.assertToast("相册数量");
    	androidDriver.findElementByXPath("//*[@text='照片']").click();
    	Thread.sleep(2000);
    	TouchAction touchAction = new TouchAction(androidDriver);
    	PointOption tapOption = PointOption.point(118,370);
    	touchAction.tap(tapOption).release().perform();
    	Thread.sleep(2000);
    	
    }
    @BeforeTest
    public void beforeTest() throws IOException, InterruptedException {
    	androidDriver = BaseAppium.init("com.android.gallery3d", ".app.GalleryActivity");
    	ScreenshotListener.driver = androidDriver;
    }

    @AfterTest
    public void afterTest() {
    	androidDriver.quit();
    }

}
