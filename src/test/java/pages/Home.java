package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Home {

    private WebDriver driver;

    private By productsHomeLabel = By.xpath("//div[@class='product_label']");

    public Home(WebDriver driver){

        this.driver = driver;
    }

    public By getProductsHomeLabel() {
        return productsHomeLabel;
    }

    public String getProductsHomeLabel(WebElement element){

        return element.getText();
    }

}
