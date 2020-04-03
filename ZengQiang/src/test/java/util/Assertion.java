package util;


import java.util.ArrayList;
import java.util.List;
 
import org.testng.Assert;
import org.testng.annotations.Listeners;
 
public class Assertion {
     
    public static boolean flag = true;
     
    public static List<Error> errors = new ArrayList<Error>();
     
    public static void verifyEquals(Object actual, Object expected){
        try{
            Assert.assertEquals(actual, expected);
        }catch(Error e){
            errors.add(e);
            flag = false;
        }
    }
     
    public static void verifyEquals(Object actual, Object expected, String message){
        try{
            Assert.assertEquals(actual, expected, message);
        }catch(Error e){
            errors.add(e);
            flag = false;
        }
    }
    
    public static void verifyNulls(boolean actual, boolean expected){
    	
    	try{
    		
    		Assert.assertEquals(actual, expected);
    		
    	}catch(Error e){
    		
    		errors.add(e);
            flag = false;
    	}
    }
 
    public static void verifyNulls(boolean actual, boolean expected , String msg){
    	
    	try{
    		
    		Assert.assertEquals(actual, expected, msg);
    		
    	}catch(Error e){
    		
    		errors.add(e);
            flag = false;
    	}
    }
    
    //其他需要的断言方法都可以这样写进来.....

}
