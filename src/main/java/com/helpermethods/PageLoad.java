package com.helpermethods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageLoad {
    public static boolean myElementIsClickable (WebDriver driver, By by) {
        try
        {
            new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(by));
        }
        catch (WebDriverException ex)
        {
            return false;
        }
        return true;
    }
}
