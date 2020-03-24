package util;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
//TestNG测试框架中断言失败是程序继续执行、只负责收集失败信息,重写断言方法
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
}
