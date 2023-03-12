package com.doct24;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoctorProfileAsPatientPage extends BasePage{

    private final static Logger logger = LoggerFactory.getLogger("DoctorCardPage.pageObjects");

    public DoctorProfileAsPatientPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[@class='profileCard__box-like']")
    private WebElement likeButton;

    @FindBy(xpath = "//div[@class='calendar__days_cell calendar__days_receipts']")
    private WebElement someFreeDayForConsultation;

    @FindBy(xpath = "//div[@class='schedule__time']")
    private WebElement someFreeTimeForConsultation;

    @FindBy(xpath = "//textarea")
    private WebElement descriptionAreaForConsultation;

    @FindBy(xpath = "//button[text()='Записаться']")
    private WebElement submitConsultationWithDescriptionButton;

    @FindBy(xpath = "//button[text()='Подтвердить']")
    private WebElement submitConsulatationInPopup;

    @FindBy(xpath = "//button[text()='Оплатить']")
    private WebElement payForConsulatitonButton;

    public DoctorProfileAsPatientPage clickLikeButton() {
        webDriverWait.until(ExpectedConditions.visibilityOf(likeButton));
        likeButton.click();
        logger.info("Клик по сердцу в профиле врача");
        return this;
    }

    public DoctorProfileAsPatientPage chooseDayOfConsultation() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(someFreeDayForConsultation));
        someFreeDayForConsultation.click();
        logger.info("Клик по свободному дню ");
        return this;
    }

    public DoctorProfileAsPatientPage chooseTimeOfConsultation() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(someFreeTimeForConsultation));
        someFreeTimeForConsultation.click();
        logger.info("Клик по свободному времени ");
        return this;
    }

    public DoctorProfileAsPatientPage putSomeDescriptionAndSubmitConsultation(String description) {
        webDriverWait.until(ExpectedConditions.visibilityOf(descriptionAreaForConsultation));
        descriptionAreaForConsultation.sendKeys(description);
        logger.info("Ввод описания проблемы ");
        webDriverWait.until(ExpectedConditions.elementToBeClickable(submitConsultationWithDescriptionButton));
        submitConsultationWithDescriptionButton.click();
        logger.info("Клик по кнопке Записаться возле описания ");
        return this;
    }

    public DoctorProfileAsPatientPage clickSubmitConsultationButton() {
        webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy
                (By.xpath("//div[@class='sc-lbxAil eihbBV']")));
        submitConsulatationInPopup.click();
        logger.info("Клик по кнопке Записаться в попапе");
        return this;
    }

    public PaymentPage clickPayForConsultationButton() {
        webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy
                (By.xpath("//div[@class='sc-lbxAil eihbBV']")));
        payForConsulatitonButton.click();
        logger.info("Клик по кнопке Оплатить");
        return new PaymentPage(driver);
    }
}
