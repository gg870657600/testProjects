package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.events.EventFiringWebDriverFactory;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class BaseAppium {
	public static AndroidDriver androidDriver;
	public static String enterButtonId = "android:id/button1";
	public static String deleteButton = "//*[@text = '删除']";
	
	public BaseAppium(AndroidDriver androidDriver){
		this.androidDriver = androidDriver;
	}
	
	 /**
     * 初始化appium
     * 参数：包名，Activity名
	 * @throws InterruptedException 
	 * @throws IOException 
     */
	public static AndroidDriver init(String appPackage, String appActivity) throws IOException, InterruptedException{
		//获得设备名称deviceName
		String deviceName = run("adb get-serialno");
    	DesiredCapabilities desiredCapabilities=new DesiredCapabilities();
    	desiredCapabilities.setCapability("automationName","Appium");
    	desiredCapabilities.setCapability("platformName","Android"); //设置ios,android平台
    	desiredCapabilities.setCapability("deviceName",deviceName);//adb devices获得测试机name
    	desiredCapabilities.setCapability("platformVersion","4.4.4"); //设置测试手机android型号
    	desiredCapabilities.setCapability("appPackage", appPackage);//测试包名
    	desiredCapabilities.setCapability("appActivity",appActivity);//测试activity
    	desiredCapabilities.setCapability("noReset", "false");//启动app时清除原有数据

    	//输入框无法输入中文，增加以下两行
    	desiredCapabilities.setCapability("unicodeKeyboard", true);
    	desiredCapabilities.setCapability("resetKeyboard", true);
    	androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
    	//全局设置等待时间，隐式等待
    	androidDriver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    	
    	//控件监听
  	    androidDriver = EventFiringWebDriverFactory.getEventFiringWebDriver(androidDriver, new ElementListener(), new WebDriverEventListener());
//  	  EventFiringWebDriver e_driver = new EventFiringWebDriver(androidDriver);
//  	    WebEventListener eventListener = new WebEventListener();
//  	  e_driver.register(eventListener);
	  	try{
	  		androidDriver.findElement(By.id("android:id/button1"));
	  	}catch (Exception e) {
	  		
	  	}
  	    
    	return androidDriver;
	}
	
	//by对象是否存在
	//调用
	//public void xXX() {
	//	if(byElementIsExist(By.id("com.xxx.xxxxx:id/tv_bottom_left")) == true) {
	//		assert true;
	//	}else {
	//		assert false:" 元素不存在 ";
	//	}
	//}
	public static boolean elementIsExist(WebElement element) {
		if(element != null){
			return true;
		}else{
			return false;
		}
	}
	
	//by定位元素
    public static WebElement by(By by){
    	WebElement element = androidDriver.findElement(by);
    	return element;
    }
	//id定位元素
    public static WebElement id(String elementId){
    	WebElement element = null;
    	try{
    		element = androidDriver.findElementById(elementId);
    	}catch (Exception e) {

    	}
    	return element;
    }
    //className定位元素
    public static WebElement className(String elementClassName){
    	WebElement element = null;
    	try{
    		element = androidDriver.findElementByClassName(elementClassName);
    	}catch (Exception e) {

    	}
    	return element;
    }
    //xpath定位元素
    public static WebElement xpath(String el){
    	WebElement element = null;
    	try{
    		element = androidDriver.findElementByXPath(el);
    	}catch (Exception e) {

    	}
    	return element;
    }
    //获取元素集合
    public static List<WebElement> elList(By by){
    	List elementList = null;
    	try{
    		elementList = androidDriver.findElements(by);
    	}catch (Exception e) {

    	}    	
    	return elementList;
    }
    
    //模拟返回按键
    public static void back(){
    	androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
    }
    
  //向上滑动屏幕
  	public static void slideUp() {
  		int width = androidDriver.manage().window().getSize().width;;
  		int height = androidDriver.manage().window().getSize().height;
  		System.out.println("width:++++++"+width);
  		new TouchAction(androidDriver).press(PointOption.point(width / 2, height * 3 / 4)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(800))).
  			moveTo(PointOption.point(width / 2, height / 10)).release().perform();
//  		TouchAction touchAction = new TouchAction(androidDriver);
//  		PointOption startPointOption = PointOption.point(400,600);
//  		PointOption endPointOption = PointOption.point(400,400);
//  		Duration duration = Duration.ofMillis(800);
//  		WaitOptions waitOptions = WaitOptions.waitOptions(duration);
//  		touchAction.press(startPointOption).waitAction(waitOptions).moveTo(endPointOption).release();
//  		touchAction.perform();
//  		System.out.println("width:++++++over");
  	}

      //向下滑动屏幕
      public static void slideDown() {
      	int width = androidDriver.manage().window().getSize().width;
      	int height = androidDriver.manage().window().getSize().height;
  	    new TouchAction(androidDriver).press(PointOption.point(width / 2, height / 10))
              .moveTo(PointOption.point(width / 2, height * 3 / 4)).release().perform();
  	  }

      //向左滑动屏幕
      public static void slideLeft() {
          //获取手机屏幕的宽度
      	int width = androidDriver.manage().window().getSize().width;
      	//获取手机屏幕的高度
      	int height = androidDriver.manage().window().getSize().height;
      	new TouchAction(androidDriver).press(PointOption.point(width * 3 / 4, height / 2))
          	.moveTo(PointOption.point(width / 10, height / 2)).release().perform().perform();
      }

      //向右滑动屏幕
      public static void slideRight() {
          int width = androidDriver.manage().window().getSize().width;
          int height = androidDriver.manage().window().getSize().height;
          new TouchAction(androidDriver).press(PointOption.point(width / 10, height / 2))
          	.moveTo(PointOption.point(width * 3 / 4 , height / 2)).release().perform();
      }
      
      // 滑动定位元素
      // name,id,AccessibilityId method
      public static String scrollTo(String content, String type)
      {
          String uiautomatorStr = null;
          
          if (type == "TEXT")
          {
              uiautomatorStr =
                  "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"" + content
                      + "\"))";
          }
          
          else if (type == "ID")
          {
              uiautomatorStr =
                  "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceId(\""
                      + content + "\"))";
          }
          
          else if (type == "AccessibilityId")
          {
              uiautomatorStr =
                  "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().description(\""
                      + content + "\"))";
          }
          return uiautomatorStr;
      }
      
      // className method
      public static String scrollTo(String content, String className, String type, int number)
      {
          String uiautomatorStr = null;
          
          // find element by classname && index
          if (className == "CLASSNAME" && type == "INDEX")
          {
              uiautomatorStr = "new UiScrollable(new UiSelector().scrollable(true).index(" + number
                  + ")).getChildByText(new UiSelector().className(\"" + content + "\")";
          }
          // find element by classname && instance
          else if (className == "CLASSNAME" && type == "INSTENCE")
          {
              uiautomatorStr = "new UiScrollable(new UiSelector().scrollable(true).instance(" + number
                  + ")).getChildByText(new UiSelector().className(\"" + content + "\")";
          }
          return uiautomatorStr;
      }
      
      /**
       * 持续点击控件
       *
       * @param driver
       * @param by
       */
      public static void keepClickElement(WebElement rememberElement,WebElement allowElement) {
          try {
//              WebElement element = driver.findElement(by);
              while (true) {
                  if (rememberElement.isDisplayed() & allowElement.isDisplayed()) {
                	  rememberElement.click();
                	  allowElement.click();
                  } else {
                      break;
                  }
              }
          } catch (NoSuchElementException e) {
              System.out.println("未找到该控件: " );
          }
      }
    
      /**
       * 出现阻塞步骤的系统弹窗时，accept 继续
       *
       * @param driver
       */
