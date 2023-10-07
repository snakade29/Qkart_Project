package QKART_TESTNG;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerClass implements ITestListener {
   
    public void onTestStart(ITestResult result) {
        System.out.println("New Test Started" +result.getName());
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("onTestSuccess Method" +result.getName());
    }

    public void onTestFailure(ITestResult result) {
        System.out.println("onTestFailure " +result.getName());
        QKART_Tests.takeScreenshot("onTestFailure", result.getName());
    }

    
}