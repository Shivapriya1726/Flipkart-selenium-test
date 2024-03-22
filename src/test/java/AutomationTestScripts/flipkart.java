package AutomationTestScripts;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class flipkart {
    public static void main(String[] args) {
        // Path to chromedriver
        System.setProperty("webdriver.chrome.driver", "D://chromedriver-win64//chromedriver-win64//chromedriver.exe");

        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Navigate to Flipkart website
        driver.get("https://www.flipkart.com/");

        // Maximize the window
        driver.manage().window().maximize();

        // Search for a Selenium book
        WebElement searchBox = driver.findElement(By.cssSelector("input[placeholder='Search for Products, Brands and More']"));
        searchBox.sendKeys("Selenium book");
        searchBox.sendKeys(Keys.ENTER);

        // Wait for search results to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement firstBook = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.s1Q9rs")));
        firstBook.click();


        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        // Check if delivery is available to the specified pin code and print the duration needed
        WebElement pincodeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pincodeInputId")));
        pincodeInput.sendKeys("600078");
        pincodeInput.sendKeys(Keys.ENTER);

        WebElement durationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div._3XINqE > span._1TPvTK")));
        String duration = durationElement.getText();
        System.out.println("Delivery Duration: " + duration);

        // Add to cart
        WebElement addToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button._2KpZ6l._2U9uOA._3v1-ww")));
        addToCartButton.click();

        // Wait for cart page to load
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // Proceed to checkout
        WebElement placeOrderButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button._2KpZ6l._2ObVJD._3AWRsL")));
        placeOrderButton.click();

        // Enter random email
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input._2IX_2-._17N0em")));
        emailInput.sendKeys("example");
        emailInput.sendKeys(Keys.ENTER);

        WebElement errorMessageElement = driver.findElement(By.cssSelector("span._2YULOR"));

        // Capturing the error message
        if (errorMessageElement.isDisplayed()) {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            String destFilePath = "images/errorMsg/error_message_screenshot.png";

            try {
                FileUtils.copyFile(srcFile, new File(destFilePath));
                System.out.println("Error message screenshot saved to: " + destFilePath);
            } catch (IOException e) {
                System.out.println("Failed to save error message screenshot: " + e.getMessage());
            }
        } else {
            System.out.println("Error message is not displayed. Skipping screenshot capture.");
        }

        // Quit the driver
        driver.quit();
    }
}
