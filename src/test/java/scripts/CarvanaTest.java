package scripts;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Waiter;

import java.util.List;

public class CarvanaTest extends Base{

    @Test(testName = "Validate Carvana home page title and url", priority = 1)
    public void validateTitleAndUrl(){
        driver.get("https://www.carvana.com/");
        Assert.assertEquals(driver.getTitle(), "Carvana | Buy & Finance Used Cars Online | At Home Delivery");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.carvana.com/");

    }

    @Test(testName = "Validate the Carvana logo", priority = 2)
    public void validateLogoIsDisplayed(){
        driver.get("https://www.carvana.com/");
        WebElement logo = driver.findElement(By.xpath("//div[@role='heading']"));
        Assert.assertTrue(logo.isDisplayed());
    }

    @Test(testName = "Validate the main navigation section items", priority = 3)
    public void validateCarvanaInfo(){
        driver.get("https://www.carvana.com/");
        List<WebElement> dropdowns = driver.findElements(By.xpath("//div[@data-qa='navigation-wrapper']/div/a"));
        for (WebElement dropdown : dropdowns) {
            Assert.assertTrue(dropdown.isDisplayed());
        }
    }

    @Test(testName = "Validate the sign in error message", priority = 4)
    public void validateErrorMessage(){
        driver.get("https://www.carvana.com/");
        driver.findElement(By.xpath("//a[@data-ae-blurbtype='button']")).click();
        driver.findElement(By.id("usernameField")).sendKeys("johndoe@gmail.com");
        driver.findElement(By.id("passwordField")).sendKeys("abcd1234");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        WebElement errorMessage = driver.findElement(By.xpath("//div[@data-qa='error-message-container']"));

        Assert.assertEquals(errorMessage.getText(), "Email address and/or password combination is incorrect\n" +
                "Please try again or reset your password.");
    }

    @Test(testName = "Validate the search filter options and search button", priority = 5)
    public void ValidateTheSearchFilterOptionsAndSearchButton(){
        driver.get("https://www.carvana.com/");
        driver.findElement(By.xpath("(//a[@data-test='TrackLink'])[2]")).click();

        List<WebElement> filterOptions = driver.findElements(By.xpath("//button[@data-qa='drop-down-wrap']"));
        for (WebElement filterOption : filterOptions) {
            Assert.assertTrue(filterOption.isDisplayed());
        }
        driver.findElement(By.xpath("//input[@data-qa='search-bar-input']")).sendKeys("Tesla");
        WebElement goButton = driver.findElement(By.xpath("//button[@data-qa='go-button']"));

        Assert.assertTrue(goButton.isDisplayed());
        Assert.assertEquals(goButton.getText(), "GO");
    }

    @Test(testName = "Validate the search result tiles", priority = 6)
    public void validateTheSearchResultTiles() {
        driver.get("https://www.carvana.com/");//URL page
        driver.findElement(By.cssSelector("a[data-cv-test*='.SearchLink']")).click();// *Search All Vehicles link*

        Waiter.pause(5);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.carvana.com/cars"); //make sure the Right URL

        WebElement searchInputBox = driver.findElement(By.cssSelector("input[data-qa='search-bar-input']"));
        searchInputBox.sendKeys("mercedes-benz");//type mercedes-benz on input box

        WebElement goButton = driver.findElement(By.cssSelector("button[data-qa='go-button']"));
        goButton.click();
        Waiter.pause(5);

//      Assert.assertEquals(driver.getCurrentUrl(), "https://www.carvana.com/cars/mercedes-benz?email-capture="); //Same URL
        Assert.assertTrue(driver.getCurrentUrl().contains("mercedes-benz")); //Contains "mercedes-benz" on URL

        List<WebElement> mercedesTiles = driver.findElements(By.className("tk-shell")); //size = 20
        List<WebElement> imagesOfMercedesCarsResults = driver.findElements(By.className("vehicle-image"));
        List<WebElement> favoriteMercedesCarsButtons = driver.findElements(By.className("favorite-icon"));
        List<WebElement> inventoryMercedesCarsResults = driver.findElements(By.cssSelector("div[class*='inventory']"));
        List<WebElement> yearMakeAndModelMercedesCarsResults = driver.findElements(By.className("year-make-experiment"));
        List<WebElement> trimMercedesCarsResults = driver.findElements(By.className("trim-mileage"));
        List<WebElement> pricesMercedesCarsResults = driver.findElements(By.className("price-variant"));
        List<WebElement> monthlyPaymentMercedesCarsResults = driver.findElements(By.className("monthly-payment"));
        List<WebElement> downPaymentMercedesCarsResults = driver.findElements(By.className("down-payment"));
        List<WebElement> deliveryChipMercedesCarsResults = driver.findElements(By.cssSelector(".delivery"));


        for (int i = 0; i < mercedesTiles.size(); i++) {
            Assert.assertTrue(mercedesTiles.get(i).isDisplayed()); // check each result title
            Assert.assertTrue(imagesOfMercedesCarsResults.get(i).isDisplayed()); //check each img result
            Assert.assertTrue(favoriteMercedesCarsButtons.get(i).isDisplayed()); //check each favorite result
            Assert.assertTrue(inventoryMercedesCarsResults.get(i).isDisplayed()); //check each inventory result
            Assert.assertNotNull(inventoryMercedesCarsResults.get(i).getText());
            Assert.assertTrue(yearMakeAndModelMercedesCarsResults.get(i).isDisplayed()); //check each yearMake&&Model result
            Assert.assertNotNull(yearMakeAndModelMercedesCarsResults.get(i).getText());
            Assert.assertTrue(trimMercedesCarsResults.get(i).isDisplayed()); //check each trim result
            Assert.assertNotNull(trimMercedesCarsResults.get(i).getText());
            Assert.assertTrue(Integer.parseInt(pricesMercedesCarsResults.get(i).getText()
                    .replaceAll("[^0-9]", "")) > 0); //check each price result
            Assert.assertTrue(monthlyPaymentMercedesCarsResults.get(i).isDisplayed()); //check each monthly payment result
            Assert.assertNotNull(monthlyPaymentMercedesCarsResults.get(i).getText());
            Assert.assertTrue(downPaymentMercedesCarsResults.get(i).isDisplayed()); //check each down payment result
            Assert.assertNotNull(downPaymentMercedesCarsResults.get(i).getText());
            Assert.assertTrue(deliveryChipMercedesCarsResults.get(i).isDisplayed()); //check each delivery Chip result
            //Waiter.waitForElementToBeVisible(driver, 5, deliveryChipMercedesCarsResults.get(i));
            Assert.assertEquals(deliveryChipMercedesCarsResults.get(i).getText(), "Free Shipping"); //check each delivery Chip result text

        }
    }

}
