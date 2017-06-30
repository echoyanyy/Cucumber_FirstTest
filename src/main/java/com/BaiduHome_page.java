package com;


import com.helpermethods.PageLoad;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.util.concurrent.TimeUnit;

public class BaiduHome_page extends LoadableComponent<BaiduHome_page> {

    public WebDriver driver;

    public BaiduHome_page(WebDriver driver,String Url){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.get(Url);
    }

    @Override
    protected void load() {
    }

    @Override
    protected void isLoaded() throws Error {

        System.out.println("=====判断BaiduHome_page是否加载完成=====");

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        wait.until((new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driverObject) {
                System.out.println("Waiting BaiduHome_page Dom loading complete\n ");
                return (Boolean) ((JavascriptExecutor) driverObject).executeScript("return document.readyState === 'complete'");
            }
        }));

        if(!PageLoad.myElementIsClickable(this.driver, By.id("kw"))) {
            throw new Error("BaiduHome_page was not successfully loaded");
        }
        System.out.println("*****BaiduHome_page is loading complete*****");
    }

    //百度logo
    @FindBy(xpath="//div[@id='lg']/img")
    private WebElement ElementBaiduLogo;

    //输入框
    @FindBy(id="kw")
    private WebElement ElementBaiduInput;

    //按钮 查询一下
    @FindBy(id="su")
    private WebElement ElementSubmit;


    public WebElement getBaiduLogo(){
        return ElementBaiduLogo;
    }

    //获取当前页面title
    public String getPageTitle(){
        return driver.getTitle();
    }

    // 输入查询内容，并点击查询按钮
    public void enterSearch(String searchText){
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.elementToBeClickable(ElementBaiduInput));
        ElementBaiduInput.clear();
        ElementBaiduInput.sendKeys(searchText);
    }

    // 输入查询内容，并点击查询按钮
    public void submit(){
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.elementToBeClickable(ElementSubmit));
        ElementSubmit.click();
    }
}
