package com.ecmoho.selenium;

import com.ecmoho.common.util.Loggers;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 父抽象类
 */
public abstract class SeleniumSpider {

    /**
     * 获取WebDriver
     * @return
     */
    public final WebDriver getWebDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("web.root") + "/chromedriver.exe");
        //System.setProperty("javax.xml.parsers.DocumentBuilderFactory","com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
        WebDriver webDriver = new ChromeDriver();
//		webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return webDriver;
    }

    /**
     * 添加日志
     *
     * @param webDriver
     * @param bean
     * @param times
     */
    public void loginPage(WebDriver webDriver, SeleniumBean bean, int times){
        Loggers.seleniumSpiderLogger.info("{} {}th attempt landing", bean.getShopCode(), times);
        loginPage(webDriver, bean);
    }

    /**
     * 定义抽象方法（子类实现页面登录细节，加载请求，操作元素）
     * @param webDriver
     * @param bean
     */
    public abstract void loginPage(WebDriver webDriver, SeleniumBean bean);

    public final String getCookie(SeleniumBean bean) {
        WebDriver webDriver = getWebDriver();
        String cookie = login(webDriver, bean);
        webDriver.quit();
        return cookie;
    }

    /**
     * 尝试登录，获取cookie
     * @param webDriver
     * @param bean
     * @return
     */
    public final String login(WebDriver webDriver, SeleniumBean bean) {
        String login_url = bean.getLogin_url();
        String red_url = bean.getRed_url();
        //定义cookie字符串cookieStr
        String cookieStr = "";
        //尝试次数
        int times = 2;
        int i = 1;
        try {
            //尝试登录
            loginPage(webDriver, bean, i);
            //尝试次数，失败之后回到登录页面重新登录
            while (i < times) {
                Thread.sleep(500L);
                if (!webDriver.getCurrentUrl().startsWith(login_url)) {
                    webDriver.get(red_url);
                    Thread.sleep(4000L);
                    //webDriver.get(red_url);
                    //Thread.sleep(2000L);
                    //获取cookie
                    Set<Cookie> cookies = webDriver.manage().getCookies();
                    for (Cookie cookie : cookies) {
                        cookieStr += cookie.getName() + "=" + cookie.getValue() + "; ";
                    }
                    break;
                } else {
                    //登录失败重试
                    if (i <= times) {
                        i++;
                        loginPage(webDriver, bean, i);
                    } else {
                        break;
                    }
                }
            }
        } catch (InterruptedException e) {
            Loggers.seleniumSpiderLogger.error("{} login failed, {},{}",
                    bean.getShopCode(), bean.toString(), e.getMessage());
            return "";
        }
        //浏览器退出
        webDriver.quit();
//        webDriver.close();
        return cookieStr;
    }

    /**
     * 判断元素是否存在
     * @param webElement
     * @return
     */
    public static boolean doesWebElementExist(WebElement webElement) {
        try {
            return webElement.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean doesWebElementExistBySelector(WebDriver driver, By selector) {
        try {
            driver.findElement(selector);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
