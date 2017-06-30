package com.cucumber.operationSteps;


import com.BaiduHome_page;
import com.cucumber.config.ConfigManager;
import com.cucumber.utl.SharedDriver;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.util.concurrent.TimeUnit;

public class BaiduSearchStepfs {

    private final WebDriver driver;
    private final ConfigManager config;
    private BaiduHome_page baiduHome_page;
    private static String baseUrl;

    public BaiduSearchStepfs(SharedDriver driver, ConfigManager config) {
        this.driver = driver;
        this.config = config;
    }

    @Given("^Go to baidu home$")
    public void go_to_baidu_home() throws Exception {
        baseUrl = this.config.get("base_path");
        baiduHome_page = new BaiduHome_page(driver,baseUrl);
        baiduHome_page.get();
        //this.driver.navigate().to(baseUrl);

    }

    @When("^I find baidu logo")
    public WebElement i_find_baidu_logo() {
        WebElement element = this.baiduHome_page.getBaiduLogo();
        return element;
    }

    @And("^Type the search text \"([^\"]*)\"$")
    public void type_the_search_text(String searchText) throws Throwable {
        this.baiduHome_page.enterSearch(searchText);
    }

    @And("^Click the submit$")
    public void click_the_submit() {
        this.baiduHome_page.submit();
    }

    @Then("^Wait the query result")
    public void wait_the_query_result() throws InterruptedException {
        Thread.sleep(10000);    //后面可以用显示等待或者隐示等待来优化代码
        Assert.assertEquals("selenium_百度搜索", this.baiduHome_page.getPageTitle());
    }
}
