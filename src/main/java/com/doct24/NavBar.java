package com.doct24;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NavBar extends BasePage{

    private static final Logger logger = LoggerFactory.getLogger("NavBar.pageObjects");
    public NavBar(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//header//a[@href='/']")
    private WebElement logoButtonInHeader;

    @FindBy(xpath = "//header//a[@href='/about']")
    private WebElement aboutUsButtonInHeader;

    @FindBy(xpath = "//header//a[@href='/doctors']")
    private WebElement ourDoctorsButtonInHeader;

    @FindBy(xpath = "//header//a[@href='/contacts']")
    private WebElement contactsButtonInHeader;

    @FindBy(xpath = "//button[.='Я врач']")
    private WebElement doctorButtonInHeader;

    @FindBy(xpath = "//button[.='Я пациент']")
    private WebElement patientButtonInHeader;

    @FindBy(xpath = "//button[.='Поиск']")
    private WebElement searchButton;

    @FindBy(xpath = "//input[@placeholder='Поиск']")
    private WebElement searchField;

    @FindBy(xpath = "//a[text()='Выбрать врача']")
    private WebElement chooseDoctorButton;



    public NavBar clickContactsButtonInHeader() {
        webDriverWait.until(ExpectedConditions.visibilityOf(contactsButtonInHeader));
        contactsButtonInHeader.click();
        logger.info("Клик кнопки Контакты в хедере");
        return this;
    }

    public NavBar clickOurDoctorsButtonInHeader() {
        webDriverWait.until(ExpectedConditions.visibilityOf(ourDoctorsButtonInHeader));
        ourDoctorsButtonInHeader.click();
        logger.info("Клик кнопки Наши врачи в хедере");
        return this;
    }

    public NavBar clickAboutUsButtonInHeaderInHeader() {
        webDriverWait.until(ExpectedConditions.visibilityOf(aboutUsButtonInHeader));
        aboutUsButtonInHeader.click();
        logger.info("Клик кнопки О нас в хедере");
        return this;
    }

    public NavBar clickLogoButtonInHeader() {
        webDriverWait.until(ExpectedConditions.visibilityOf(logoButtonInHeader));
        logoButtonInHeader.click();
        logger.info("Клик Лого в хедере");
        logger.info(logoButtonInHeader.toString());
        return this;
    }

    public NavBar checkUrl(String expectedUrl) {
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
        logger.info("URL корректен");
        return this;
    }

    public PatientLkPage clickIpatientButtonInHeader() {
        webDriverWait.until(ExpectedConditions.visibilityOf(patientButtonInHeader));
        patientButtonInHeader.click();
        logger.info("Клик кнопки Я пациент в хедере");
        return new PatientLkPage(driver);
    }

    public NavBar clickIdoctorButtonInHeader() {
        webDriverWait.until(ExpectedConditions.visibilityOf(doctorButtonInHeader));
        doctorButtonInHeader.click();
        logger.info("Клик кнопки Я врач в хедере");
        return this;
    }

    public NavBar clickSearchButtonInHeader() {
        webDriverWait.until(ExpectedConditions.visibilityOf(searchButton));
        searchButton.click();
        logger.info("Клик по кноке поиска");
        return this;
    }

    public NavBar putSearchRequestAndCheckSuggestions(String searchRequest) {
        putSearchRequestInRequestField(searchRequest);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
                ("//div[@class='doctor_spec']//span[contains(text(),'" + searchRequest + "')]")));
        List<WebElement> searchResults = driver.findElements(By.xpath
                ("//div[@class='doctor_spec']//span[contains(text(),'" + searchRequest + "')]"));
        Assertions.assertNotEquals(0,searchResults.size());
        logger.info("Проверка саджестов посика пройдена");
        return this;
    }

    public NavBar putSearchRequestInRequestField(String searchRequest) {
        webDriverWait.until(ExpectedConditions.visibilityOf(searchField));
        searchField.sendKeys(searchRequest);
        logger.info("Ввод запроса в поле поиска");
        return this;
    }

    public DoctorProfileAsPatientPage putSearchRequestAndClickOnMathcedDoctor(String searchRequest) {
        putSearchRequestInRequestField(searchRequest);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//div[contains(text(),'" + searchRequest + "')]")));
        driver.findElement(By.xpath("//div[contains(text(),'" + searchRequest + "')]")).click();
        logger.info("Клик по доктору из саджеста");
        return new DoctorProfileAsPatientPage(driver);
    }

    public OurDoctorsPage clickChooseDoctorButton() {
        webDriverWait.until(ExpectedConditions.visibilityOf(chooseDoctorButton));
        chooseDoctorButton.click();
        logger.info("Клик по кнопке Выбрать врача");
        return new OurDoctorsPage(driver);
    }
}