//      public static void acceptPermission(AppiumDriver driver) {
//          clickElement(driver, new MobileBy.ByAndroidUIAutomator("new UiSelector().className(\"android.widget.Button\").textMatches(\".*允许.*\")"));
//      }
      
      //拨号键盘输入数字
      public static void inputNumember(String numember){

    	  String oneId = "com.android.dialer:id/one";
    	  String twoId = "com.android.dialer:id/two";
    	  String threeId = "com.android.dialer:id/three";
    	  String fourId = "com.android.dialer:id/four";
    	  String fiveId = "com.android.dialer:id/five";
		  String sixId = "com.android.dialer:id/six";
		  String sevenId = "com.android.dialer:id/seven";
		  String eightId = "com.android.dialer:id/eight";
		  String nineId = "com.android.dialer:id/nine";
		  String zeroId = "com.android.dialer:id/zero";
		  String starId = "com.android.dialer:id/star";
		  String poundId = "com.android.dialer:id/pound";

		  for(int i=0; i<numember.length(); i++){
			  switch(numember.charAt(i)){
				  case '1':
					  id(oneId).click();
					  break;
				  case '2':
					  id(twoId).click();
					  break;
				  case '3':
					  id(threeId).click();
					  break;
				  case '4':
					  id(fourId).click();
					  break;
				  case '5':
					  id(fiveId).click();
					  break;
				  case '6':
					  id(sixId).click();
					  break;
				  case '7':
					  id(sevenId).click();
					  break;
				  case '8':
					  id(eightId).click();
					  break;
				  case '9':
					  id(nineId).click();
					  break;
				  case '0':
					  id(zeroId).click();
					  break;
				  case '*':
					  id(starId).click();
					  break;
				  case '#':
					  id(poundId).click();
					  break;
			  }
		  }
      }
      
      //显示等待
      public static WebElement webDriverWait(By by ,int time){
    	  try{
    		  WebDriverWait wait = new WebDriverWait(androidDriver,time);
  			  WebElement el = wait.until(ExpectedConditions.elementToBeClickable(by));
    		  return el;
    	  }catch(Exception e){
    		  System.out.println("显式等待:未发现该元素");
    		  return null;
    	  }
    	  
      }
      
      //运行外部程序，比如adb;运行dos命令:"cmd.exe /c dir E:\\testProjects"
      public static String run(String command) throws IOException, InterruptedException{
    	  StringBuilder response =new StringBuilder();
    	  Process process = Runtime.getRuntime().exec(command);
    	  InputStream is = process.getInputStream();
    	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    	    String line;
    	    while((line = reader.readLine())!= null){
    	        System.out.println(line);
    	        response.append(line+"\n");
    	    }
    	    process.waitFor();
    	    is.close();
    	    reader.close();
    	    process.destroy();
    	    return response.toString();
      }
      //长按某个控件
      public static void longPress(WebElement element,long time){
    	  LongPressOptions longPressOptions = new LongPressOptions();
      	  //控件
      	  longPressOptions.withElement(new ElementOption().element(element));
      	  //长按时间
      	  longPressOptions.withDuration(Duration.ofSeconds(time));
      	  new TouchAction(androidDriver).longPress(longPressOptions).release().perform();
      }
      //删除ListView控件的每个item
      public static void deleteList(List<WebElement> list){
    	  for(int i = 0; i < list.size(); i++){
    		  //调用上面的长按函数,始终删除索引0的item,因为item会上移
    		  BaseAppium.longPress(list.get(0),1);
    		  BaseAppium.xpath(deleteButton).click();
    		  BaseAppium.id(enterButtonId).click();
    	  }
      }
}      
