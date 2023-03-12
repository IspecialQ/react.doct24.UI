package com.doct24;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OurDoctorsPage extends BasePage{

    private final static Logger logger = LoggerFactory.getLogger("OurDoctorsPage.pageObjects");
    public OurDoctorsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//label[@for='Акушер']")
    private WebElement specializationWebElementForWait;

    @FindBy(xpath = "//a[contains(@href,'/doctors/')]")
    private WebElement enrollButton;

    private WebElement getSpecializationWebElement(String specialization) {
        webDriverWait.until(ExpectedConditions.visibilityOf(specializationWebElementForWait));
        WebElement specializationButton = driver.findElement(By.xpath("//label[@for='" + specialization + "']"));
        logger.info("Поиск элемента");
        return specializationButton;
    }

    public OurDoctorsPage chooseDoctorSpecialization(String specialization) {
        getSpecializationWebElement(specialization).click();
        logger.info("Клик по чекбоксу выбранной специализации");
        return this;
    }

    public DoctorProfileAsPatientPage clickEnrollOnFirstDoctor() {
        try {
            webDriverWait.until(ExpectedConditions.stalenessOf
                    (driver.findElement(By.xpath("//div[@class='sc-gKXOVf cqwwj']"))));
            enrollButton.click();
            logger.info("Клик по кнопке Записаться у первого врача");
        } catch (Exception e) {
            logger.error("Нет врачей указанной специальности");
            return new DoctorProfileAsPatientPage(driver);
        }
        return new DoctorProfileAsPatientPage(driver);
    }
}
