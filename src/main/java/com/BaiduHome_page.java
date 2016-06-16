package com;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaiduHome_page {

    public WebDriver driver;

    public BaiduHome_page(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //百度logo
    @FindBy(xpath="//div[@id='lg']/img")
    public WebElement ElementBaiduLogo;

    //输入框
    @FindBy(id="kw")
    public WebElement ElementBaiduInput;

    //按钮 查询一下
    @FindBy(id="su")
    public WebElement ElementSubmit;


    //获取当前页面面包屑信息 预约订单
    public String getPageTitle(){
        return driver.getTitle();
    }

    // 输入查询内容，并点击查询按钮
    public void enterSearch(String searchText){
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.elementToBeClickable(ElementBaiduInput));
        ElementBaiduInput.clear();
        ElementBaiduInput.sendKeys(searchText);
        ElementSubmit.click();
    }

    // 输入查询内容，并点击查询按钮
    public void submit(){
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.elementToBeClickable(ElementSubmit));
        ElementSubmit.click();
    }
}
