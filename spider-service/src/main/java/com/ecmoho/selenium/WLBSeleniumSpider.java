package com.ecmoho.selenium;

import com.ecmoho.common.util.Loggers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

/**
 * Created by meidejing on 2016/7/8.
 * 物流宝模拟登录
 */
@Component("WLBSeleniumSpider")
public class WLBSeleniumSpider extends SeleniumSpider {

    @Override
    public void loginPage(WebDriver webDriver, SeleniumBean bean) {

        String iframe_name = "J_loginIframe";
        String pcId = "J_Quick2Static";
        String login_name_id = "TPL_username_1";
        String password_id = "TPL_password_1";
        String login_button_id = "J_SubmitStatic";

        try {
            int i = 0;
            int times = 4;
            webDriver.get(bean.getLogin_url());
            Thread.sleep(3040L);
            while (i < times) {
                if (doesWebElementExistBySelector(webDriver, By.id(iframe_name))) {
                    webDriver.switchTo().frame(webDriver.findElement(By.id(iframe_name)));
                    WebElement pcElement = webDriver.findElement(By.id(pcId));
                    if (doesWebElementExist(pcElement)) {
                        pcElement.click();
                    }
                    WebElement userElement = webDriver.findElement(By.id(login_name_id));
                    userElement.clear();
                    userElement.sendKeys(bean.getLogin_name());
                    userElement.click();
                    Thread.sleep(1040L);
                    WebElement passElement = webDriver.findElement(By.id(password_id));
                    passElement.clear();
                    passElement.sendKeys(bean.getPassword());
                    passElement.click();
                    Thread.sleep(2000L);
                    WebElement moveElement = webDriver.findElement(By.id("nc_1_n1z"));
                    if (doesWebElementExist(moveElement)) {
                        Actions action = new Actions(webDriver);
                        // action.dragAndDrop(target,260);
                        action.click(webDriver.findElement(By.id("nc_1_n1z")));
                        action.dragAndDropBy(moveElement, 260, 0).build().perform();
                        Thread.sleep(3000L);
                    }
                    WebElement ensureElement = webDriver.findElement(By.id("nc_1__btn_2"));
                    if (!doesWebElementExist(ensureElement)) {
                        webDriver.findElement(By.id(login_button_id)).click();
                        Thread.sleep(10000L);
                    }
                    Thread.sleep(2000L);
                    webDriver.switchTo().defaultContent();
                    break;
                } else {
                    if (i <= times) {
                        i++;
                        webDriver.get(bean.getLogin_url());
                        Thread.sleep(10040L);
                    } else {
                        break;
                    }
                }
            }
        } catch (InterruptedException e) {
            webDriver.quit();
            Loggers.seleniumSpiderLogger.error("{} login WLB failed, {},{}",
                    bean.getShopCode(), bean.toString(), e.getMessage());
        }
    }
}
