package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login {

    //declaring the driver
    private WebDriver driver;

    //Initialising the Web Page elements which were identified using locators
    private By username = By.id("user-name");
    private By password = By.id("password");
    private By signIn = By.xpath("//input[@type='submit']");
    private By loginError = By.xpath("//h3[@data-test='error']");


    public Login(WebDriver driver){

        this.driver = driver;
    }

    private void setUsername(String strUserName){
        driver.findElement(username).sendKeys(strUserName);
    }

    private void setPassword(String strPassword){
        driver.findElement(password).sendKeys(strPassword);
    }

    private void clickSignIn(){
        driver.findElement(signIn).click();

    }

    public void loginUser(String username, String password){
        setUsername(username);
        setPassword(password);
        clickSignIn();
    }

    public By getLoginError(){

        return loginError;
    }

    public String getLoginErrorMessage(WebElement element){

        return element.getText();
    }


}
