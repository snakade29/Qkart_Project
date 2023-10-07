package QKART_SANITY_LOGIN.Module1;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app";

    public Home(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean PerformLogout() throws InterruptedException {
        try {
            // Find and click on the Logout Button
            WebElement logout_button = driver.findElement(By.className("MuiButton-text"));
            logout_button.click();

            // SLEEP_STMT_10: Wait for Logout to complete
            // Wait for Logout to Complete
            Thread.sleep(3000);

            return true;
        } catch (Exception e) {
            // Error while logout
            return false;
        }
    }

    /*
     * Returns Boolean if searching for the given product name occurs without any errors
     */
    public Boolean searchForProduct(String product) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Clear the contents of the search box and Enter the product name in the search
            // box
            WebElement searchbox = this.driver
                    .findElement(By.xpath("//div[contains(@class ,'css-11zsshc')]//input"));
            searchbox.clear();
            searchbox.sendKeys(product);

            WebDriverWait wait = new WebDriverWait(driver, 5);
            // WebElement ele =
            // wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='MuiCardContent-root
            // css-1qw96cp']/p")));

            wait.until(ExpectedConditions.or(
                    // check for no result
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//*[@id=\"root\"]/div/div/div[3]/div/div[2]/div/h4")),
                    // check for results
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//div[@class='MuiCardContent-root css-1qw96cp']/p"))));
            // WebElement search
            // =this.driver.findElement(By.xpath("//div[@class='MuiOutlinedInput-root
            // MuiInputBase-root MuiInputBase-colorPrimary MuiInputBase-formControl
            // MuiInputBase-sizeSmall MuiInputBase-adornedEnd search
            // css-11zsshc']//div[@class='MuiInputAdornment-root MuiInputAdornment-positionEnd
            // MuiInputAdornment-outlined MuiInputAdornment-sizeSmall
            // css-1nvf7g0']//*[name()='svg']"));
            // search.click();
            // System.out.println(ele);

            return true;

        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    /*
     * Returns Array of Web Elements that are search results and return the same
     */
    public List<WebElement> getSearchResults() {
        List<WebElement> searchResults = new ArrayList<WebElement>();
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Find all webelements corresponding to the card content section of each of
            // search results

            searchResults = driver
                    .findElements(By.xpath("//div[@class='MuiCardContent-root css-1qw96cp']"));
            return searchResults;
        } catch (Exception e) {
            System.out.println("There were no search results: " + e.getMessage());
            return searchResults;

        }
    }

    /*
     * Returns Boolean based on if the "No products found" text is displayed
     */
    public Boolean isNoResultFound() {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Check the presence of "No products found" text in the web page. Assign status
            // = true if the element is *displayed* else set status = false
            WebElement Ele = driver.findElement(By.xpath("//h4[text()=' No products found ']"));

            if (Ele.isDisplayed())
                status = true;

            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if add product to cart is successful
     */
    public Boolean addProductToCart(String productName) {
        try {
            /*
             * Iterate through each product on the page to find the WebElement corresponding to the
             * matching productName
             * 
             * Click on the "ADD TO CART" button for that element
             * 
             * Return true if these operations succeeds
             */

            List<WebElement> products = driver
                    .findElements(By.xpath(" //div[@class='MuiCardContent-root css-1qw96cp']/p"));
            WebElement Addtocart =
                    driver.findElement(By.xpath(" //button[normalize-space()='Add to cart']"));

            Thread.sleep(2000);
            for (WebElement product : products) {
                if (product.getText().equalsIgnoreCase(productName)) {
                    Addtocart.click();
                    return true;
                }

            }
            Thread.sleep(2000);



            System.out.println("Unable to find the given product");
            return false;
        } catch (Exception e) {
            System.out.println("Exception while performing add to cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting the status of clicking on the checkout button
     */
    public Boolean clickCheckout() {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            // Find and click on the the Checkout button
            WebElement checkout =
                    driver.findElement(By.xpath(" //button[normalize-space()='Checkout']"));

            if (checkout.isDisplayed()) {
                status = true;
                checkout.click();
            }


            return status;
        } catch (Exception e) {
            System.out.println("Exception while clicking on Checkout: " + e.getMessage());
            return status;
        }
    }

    /*
     * Return Boolean denoting the status of change quantity of product in cart operation
     */
    public Boolean changeProductQuantityinCart(String productName, int expectedquantity) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 06: MILESTONE 5

            // Find the item on the cart with the matching productName

            // Increment or decrement the quantity of the matching product until the current
            // quantity is reached (Note: Keep a look out when then input quantity is 0,
            // here we need to remove the item completely from the cart)
            List<WebElement> productNameElements =
                    driver.findElements(By.xpath("//div[@class='MuiBox-root css-1gjj37g']/div[1]"));
            List<WebElement> actualItemQtnElements =
                    driver.findElements(By.xpath(" //div[@data-testid='item-qty']"));
            List<WebElement> plusButtonElements =
                    driver.findElements(By.xpath("  //*[@data-testid='AddOutlinedIcon']"));
            List<WebElement> minuButtonElements =
                    driver.findElements(By.xpath(" //*[@data-testid='RemoveOutlinedIcon']"));

            // WebDriverWait wait = new WebDriverWait(driver, 10);
            for (int i = 0; i < productNameElements.size(); i++) {
                WebElement productNameElement = productNameElements.get(i);
                String productelementName = productNameElement.getText();


                if (productelementName.equalsIgnoreCase(productName)) {
                    while (true) {

                        WebElement actualItemQtn = actualItemQtnElements.get(i);
                        String actualItem = actualItemQtn.getText();
                        int actualQtn = Integer.parseInt(actualItem);
                        if (actualQtn < expectedquantity) {
                            WebElement plusBtn = plusButtonElements.get(i);
                            plusBtn.click();

                        } else if (actualQtn > expectedquantity) {
                            WebElement minusBtn = minuButtonElements.get(i);
                            minusBtn.click();

                        } else if (expectedquantity == actualQtn) {
                            break;
                        }
                        synchronized (driver) {
                            driver.wait(2000);
                        }
                    }

                }
            }

            return false;
        } catch (Exception e) {
            if (expectedquantity == 0)
                return true;
            System.out.println("exception occurred when updating cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the cart contains items as expected
     */
    public Boolean verifyCartContents(List<String> expectedCartContents) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 07: MILESTONE 6

            // Get all the cart items as an array of webelements

            List<WebElement> CartContents = driver.findElements(
                    By.xpath("//*[@id='root']/div/div/div[3]/div[2]/div/div/div/div[2]/div[1]"));
            WebElement checkoutElements = driver.findElement(By.xpath(" //*[text()='Checkout']"));

            Thread.sleep(2000);
            // Iterate through expectedCartContents and check if item with matching product
            // name is present in the cart


            if (!checkoutElements.isDisplayed()) {
                return false;
            }

            for (int i = 0; i < CartContents.size(); i++) {

                WebElement cartContentElement = CartContents.get(i);
                String cartContentText = cartContentElement.getText();


                if (!cartContentText.equals(expectedCartContents.get(i))) {
                    return false;
                }



            }


            return true;

        } catch (Exception e) {
            System.out.println("Exception while verifying cart contents: " + e.getMessage());
            return false;
        }
    }



    public void ClickOnPrivacyPolicy() {
        driver.findElement(By.xpath("//a[text()='Privacy policy']")).click();
    }

    public void ClickOnTermsOfServices() {
        driver.findElement(By.xpath(" //a[text()='Terms of Service']")).click();
    }

    public void ClickOnContactUs() {
        driver.findElement(By.xpath("//p[text()='Contact us']")).click();
    }

    public boolean ContactUsDetail(String Name, String Email, String Message) {
        boolean status = false;
        WebElement name = driver.findElement(By.xpath(
                "((//button[text()=' Contact Now']//preceding::input[1])//preceding::input[1])//preceding::input[1]"));
        WebElement email = driver.findElement(By.xpath(
                "(//button[text()=' Contact Now']//preceding::input[1])//preceding::input[1]"));
        WebElement message = driver
                .findElement(By.xpath("//button[text()=' Contact Now']//preceding::input[1]"));
        name.sendKeys(Name);
        email.sendKeys(Email);
        message.sendKeys(Message);

        WebElement contact = driver.findElement(By.xpath("//button[text()=' Contact Now']"));
        contact.click();
        // try {
        // if(contact.isDisplayed())
        // status=true;

        // } catch (Exception e) {
        // //TODO: handle exception
        // status = false;
        // }

        // boolean isButtonInvisible = false;

        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.invisibilityOf(contact));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
