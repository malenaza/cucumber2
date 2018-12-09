package devo.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SecureAreaPage {

    @FindBy(how = How.ID, using = "flash")
    private WebElement message;

    private WebDriver driver;

    public SecureAreaPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver,this);
    }

    public String getLoginMessage(){
        return    message.getText();
    }
}
