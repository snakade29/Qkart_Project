package QKART_SANITY_LOGIN.Module1;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Checkout {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app/checkout";

    public Checkout(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToCheckout() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    /*
     * Return Boolean denoting the status of adding a new address
     */
    public Boolean addNewAddress(String addresString) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            /*
             * Click on the "Add new address" button, enter the addressString in the address text
             * box and click on the "ADD" button to save the address
             */

            WebElement addNewAddressbtn =
                    driver.findElement(By.id("add-new-btn"));
           
            

          
            addNewAddressbtn.click();
            Thread.sleep(2000);

            WebElement textarea = driver.findElement(
                By.xpath(" //textarea[@placeholder='Enter your complete address']"));
            textarea.sendKeys(addresString);
            WebElement Add = driver.findElement(By.xpath(" //button[text()='Add']"));
            Add.click();

            WebDriverWait wait = new WebDriverWait(driver, 10);
               wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='address-item not-selected MuiBox-root css-0']")));


            /*
             * Click on the "Add new address" button, enter the addressString in the address
             * text box and click on the "ADD" button to save the address
             */
            return false;
        } catch (Exception e) {
            System.out.println("Exception occurred while entering address: " + e.getMessage());
            return false;

        }
    }

    /*
     * Return Boolean denoting the status of selecting an available address
     */
    public Boolean selectAddress(String addressToSelect) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            /*
             * Iterate through all the address boxes to find the address box with matching text,
             * addressToSelect and click on it
             */
            List<WebElement> addresstext = driver.findElements(
                    By.xpath(" //*[@id='root']/div/div[2]/div[1]/div/div[1]/div/div/p"));

            List<WebElement> addressbtn =
                    driver.findElements(By.xpath(" //div[@class='MuiBox-root css-0']//input"));
              
            for (int i = 0; i < addresstext.size(); i++) {
                WebElement address = addresstext.get(i);
                String addressadd = address.getText();
                if (addressadd.equalsIgnoreCase(addressToSelect)) {
                    WebElement radiobtn = addressbtn.get(i);
                    radiobtn.click();
                    return true ;
                    
                }

            }



            return false;
        } catch (Exception e) {
            System.out.println(
                    "Exception Occurred while selecting the given address: " + e.getMessage());
            /*
             * Iterate through all the address boxes to find the address box with matching
             * text, addressToSelect and click on it
             */
        }
        return null;

    }

    /*
     * Return Boolean denoting the status of place order action
     */
    public Boolean placeOrder() {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            // Find the "PLACE ORDER" button and click on it
            WebElement placeorder = driver.findElement(By.xpath(" //*[text()='PLACE ORDER']"));
            placeorder.click();
            return false;

        } catch (Exception e) {
            System.out.println("Exception while clicking on PLACE ORDER: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the insufficient balance message is displayed
     */
    public Boolean verifyInsufficientBalanceMessage() {
        try {
            // :CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 08: MILESTONE 7
               WebElement insufficiElement = driver.findElement(By.xpath("//div[@id='notistack-snackbar']"));

             if( insufficiElement.isDisplayed())
                {
                    if(insufficiElement.getText().equalsIgnoreCase("You do not have enough balance in your wallet for this purchase"))
                    return true ;
                }

                 
            

            return false;
        } catch (Exception e) {
            System.out.println(
                    "Exception while verifying insufficient balance message: " + e.getMessage());
            return false;
        }
    }
}
