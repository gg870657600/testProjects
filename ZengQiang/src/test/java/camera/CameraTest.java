package camera;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import util.AssertListener;
import util.Assertion;
import util.BaseAppium;
import util.ScreenshotListener;
@Listeners({ScreenshotListener.class})
public class CameraTest {
	public static AndroidDriver androidDriver;
//	public static ElementIsExist element;
	public static String switchCamera = "com.android.camera2:id/switch_camera";
	public static String shutterButton = "com.android.camera2:id/shutter_button";
	public static String cameraVideoSwitchButton = "com.android.camera2:id/camera_video_switch_button";
	public static String settingClassName = "android.widget.ImageButton";
	public static String deleteButton = "//*[@text='删除']";
	public static String enterButton = "//*[@text='确定']";
	public static String photoButton = "//*[@text='图片']";
	public static String videoButton = "//*[@text='视频']";
	public static String selectButton = "//*[contains(@text, '项')]";
	public static String selectButton2 = "//*[@text='选择']";
	public static String allSelectButton = "//*[@text='全选']";
	@Test
	public void openCamera() throws InterruptedException {

    	//根据类名断言,是否跳到主界面(adb shell dumpsys activity | find "mFocusedActivity")
		if(BaseAppium.id(shutterButton).isDisplayed()){
			Assert.assertEquals(1, 1);
		}else{
			Assert.assertEquals(1, 0, "打开相机失败");
		}
    	
    	Thread.sleep(2000);
    }
	//后置摄像头拍照
    @Test(priority = 1,dependsOnMethods={"openCamera"})   //priority执行优先级
    public void rearCamera() throws InterruptedException {
    	BaseAppium.id(shutterButton).click();
    	Thread.sleep(2000);
    	//根据相册数量进行断言，如果有一张图片则拍照成功
    	int expected = 1;
    	int actual = countPhoto();
    	Assert.assertEquals(actual, expected, "后置摄像头拍照失败");
    	Thread.sleep(2000);
    }
    //前置摄像头拍照
    @Test(priority = 2,dependsOnMethods={"openCamera"})
    public void frontCamera() throws InterruptedException {
    	BaseAppium.id(switchCamera).click();
    	Thread.sleep(2000);
    	BaseAppium.id(shutterButton).click();
    	int expected = 2;
    	int actual = countPhoto();
    	Assert.assertEquals(actual, expected, "前置摄像头拍照失败");
    	Thread.sleep(2000);
    }
    //前置摄像头录像
    @Test(priority = 3,dependsOnMethods={"openCamera"})
    public void frontVideo() throws InterruptedException {

    	BaseAppium.id(cameraVideoSwitchButton).click();
    	Thread.sleep(2000);
    	BaseAppium.id(shutterButton).click();
    	Thread.sleep(5000);
    	BaseAppium.id(shutterButton).click();
    	int expected = 1;
    	int actual = countVideo();
    	Assert.assertEquals(actual, expected, "前置摄像头录像失败");
    	Thread.sleep(2000);
    }
    //后置摄像头录像
    @Test(priority = 4,dependsOnMethods={"openCamera"})
    public void rearVideo() throws InterruptedException {

    	BaseAppium.id(switchCamera).click();
    	Thread.sleep(2000);
    	BaseAppium.id(shutterButton).click();
    	Thread.sleep(5000);
    	BaseAppium.id(shutterButton).click();
    	int expected = 2;
    	int actual = countVideo();
    	Assert.assertEquals(actual, expected, "后置摄像头录像失败");
    	Thread.sleep(2000);
    }
    @BeforeTest
    public void beforeTest() throws InterruptedException, IOException {
  	    androidDriver = BaseAppium.init("com.android.camera2", "com.android.camera.CameraLauncher");
  	    ScreenshotListener.driver = androidDriver;
  	    
  	    //查看我的文件中图片和视频数量，如果大于0，删除所有图片和视频
  	    if (countPhoto() > 0 | countVideo() > 0){
  	        //执行用例前删除相册中的图片和视频
  	  	    deletePhotoVideo();
  	    }else{
  	    	System.out.println("没有视频或图片");
  	    }
  	    
    }
    @BeforeMethod
    public void beforeMethod(){
    	

    }
    @AfterMethod
    public void afterMethod(){
    	
    }
    @AfterTest
    public void afterTest() {
	    androidDriver.quit();
    }
    //删除相册中的图片和视频
    public void deletePhotoVideo() throws InterruptedException{
    	//启动相册app
    	Activity activity = new Activity("com.android.gallery3d", ".app.GalleryActivity");
    	androidDriver.startActivity(activity);
    	//长按坐标定位
//    	PointOption tapOption = PointOption.point(118,370);
//    	new TouchAction(androidDriver).longPress(tapOption).release().perform();
  	
    	//全选删除
    	BaseAppium.by(By.className(settingClassName)).click();
    	BaseAppium.xpath(selectButton2).click();
    	BaseAppium.xpath(selectButton).click();
    	BaseAppium.xpath(allSelectButton).click();
    	BaseAppium.by(By.className(settingClassName)).click();
    	BaseAppium.xpath(deleteButton).click();
    	BaseAppium.xpath(enterButton).click();
    	Thread.sleep(2000);
    	//返回
    	BaseAppium.back();
    	System.out.println("删除相册中的图片和视频");
    }
    //我的文件中图片的数量
    public int countPhoto() throws InterruptedException{
    	//启动相册app
    	Activity activity = new Activity("com.android.fileexplorer", ".FileExplorerActivity");
    	androidDriver.startActivity(activity);
    	BaseAppium.xpath(photoButton).click();
    	int count;
    	List photoElList = null;
    	try {
    		photoElList = BaseAppium.by(By.id("com.android.fileexplorer:id/file_list")).findElements(By.className("android.widget.FrameLayout"));
    	}catch (Exception e){
    		
    	}    	
    	if ( photoElList != null){
    		count = photoElList.size();
    	}else{
    		count = 0;
    	}    	
    	System.out.println("图片数量:" + count);
    	BaseAppium.back();
    	Thread.sleep(1000);
    	BaseAppium.back();
    	return count;
    }
  //我的文件中视频的数量
    public int countVideo() throws InterruptedException{
    	//启动相册app
    	Activity activity = new Activity("com.android.fileexplorer", ".FileExplorerActivity");
    	androidDriver.startActivity(activity);
    	BaseAppium.xpath(videoButton).click();
    	int count;
    	List videoElList = null;
    	try {
    		videoElList = BaseAppium.by(By.id("com.android.fileexplorer:id/file_list")).findElements(By.className("android.widget.FrameLayout"));
    	}catch (Exception e){
    		
    	}
    	if ( videoElList != null){
    		count = videoElList.size();
    	}else{
    		count = 0;
    	}
    	System.out.println("视频数量:" + count);
    	BaseAppium.back();
    	Thread.sleep(1000);
    	BaseAppium.back();
    	return count;
    }
}
