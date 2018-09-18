package devo.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage {

    @FindBy(how = How.NAME, using = "username")
    private WebElement usernameTextBox;

    @FindBy(how = How.NAME, using = "password")
    private WebElement passwordTextBox;

    @FindBy(how = How.CSS, using = "i.fa.fa-2x.fa-sign-in")
    private WebElement loginButton;

    @FindBy(how = How.ID, using = "flash")
    private WebElement message;

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void goToPage(String url){
        driver.get(url);
        if (!driver.getTitle().equalsIgnoreCase("The Internet")) throw new RuntimeException("Page is not correctly displayed!");


    }
    public SecureAreaPage login(String user, String pass){
        usernameTextBox.sendKeys(user);
        passwordTextBox.sendKeys(pass);
        loginButton.click();
        return new SecureAreaPage(this.driver);
    }


    public String getLoginMessage(){
        return    message.getText();
    }
}
