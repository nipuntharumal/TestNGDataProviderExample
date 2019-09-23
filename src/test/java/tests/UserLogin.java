package tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import pages.Home;
import pages.Login;
import services.DataAccess;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UserLogin {

    private WebDriver driver;
    private WebDriverWait wait;
    private WebElement element;
    private Login loginPage;
    private Home homePage;


    // -- This DataProvider method has been explicitly designed for the purpose of reading
    //    Json Object Data based on specified testcase. So that it can be utilized as a
    //    general DataProvider for all the test cases

    /** Test Method's respective name will get assigned to the Method Parameter during
     *  the runtime of each Test **/

    @DataProvider
    public Object[][] getTestData(Method method, ITestContext context) throws FileNotFoundException {

        String testDataFile = context.getCurrentXmlTest().getParameter("testDataFile");
        System.out.println(method.getName());

        FileReader fileReader = new FileReader(testDataFile);
        JsonElement jsonData = new JsonParser().parse(fileReader);
        JsonElement dataSet = jsonData.getAsJsonObject().get(method.getName());
        List<DataAccess> loginData = new Gson().fromJson(dataSet,new TypeToken<List<DataAccess>>(){}.getType());

        Object [][] dataValues = new Object[loginData.size()][1];
        int index = 0;

        for(Object[] each: dataValues){
            each[0]=loginData.get(index++);
        }

        return dataValues;
    }

    @Parameters({"url"})
    @BeforeMethod(groups = {"e2e"})
    public void setup(String url){

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(url);
        loginPage = new Login(driver);

    }

    @Test(groups = {"e2e"},dataProvider = "getTestData",priority = 1)
    public void should_be_successful_with_correct_credentials(DataAccess testData){

        loginPage.loginUser(testData.getUsername(),testData.getPassword());
        homePage = new Home(driver);

        wait = new WebDriverWait(driver,2);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(homePage.getProductsHomeLabel()));
        Assert.assertTrue(homePage.getProductsHomeLabel(element).contains(testData.getProductsHomeLabelValue()));

    }

    @Test (groups = {"e2e"},dataProvider = "getTestData",priority = 0)
    public void should_not_be_allowed_with_incorrect_password(DataAccess testData){

        loginPage.loginUser(testData.getUsername(),testData.getPassword());
        wait = new WebDriverWait(driver,2);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(loginPage.getLoginError()));
        Assert.assertTrue(loginPage.getLoginErrorMessage(element).contains(testData.getError()));

    }

    @Test (groups = {"e2e"},dataProvider="getTestData",priority = 3)
    public void should_not_be_allowed_with_invalid_user(DataAccess testData){

        loginPage.loginUser(testData.getUsername(),testData.getPassword());
        wait = new WebDriverWait(driver,2);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(loginPage.getLoginError()));
        Assert.assertTrue(loginPage.getLoginErrorMessage(element).contains(testData.getError()));

    }

    @Test (groups = {"e2e"},dataProvider="getTestData",priority = 4)
    public void should_not_be_allowed_for_locked_out__user(DataAccess testData){

        loginPage.loginUser(testData.getUsername(),testData.getPassword());
        wait = new WebDriverWait(driver,2);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(loginPage.getLoginError()));
        Assert.assertTrue(loginPage.getLoginErrorMessage(element).contains(testData.getError()));

    }

    @AfterMethod(groups = {"e2e"})
    public void close(){
        driver.close();
    }



}
