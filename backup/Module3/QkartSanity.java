/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package QKART_SANITY_LOGIN.Module1;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import org.openqa.selenium.JavascriptExecutor;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QkartSanity {

    public static String lastGeneratedUserName;


    public static RemoteWebDriver createDriver() throws MalformedURLException {
        // Launch Browser using Zalenium
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        RemoteWebDriver driver =
                new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);

        return driver;
    }

    public static void logStatus(String type, String message, String status) {

        System.out.println(String.format("%s |  %s  |  %s | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    public static void takeScreenshot(WebDriver driver, String screenshotType, String description) {
        // TODO: CRIO_TASK_MODULE_SYNCHRONISATION - Implement method using below steps
        /*
         * 1. Check if the folder "/screenshots" exists, create if it doesn't 2. Generate a unique
         * string using the timestamp 3. Capture screenshot 4. Save the screenshot inside the
         * "/screenshots" folder using the following naming convention:
         * screenshot_<Timestamp>_<ScreenshotType>_<Description>.png eg:
         * screenshot_2022-03-05T06:59:46.015489_StartTestcase_Testcase01.png
         */
        try {
            File theDir = new File("/screenshots");
            if (!theDir.exists()) {
                theDir.mkdirs();
            }

            String timestamp = String.valueOf(java.time.LocalDateTime.now());
            String fileName = String.format("screenshot_%s_%s_%s.png", timestamp, screenshotType,
                    description);

            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

            File DestFile = new File("screenshots/" + fileName);
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * Testcase01: Verify the functionality of Login button on the Home page
     */
    public static Boolean TestCase01(RemoteWebDriver driver) throws InterruptedException {
        Boolean status;
        logStatus("Start TestCase", "Test Case 1: Verify User Registration", "DONE");
        takeScreenshot(driver, "Start TestCase", "Test Case 1");
        // Visit the Registration page and register a new user
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);
        if (!status) {
            logStatus("End TestCase", "Test Case 1: Verify user Registration : ",
                    status ? "PASS" : "FAIL");

            // Return False as the test case Fails
            return false;
        } else {
            logStatus("TestCase 1", "Test Case Pass. User Registration Pass", "PASS");
        }

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the login page and login with the previuosly registered user
        Login login = new Login(driver);
        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, "abc@123");
        logStatus("Test Step", "User Perform Login: ", status ? "PASS" : "FAIL");
        if (!status) {
            logStatus("End TestCase", "Test Case 1: Verify user Registration : ",
                    status ? "PASS" : "FAIL");
            return false;
        }

        // Visit the home page and log out the logged in user
        Home home = new Home(driver);
        status = home.PerformLogout();
        logStatus("End TestCase", "Test Case 1: Verify user Registration : ",
                status ? "PASS" : "FAIL");
        takeScreenshot(driver, "End TestCase", "Test Case 1");
        takeScreenshot(driver, "End TestCase", "Test Case 1");
        return status;
    }

    /*
     * Verify that an existing user is not allowed to re-register on QKart
     */
    public static Boolean TestCase02(RemoteWebDriver driver) throws InterruptedException {
        Boolean status;
        logStatus("Start Testcase",
                "Test Case 2: Verify User Registration with an existing username ", "DONE");
        takeScreenshot(driver, "Start TestCase", "Test Case 2");
        // Visit the Registration page and register a new user
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);
        logStatus("Test Step", "User Registration : ", status ? "PASS" : "FAIL");
        if (!status) {
            logStatus("End TestCase", "Test Case 2: Verify user Registration : ",
                    status ? "PASS" : "FAIL");
            return false;

        }

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the Registration page and try to register using the previously
        // registered user's credentials
        registration.navigateToRegisterPage();
        status = registration.registerUser(lastGeneratedUserName, "abc@123", false);

        // If status is true, then registration succeeded, else registration has
        // failed. In this case registration failure means Success
        logStatus("End TestCase", "Test Case 2: Verify user Registration : ",
                status ? "FAIL" : "PASS");
        takeScreenshot(driver, "End TestCase", "Test Case 2");
        return !status;
    }

    /*
     * Verify the functinality of the search text box
     */
    public static Boolean TestCase03(RemoteWebDriver driver) throws InterruptedException {
        logStatus("TestCase 3", "Start test case : Verify functionality of search box ", "DONE");
        takeScreenshot(driver, "Start TestCase", "Test Case 3");
        boolean status;

        // Visit the home page
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        // SLEEP_STMT_01 : Wait for Page to Load
        Thread.sleep(5000);

        // Search for the "yonex" product
        status = homePage.searchForProduct("yonex");
        if (!status) {
            logStatus("TestCase 3", "Test Case Failure. Unable to search for given product",
                    "FAIL");
            return false;
        }

        // Fetch the search results
        List<WebElement> searchResults = homePage.getSearchResults();

        // Verify the search results are available
        if (searchResults.size() == 0) {
            logStatus("TestCase 3",
                    "Test Case Failure. There were no results for the given search string", "FAIL");
            return false;
        }

        for (WebElement webElement : searchResults) {
            // Create a SearchResult object from the parent element
            SearchResult resultelement = new SearchResult(webElement);

            // Verify that all results contain the searched text
            String elementText = resultelement.getTitleofResult();
            if (!elementText.toUpperCase().contains("YONEX")) {
                logStatus("TestCase 3",
                        "Test Case Failure. Test Results contains un-expected values: "
                                + elementText,
                        "FAIL");
                return false;
            }
        }

        logStatus("Step Success", "Successfully validated the search results ", "PASS");
        // SLEEP_STMT_02
        Thread.sleep(2000);

        // Search for product
        status = homePage.searchForProduct("Gesundheit");
        if (!status) {
            logStatus("TestCase 3", "Test Case Failure. Unable to search for given product",
                    "FAIL");
            return false;
        }

        // Verify no search results are found
        searchResults = homePage.getSearchResults();
        if (searchResults.size() == 0) {
            if (homePage.isNoResultFound()) {
                logStatus("Step Success",
                        "Successfully validated that no products found message is displayed",
                        "PASS");
            }
            logStatus("TestCase 3",
                    "Test Case PASS. Verified that no search results were found for the given text",
                    "PASS");
        } else {
            logStatus("TestCase 3",
                    "Test Case Fail. Expected: no results , actual: Results were available",
                    "FAIL");
            return false;
        }
        takeScreenshot(driver, "End TestCase", "Test Case 3");
        return true;
    }

    /*
     * Verify the presence of size chart and check if the size chart content is as expected
     */
    public static Boolean TestCase04(RemoteWebDriver driver) throws InterruptedException {
        logStatus("TestCase 4", "Start test case : Verify the presence of size Chart", "DONE");
        takeScreenshot(driver, "Start TestCase", "Test Case 4");
        boolean status = false;

        // Visit home page
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        // SLEEP_STMT_03 : Wait for page to load
        Thread.sleep(5000);

        // Search for product and get card content element of search results
        status = homePage.searchForProduct("Running Shoes");
        List<WebElement> searchResults = homePage.getSearchResults();

        // Create expected values
        List<String> expectedTableHeaders = Arrays.asList("Size", "UK/INDIA", "EU", "HEEL TO TOE");
        List<List<String>> expectedTableBody = Arrays.asList(Arrays.asList("6", "6", "40", "9.8"),
                Arrays.asList("7", "7", "41", "10.2"), Arrays.asList("8", "8", "42", "10.6"),
                Arrays.asList("9", "9", "43", "11"), Arrays.asList("10", "10", "44", "11.5"),
                Arrays.asList("11", "11", "45", "12.2"), Arrays.asList("12", "12", "46", "12.6"));

        // Verify size chart presence and content matching for each search result
        for (WebElement webElement : searchResults) {
            SearchResult result = new SearchResult(webElement);

            // Verify if the size chart exists for the search result
            if (result.verifySizeChartExists()) {
                logStatus("Step Success", "Successfully validated presence of Size Chart Link",
                        "PASS");

                // Verify if size dropdown exists
                status = result.verifyExistenceofSizeDropdown(driver);
                logStatus("Step Success", "Validated presence of drop down",
                        status ? "PASS" : "FAIL");

                // Open the size chart
                if (result.openSizechart()) {
                    // Verify if the size chart contents matches the expected values
                    if (result.validateSizeChartContents(expectedTableHeaders, expectedTableBody,
                            driver)) {
                        logStatus("Step Success",
                                "Successfully validated contents of Size Chart Link", "PASS");
                    } else {
                        logStatus("Step Failure",
                                "Failure while validating contents of Size Chart Link", "FAIL");
                    }

                    // Close the size chart modal
                    status = result.closeSizeChart(driver);

                } else {
                    logStatus("TestCase 4", "Test Case Fail. Failure to open Size Chart", "FAIL");
                    return false;
                }

            } else {
                logStatus("TestCase 4", "Test Case Fail. Size Chart Link does not exist", "FAIL");
                return false;
            }
        }
        logStatus("TestCase 4", "Test Case PASS. Validated Size Chart Details", "PASS");
        takeScreenshot(driver, "End TestCase", "Test Case 4");
        return status;
    }

    /*
     * Verify the complete flow of checking out and placing order for products is working correctly
     */
    public static Boolean TestCase05(RemoteWebDriver driver) throws InterruptedException {
        Boolean status;
        logStatus("Start TestCase", "Test Case 5: Verify Happy Flow of buying products", "DONE");
        takeScreenshot(driver, "Start TestCase", "Test Case 5");
        // Go to the Register page
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();

        // Register a new user
        status = registration.registerUser("testUser", "abc@123", true);
        if (!status) {
            logStatus("TestCase 5", "Test Case Failure. Happy Flow Test Failed", "FAIL");
        }

        // Save the username of the newly registered user
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Go to the login page
        Login login = new Login(driver);
        login.navigateToLoginPage();

        // Login with the newly registered user's credentials
        status = login.PerformLogin(lastGeneratedUserName, "abc@123");
        if (!status) {
            logStatus("Step Failure", "User Perform Login Failed", status ? "PASS" : "FAIL");
            logStatus("End TestCase", "Test Case 5: Happy Flow Test Failed : ",
                    status ? "PASS" : "FAIL");
        }

        // Go to the home page
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        // Find required products by searching and add them to the user's cart
        status = homePage.searchForProduct("Yonex");
        homePage.addProductToCart("YONEX Smash Badminton Racquet");
        status = homePage.searchForProduct("Tan");
        homePage.addProductToCart("Tan Leatherette Weekender Duffle");

        // Click on the checkout button
        homePage.clickCheckout();

        // Add a new address on the Checkout page and select it
        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress("Addr line 1 addr Line 2 addr line 3");
        checkoutPage.selectAddress("Addr line 1 addr Line 2 addr line 3");

        // Place the order
        checkoutPage.placeOrder();
        // SLEEP_STMT_04: Wait for place order to succeed and navigate to Thanks page
        Thread.sleep(3000);

        // Check if placing order redirected to the Thansk page
        status = driver.getCurrentUrl().endsWith("/thanks");

        // Go to the home page
        homePage.navigateToHome();
        Thread.sleep(3000);

        // Log out the user
        homePage.PerformLogout();

        logStatus("End TestCase", "Test Case 5: Happy Flow Test Completed : ",
                status ? "PASS" : "FAIL");
        takeScreenshot(driver, "End TestCase", "Test Case 5");
        return status;
    }

    /*
     * Verify the quantity of items in cart can be updated
     */
    public static Boolean TestCase06(RemoteWebDriver driver) throws InterruptedException {
        Boolean status;
        logStatus("Start TestCase", "Test Case 6: Verify that cart can be edited", "DONE");
        takeScreenshot(driver, "Start TestCase", "Test Case 6");
        Home homePage = new Home(driver);
        Register registration = new Register(driver);
        Login login = new Login(driver);

        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);


        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the login page and login with the previuosly registered user

        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, "abc@123");
        logStatus("Test Step", "User Perform Login: ", status ? "PASS" : "FAIL");

        status = homePage.searchForProduct("Xtend Smart Watch");
        homePage.addProductToCart("Xtend Smart Watch");
        status = homePage.searchForProduct("Yarine Floor Lamp");
        homePage.addProductToCart("Yarine Floor Lamp");



        homePage.changeProductQuantityinCart("Xtend Smart Watch", 2);

        homePage.changeProductQuantityinCart("Yarine Floor Lamp", 0);

        homePage.changeProductQuantityinCart("Xtend Smart Watch", 1);

        homePage.clickCheckout();

        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress("Addr line 1 addr Line 2 addr line 3");
        checkoutPage.selectAddress("Addr line 1 addr Line 2 addr line 3");

        checkoutPage.placeOrder();
        Thread.sleep(3000);

        status = driver.getCurrentUrl().endsWith("/thanks");

        homePage.navigateToHome();
        Thread.sleep(3000);
        homePage.PerformLogout();

        logStatus("End TestCase", "Test Case 6: Verify that cart can be edited: ",
                status ? "PASS" : "FAIL");
        takeScreenshot(driver, "End TestCase", "Test Case 6");
        return status;
    }

    /*
     * Verify that the cart contents are persisted after logout
     */
    public static Boolean TestCase07(RemoteWebDriver driver) throws InterruptedException {
        Boolean status = false;
        List<String> expectedResult =
                Arrays.asList("Stylecon 9 Seater RHS Sofa Set", "Xtend Smart Watch");

        logStatus("Start TestCase",
                "Test Case 7: Verify that cart contents are persisted after logout", "DONE");
        takeScreenshot(driver, "Start TestCase", "Test Case 7");
        Home homePage = new Home(driver);
        Register registration = new Register(driver);
        Login login = new Login(driver);

        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);


        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the login page and login with the previuosly registered user

        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, "abc@123");
        logStatus("Test Step", "User Perform Login: ", status ? "PASS" : "FAIL");



        homePage.navigateToHome();
        status = homePage.searchForProduct("Stylecon");
        homePage.addProductToCart("Stylecon 9 Seater RHS Sofa Set");

        status = homePage.searchForProduct("Xtend");
        homePage.addProductToCart("Xtend Smart Watch");

        homePage.PerformLogout();

        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, "abc@123");
        Thread.sleep(3000);

        status = homePage.verifyCartContents(expectedResult);

        logStatus("End TestCase",
                "Test Case 7: Verify that cart contents are persisted after logout: ",
                status ? "PASS" : "FAIL");

        homePage.PerformLogout();
        takeScreenshot(driver, "End TestCase", "Test Case 7");
        return status;
    }

    public static Boolean TestCase08(RemoteWebDriver driver) throws InterruptedException {
        Boolean status;
        logStatus("Start TestCase",
                "Test Case 8: Verify that insufficient balance error is thrown when the wallet balance is not enough",
                "DONE");
        takeScreenshot(driver, "Start TestCase", "Test Case 8");

        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);
        if (!status) {
            logStatus("Step Failure", "User Perform Registration Failed", status ? "PASS" : "FAIL");
            logStatus("End TestCase",
                    "Test Case 8: Verify that insufficient balance error is thrown when the wallet balance is not enough: ",
                    status ? "PASS" : "FAIL");
            return false;
        }
        lastGeneratedUserName = registration.lastGeneratedUsername;

        Login login = new Login(driver);
        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, "abc@123");
        if (!status) {
            logStatus("Step Failure", "User Perform Login Failed", status ? "PASS" : "FAIL");
            logStatus("End TestCase",
                    "Test Case 8: Verify that insufficient balance error is thrown when the wallet balance is not enough: ",
                    status ? "PASS" : "FAIL");

            return false;
        }

        Home homePage = new Home(driver);
        homePage.navigateToHome();
        status = homePage.searchForProduct("Stylecon");
        homePage.addProductToCart("Stylecon 9 Seater RHS Sofa Set");
        Thread.sleep(3000);

        homePage.changeProductQuantityinCart("Stylecon 9 Seater RHS Sofa Set", 10);

        homePage.clickCheckout();

        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress("Addr line 1 addr Line 2 addr line 3");
        checkoutPage.selectAddress("Addr line 1 addr Line 2 addr line 3");

        checkoutPage.placeOrder();
        Thread.sleep(3000);

        status = checkoutPage.verifyInsufficientBalanceMessage();

        logStatus("End TestCase",
                "Test Case 8: Verify that insufficient balance error is thrown when the wallet balance is not enough: ",
                status ? "PASS" : "FAIL");

        takeScreenshot(driver, "End TestCase", "Test Case 8");

        return status;
    }

    public static Boolean TestCase09(RemoteWebDriver driver) throws InterruptedException {
        Boolean status = false;
        logStatus("Start TestCase",
                "Test Case 09 :Verify the content of the cart  after switch to new tab", "Done");
        takeScreenshot(driver, "Start TestCase", "TestCase 9");
        List<String> expectedResult = Arrays.asList("YONEX Smash Badminton Racquet");

        Home homePage = new Home(driver);
        Register registration = new Register(driver);
        Login login = new Login(driver);

        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);


        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the login page and login with the previuosly registered user

        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, "abc@123");
        logStatus("Test Step", "User Perform Login: ", status ? "PASS" : "FAIL");



        homePage.navigateToHome();
        status = homePage.searchForProduct("YONEX Smash Badminton Racquet");
        homePage.addProductToCart("YONEX Smash Badminton Racquet");

        String currentUrl = driver.getCurrentUrl();

        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));


        driver.get(currentUrl);
    
        homePage.navigateToHome();
        



        status = homePage.verifyCartContents(expectedResult);
         

       

        homePage.PerformLogout();
       
       

        logStatus("End TestCase",
                "Test Case 09 : Verify the content of the cart  after switch to new tab",
                status ? "PASS" : "FAIL");

        takeScreenshot(driver, "End TestCase", "Test Case 09");
        
        // driver.switchTo().window(tabs.get(0)).close();
        driver.close();
        driver.switchTo().window(tabs.get(0));
      

    
        homePage.navigateToHome();

        

        // TODO: CRIO_TASK_MODULE_SYNCHRONISATION -
        return status;
    }

    public static Boolean TestCase10(RemoteWebDriver driver) throws InterruptedException {
        // TODO: CRIO_TASK_MODULE_SYNCHRONISATION -

        Boolean status = true;
        logStatus("Start TestCase", "Test Case 10 : Verify Privacy Policy and Terms of Service",
                "Done");
        
        Home homePage = new Home(driver);
        homePage.navigateToHome();
        takeScreenshot(driver, "Start TestCase", "TestCase 10");
        String parentUrlBeforeClick = driver.getCurrentUrl();
        String parentWindow = driver.getWindowHandle();
        homePage.ClickOnPrivacyPolicy();

        // Now iterate using Iterator


        // driver.switchTo().window(parentWindow);

        String parentUrlAfterclick = driver.getCurrentUrl();


        if (!parentUrlBeforeClick.equalsIgnoreCase(parentUrlAfterclick))
            logStatus("Test",
                    "Test Case 10 :Verify that the url of the current tab does not change", "Fail");



        Set<String> s = driver.getWindowHandles();

        // Now iterate using Iterator
        Iterator<String> I1 = s.iterator();

        while (I1.hasNext()) {
            String child_window = I1.next();

            if (!parentWindow.equals(child_window)) {

                driver.switchTo().window(child_window);
                WebDriverWait wait = new WebDriverWait(driver, 10);

                WebElement privacyPolicyContent =
                        driver.findElement(By.xpath("//h2[text()='Privacy Policy']"));
                wait.until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//h2[text()='Privacy Policy']")));

                if (!privacyPolicyContent.isDisplayed()) {
                    logStatus("Test",
                            "Verify  if the privacy policy content is available on the newly opened tab",
                            "Fail");
                    status = false;
                }

                driver.close();
            }

        }

        driver.switchTo().window(parentWindow);

        homePage.ClickOnTermsOfServices();

        // driver.switchTo().window(parentWindow);

        if (!parentUrlBeforeClick.equals(driver.getCurrentUrl())) {
            logStatus("Test", "Verify that the url of the current tab does not change", "Fail");
            status = false;
        }

        Set<String> s1 = driver.getWindowHandles();

        // Now iterate using Iterator
        Iterator<String> I2 = s1.iterator();

        while (I2.hasNext()) {
            String child_window = I2.next();

            if (!parentWindow.equals(child_window)) {
                driver.switchTo().window(child_window);

                WebDriverWait wait = new WebDriverWait(driver, 10);

                WebElement TermsOfServicesContent =
                        driver.findElement(By.xpath("//h2[text()='Terms of Service']"));
                wait.until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//h2[text()='Terms of Service']")));
                Thread.sleep(3000);

                if (!TermsOfServicesContent.isDisplayed()) {
                    logStatus("Test",
                            "Verify  if the Terms of servicescontent is available on the newly opened tab",
                            "Fail");
                    status = false;
                   

                }
                driver.close();
                
            }
            
        }
        driver.switchTo().window(parentWindow);

        
        



        logStatus("End TestCase", "Test Case 10 :Verify Privacy Policy and Terms of Service",
                status ? "PASS" : "FAIL");
        takeScreenshot(driver, "End TestCase", "Test Case 10");

        return status;
    }

    public static Boolean TestCase11(RemoteWebDriver driver) throws InterruptedException {
        Boolean status = false;
        // TODO: CRIO_TASK_MODULE_SYNCHRONISATION -
        logStatus("Start TestCase ", "Test Case 11 : verify that the “Contact us” link works fine",
                "Done");
        takeScreenshot(driver, "Start TestCase", "TestCase 11");
        Home homePage = new Home(driver);
        homePage.navigateToHome();
        homePage.ClickOnContactUs();

        status = homePage.ContactUsDetail("crio user", " criouser@gmail.com",
                "Testing the contact us page");

        logStatus("End TestCase ", "Test Case 11 : verify that the “Contact us” link works fine ",
                status ? "PASS" : "FAIL");
        takeScreenshot(driver, "End TestCase", "Test Case 11");

        return status;
    }

    public static Boolean TestCase12(RemoteWebDriver driver) throws InterruptedException {
        Boolean status = false;
        // TODO: CRIO_TASK_MODULE_SYNCHRONISATION -

        logStatus("Start TestCase", "Test Case 12:  ", "DONE");
        takeScreenshot(driver, "Start TestCase", "Test Case 12");

        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);

        lastGeneratedUserName = registration.lastGeneratedUsername;

        Login login = new Login(driver);
        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, "abc@123");


        Home homePage = new Home(driver);
        homePage.navigateToHome();
        status = homePage.searchForProduct("YONEX Smash Badminton Racquet");
        homePage.addProductToCart("YONEX Smash Badminton Racquet");



        homePage.clickCheckout();

        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress("Addr line 1 addr Line 2 addr line 3");
        checkoutPage.selectAddress("Addr line 1 addr Line 2 addr line 3");

        checkoutPage.placeOrder();


        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            wait.until(
                    ExpectedConditions.urlToBe("https://crio-qkart-frontend-qa.vercel.app/thanks"));

        } catch (Exception e) {
            // TODO: handle exception
            logStatus("Test Case", "verify if  the final page endswith (“/thanks”)",
                    status ? "PASS" : "FAIL");

        }


        int size = driver.findElements(By.xpath("//*[@id='root']//iframe")).size();

        if (size == 3)
            logStatus("Test Case", "Verify a total of 3 advertisements are present ", "Pass");
        else
            logStatus("Test Case", "Verify a total of 3 advertisements are present ", "Fail");

        //int buyNowButtonSize = driver.findElements(By.xpath(" //*[text()='Buy Now']")).size();

        for (int i = 1; i <= 2; i++) {
            driver.switchTo().frame(driver
                    .findElement(By.xpath("//*[@id='root']/div/div[2]/div/iframe[" + i + "]")));
            driver.findElement(By.xpath("//*[text()='Buy Now']"));

            try {
                wait.until(ExpectedConditions.elementToBeClickable(
                        driver.findElement(By.xpath("//*[text()='Buy Now']"))));

                        driver.findElement(By.xpath("//*[text()='Buy Now']")).click();
                String checkout="https://crio-qkart-frontend-qa.vercel.app/checkout";
                        
                if(!checkout.equalsIgnoreCase(driver.getCurrentUrl()))
                        logStatus("Test","After clicking buy now not getting the same url ", "Fail");
                         driver.navigate().back();
                         driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div/iframe[" + i + "]")));
                         wait.until(ExpectedConditions.elementToBeClickable(
                            driver.findElement(By.xpath("//button[text()='View Cart']"))));
                            driver.findElement(By.xpath("//button[text()='View Cart']")).click();
                       String homeurl ="https://crio-qkart-frontend-qa.vercel.app/";
                       if(!homeurl.equalsIgnoreCase( driver.getCurrentUrl()))
                       logStatus("Test","After clicking buy now not getting the same url ", "Fail");
                       driver.navigate().back();

            } catch (Exception e) {
                // TODO: handle exception
                status = false;
                logStatus("Test Case", "Element Not Found", "FAIL");

            }

        }

    driver.switchTo().parentFrame();

        logStatus("End TestCase", "Test Case 12:   ", status ? "PASS" : "FAIL");

        takeScreenshot(driver, "End TestCase", "Test Case 12");

        return status;



    }

    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        int totalTests = 0;
        int passedTests = 0;
        Boolean status;
        RemoteWebDriver driver = createDriver();
        // Maximize and Implicit Wait for things to initailize
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        try {
            // Execute Test Case 1
            totalTests += 1; 
            status = TestCase01(driver);
            if (status) {
            passedTests += 1;
            }

            System.out.println("");

            // // Execute Test Case 2
            totalTests += 1;
            status = TestCase02(driver);
            if (status) {
            passedTests += 1;
            }

            System.out.println("");
            // // Execute Test Case 3
            totalTests += 1;
            status = TestCase03(driver);
            if (status) {
            passedTests += 1;
            }

            System.out.println("");

            // // Execute Test Case 4
            totalTests += 1;
            status = TestCase04(driver);
            if (status) {
            passedTests += 1;
            }

            System.out.println("");

            // // Execute Test Case 5
            totalTests += 1;
            status = TestCase05(driver);
            if (status) {
            passedTests += 1;
            }

            System.out.println("");

            // Execute Test Case 6
            totalTests += 1;
            status = TestCase06(driver);
            if (status) {
            passedTests += 1;
            }

            System.out.println("");

            // Execute Test Case 7
            totalTests += 1;
            status = TestCase07(driver);
            if (status) {
            passedTests += 1;
            }

            System.out.println("");

            // Execute Test Case 8
            totalTests += 1;
            status = TestCase08(driver);
            if (status) {
            passedTests += 1;
            }


            System.out.println("");

            // // Execute Test Case 9
            totalTests += 1;
            status = TestCase09(driver);
            if (status) {
            passedTests += 1;
            }

            System.out.println("");

            // Execute Test Case 10

            totalTests += 1;
            status = TestCase10(driver);
            if (status) {
            passedTests += 1;
            } 

            System.out.println("");

            // Execute Test Case 11
            totalTests += 1;
            status = TestCase11(driver);
            if (status) {
                passedTests += 1;
            }
            System.out.println("");

            // Execute Test Case 12
            totalTests += 1;
            status = TestCase12(driver);
            if (status) {
            passedTests += 1;
            }

            System.out.println("");
        } catch (Exception e) {
            throw e;
        } finally {
            // quit Chrome Driver
            driver.quit();

            System.out.println(String.format("%s out of %s test cases Passed ",
                    Integer.toString(passedTests), Integer.toString(totalTests)));
        }

    }
}
