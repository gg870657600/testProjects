package util;

import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

public class ScreenshotMethodObject {
	private static Logger logger = Logger.getLogger(ScreenshotMethodObject.class);
    public Date data;
 
    /**
     * 获取当前测试时间
     * 参数：无
     * return  返回当前日期 指定格式
     **/
    private String getTime(){
        SimpleDateFormat data = new SimpleDateFormat("yyyyMMddhhmm");
        return data.format(new Date());
    }
 
    /**
     * 截图（路径写死）
     * 参数：driver 对象
     * return  无
     **/
    public void Screenshot(TakesScreenshot drivername,String strName ){
        String filename=getTime()+strName + ".jpg";
        File scrFile = drivername.getScreenshotAs(OutputType.FILE);
        try{
            FileUtils.copyFile(scrFile,new File("E://testProjects//ZengQiang//test-output//Screenshot"+"\\"+filename));
        } catch (IOException e) {
        	logger.error("保存失败");
            e.printStackTrace();
        }
        finally {
        	logger.info("Screen shot finished, path in " + "E://testProjects//ZengQiang//test-output//Screenshot");
        }
    }
 
    /**
     * 截图（可自定义路径）
     * 参数：截图的图片的名字，和pathName 文件路径
     * return  无
     **/
    public void Screenshot(TakesScreenshot drivername,String pathName,String strName){
        String filename=getTime()+strName + ".jpg";
        File scrFile = drivername.getScreenshotAs(OutputType.FILE);
        try{
            FileUtils.copyFile(scrFile,new File(pathName+"\\"+filename));
        } catch (IOException e) {
        	logger.error("保存失败....");
            e.printStackTrace();
        }
        finally {
        	logger.info("Screen shot finished, path in " + pathName);
        }
    }
    //只需要传入driver对象
    public static void takeScreenShot(AndroidDriver driver)
    {  
       File screenShotFile = driver.getScreenshotAs(OutputType.FILE);  
       try {   
          FileUtils.copyFile(screenShotFile, new File("E:\\testProjects\\ZengQiang\\test-output\\Screenshot\\" + getCurrentDateTime()+ ".jpg"));  
          } 
       catch (IOException e) {e.printStackTrace();}  
    } 
    public static String getCurrentDateTime(){
       SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");//设置日期格式
       return df.format(new Date());
    }

}
