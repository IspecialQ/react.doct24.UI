package com.doct24;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentPage extends BasePage {
    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    private static final Logger logger = LoggerFactory.getLogger("PaymentPage.pageObjects");

    @FindBy(xpath = "//div[text()='Ваш кошелёк']")
    private WebElement uMoneyButton;

    @FindBy(xpath = "//div[contains(@class,'ConfirmButton_')]")
    private WebElement confirmPaymentButton;

    @FindBy(xpath = "//a[contains(text(),'Вернуться')]")
    private WebElement backToSiteButton;

    public PaymentPage clickUmoneyButton() {
        webDriverWait.until(ExpectedConditions.urlContains("yoomoney.ru"));
        uMoneyButton.click();
        logger.info("Клик по кнопке оплаты Юмани");
        return this;
    }

    public PaymentPage clickConfirmPaymentButton() {
        webDriverWait.until(ExpectedConditions.visibilityOf(confirmPaymentButton));
        confirmPaymentButton.click();
        logger.info("Клик по кнопке Подтвердить");
        return this;
    }

    public PatientLkPage clickBackToSiteButton() {
        webDriverWait.until(ExpectedConditions.visibilityOf(backToSiteButton));
        backToSiteButton.click();
        logger.info("Клик по кнопке Вернуться на сайт");
        return new PatientLkPage(driver);
    }
}
