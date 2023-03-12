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

public class PatientLkPage extends BasePage {

    private final static Logger logger = LoggerFactory.getLogger("PatientLkPage.pageObjects");

    public PatientLkPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[.='Изменить данные']")
    private WebElement changeDataInProfileButton;

    @FindBy(xpath = "//input[@placeholder='Фамилия']")
    private WebElement lastNameField;

    @FindBy(id = "firstName")
    private WebElement firstNameField;

    @FindBy(id = "patronymic")
    private WebElement patronymicNameField;

    @FindBy(id = "female")
    private WebElement femaleGenderButton;

    @FindBy(id = "male")
    private WebElement maleGenderButton;

    @FindBy(xpath = "//input[@placeholder='ABC@mail.ru']")
    private WebElement emailField;

    @FindBy(xpath = "//div[@class=' css-1fos893-singleValue']")
    private WebElement dayOfBirthButton;

    @FindBy(xpath = "//div[@class=' css-1y2twnv-singleValue']")
    private WebElement monthOfBirthButton;

    @FindBy(xpath = "//div[@class=' css-da8hyd-singleValue']")
    private WebElement yearOfBirthButton;

    @FindBy(xpath = "//button[.='Сохранить' ]")
    private WebElement saveChangesButton;

    @FindBy(xpath = "//section//h3[@class='patient__card_main_header']")
    private WebElement patientNameElement;

    @FindBy(xpath = "//div[contains(text(),'см')]")
    private WebElement heightButton;

    @FindBy(xpath = "//div[contains(text(),'кг')]")
    private WebElement weightButton;

    @FindBy(xpath = "//div[contains(text(),'Rh')]")
    private WebElement bloodTypeButton;

    @FindBy(xpath = "//div[@class='sc-bczRLJ kjoOgt patient__card']//p[contains(text(),'Мужчина')]")
    private WebElement maleElementInProfile;

    @FindBy(xpath = "//div[@class='sc-bczRLJ kjoOgt patient__card']//p[contains(text(),'Женщина')]")
    private WebElement femaleElementInProfile;

    @FindBy(xpath = "//a[@href='/lkpatient/favdoctors']")
    private WebElement favoritesDoctors;

    @FindBy(xpath = "//img[@alt='like']")
    private WebElement likeButtonOfDoctorInFavoritesDoctor;

    @FindBy(xpath = "//a[text()='Вернуться к консультациям']")
    private WebElement backToConsultationInPopupButton;

    private final String listBoxLocator = "//div[@class=' css-1bde012-option']"; //локатор для смены даты рождения

    private void sendKeysWithDelays(WebElement webElement, String value) {
        for(int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            String s = new StringBuilder().append(c).toString();
            webElement.sendKeys(s);
        }
    }

    private List<WebElement> createAndFilterListOfListboxElements(String contains) {
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(listBoxLocator)));
        List<WebElement> ListboxElements = driver.findElements(By.xpath(listBoxLocator));
        ListboxElements.stream().filter(p -> p.getText().contains(contains)).findFirst().get().click();
        logger.info("Поиск элемента в списке listbox " + contains);
        return ListboxElements;
    }

    public PatientLkPage clickChangeDataButton() {
        webDriverWait.until(ExpectedConditions.visibilityOf(changeDataInProfileButton));
        changeDataInProfileButton.click();
        logger.info("Клик по кнопке Изменить данные");
        return this;
    }

    public PatientLkPage clickSaveChangesButton() {
        webDriverWait.until(ExpectedConditions.visibilityOf(saveChangesButton));
        saveChangesButton.click();
        logger.info("Клик по кнопке Сохранить");
        return this;
    }

    public PatientLkPage changeBirthdayDateInProfileAndCheckResult(String day, String month, String year){
        webDriverWait.until(ExpectedConditions.visibilityOf(dayOfBirthButton));
        dayOfBirthButton.click();
        logger.info("Клик на день рождения");
        try {
            createAndFilterListOfListboxElements(day);
        } catch (Exception e) {
            logger.info("Заданный день совпадает с уже записанным в аккаунт " + day);
        }
        webDriverWait.until(ExpectedConditions.visibilityOf(monthOfBirthButton));
        monthOfBirthButton.click();
        logger.info("Клик на месяц рождения");
        try {
            createAndFilterListOfListboxElements(month);
        } catch (Exception e) {
            logger.info("Заданный месяц совпадает с уже записанным в аккаунт " + month);}
        webDriverWait.until(ExpectedConditions.visibilityOf(yearOfBirthButton));
        yearOfBirthButton.click();
        logger.info("Клик на год рождения");
        try {
            createAndFilterListOfListboxElements(year);
        } catch (Exception e) {
            logger.info("Заданный месяц совпадает с уже записанным в аккаунт " + year);}
        clickSaveChangesButton();
        clickChangeDataButton();
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy
                (By.xpath("//div[@class='sc-jWquRx dAOLXe']")));
        Assertions.assertTrue(driver.findElement(By.xpath("//div[text()='" + day +"']")).isDisplayed());
        logger.info("Проверка дня выполнена");
        Assertions.assertTrue(driver.findElement(By.xpath("//div[text()='" + month +"']")).isDisplayed());
        logger.info("Проверка месяца выполнена");
        Assertions.assertTrue(driver.findElement(By.xpath("//div[text()='" + year +"']")).isDisplayed());
        logger.info("Проверка года выполнена");
        return this;
    }

    public PatientLkPage changeNameInProfileAsPatient(String lastName, String firstName, String patronymic) {
        webDriverWait.until(ExpectedConditions.visibilityOf(lastNameField));
        lastNameField.clear();
        logger.info("Очистка поля Фамилия");
        sendKeysWithDelays(lastNameField, lastName);
        logger.info("Заполнение поля Фамилия данными = " + lastName);
        firstNameField.clear();
        logger.info("Очистка поля Имя");
        sendKeysWithDelays(firstNameField, firstName);
        logger.info("Заполнение поля Имя данными = " + firstName);
        patronymicNameField.clear();
        logger.info("Очистка поля Отчество");
        sendKeysWithDelays(patronymicNameField, patronymic);
        logger.info("Заполнение поля Отчество данными = " + patronymic);
        clickSaveChangesButton();
        webDriverWait.until(ExpectedConditions.textToBePresentInElement
                (patientNameElement, lastName + " " + firstName + " " + patronymic));
        Assertions.assertEquals(lastName + " " + firstName + " " + patronymic,patientNameElement.getText());
        logger.info("Проверка смены имени пройдена");
        return this;
    }

    public PatientLkPage changeDataAboutWeightHeightAndBloodType(String height, String weight, String bloodType) {
        webDriverWait.until(ExpectedConditions.visibilityOf(heightButton));
        heightButton.click();
        logger.info("Клик на кнопку Рост");
        try { createAndFilterListOfListboxElements(height); }
        catch (Exception e){
            logger.info("Заданный рост совпадает с уже записанным в аккаунт = " + height);
        }
        webDriverWait.until(ExpectedConditions.visibilityOf(weightButton));
        weightButton.click();
        logger.info("Клик по кнопке Вес");
        try { createAndFilterListOfListboxElements(weight); }
        catch (Exception e){
            logger.info("Заданный вес совпадает с уже записанным в аккаунт = " + weight);
        }
        webDriverWait.until(ExpectedConditions.visibilityOf(bloodTypeButton));
        bloodTypeButton.click();
        logger.info("Клик на кнопку Группа крови");
        try { createAndFilterListOfListboxElements(bloodType); }
        catch (Exception e){
            logger.info("Заданная группа крови совпадает с уже записанным в аккаунт = " + bloodType);
        }
        clickSaveChangesButton();
        clickChangeDataButton();
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy
                (By.xpath("//div[@class='sc-PJClH fVoTOd']")));
        Assertions.assertTrue(driver.findElement(By.xpath("//div[text()='" + weight +"']")).isDisplayed());
        logger.info("Проверка роста выполнена");
        webDriverWait.until(ExpectedConditions.visibilityOf(heightButton));
        Assertions.assertTrue(driver.findElement(By.xpath("//div[text()='" + height +"']")).isDisplayed());
        logger.info("Проверка веса выполнена");
        webDriverWait.until(ExpectedConditions.visibilityOf(bloodTypeButton));
        Assertions.assertTrue(driver.findElement(By.xpath("//div[text()='" + bloodType +"']")).isDisplayed());
        logger.info("Проверка группы крови выполнена");
        return this;
    }

    public PatientLkPage changeSexAsPatient(String sex) {
        if(sex.equals("male")) {
            webDriverWait.until(ExpectedConditions.visibilityOf(maleGenderButton));
            maleGenderButton.click();
            logger.info("Клик по кнопке Мужской");
            clickSaveChangesButton();
            webDriverWait.until(ExpectedConditions.visibilityOf(maleElementInProfile));
            Assertions.assertTrue(maleElementInProfile.isDisplayed());
            logger.info("Проверка смены пола пройдена");
        } else if (sex.equals("female")) {
            webDriverWait.until(ExpectedConditions.visibilityOf(femaleGenderButton));
            femaleGenderButton.click();
            logger.info("Клик по кнопке Женский");
            clickSaveChangesButton();
            webDriverWait.until(ExpectedConditions.visibilityOf(femaleElementInProfile));
            Assertions.assertTrue(femaleElementInProfile.isDisplayed());
            logger.info("Проверка смены пола пройдена");
        }
        return this;
    }

    public PatientLkPage changeEmail(String email) {
        webDriverWait.until(ExpectedConditions.visibilityOf(emailField));
        actions.click(emailField).perform();
        emailField.clear();
        logger.info("Очистка поля Email");
        sendKeysWithDelays(emailField, email);
        logger.info("Заполняем поле Email данными = " + email );
        clickSaveChangesButton();
        clickChangeDataButton();
        webDriverWait.until(ExpectedConditions.visibilityOf(emailField));
        Assertions.assertTrue(driver.findElement(By.xpath("//input[@value='" + email + "']")).isDisplayed());
        return this;
    }

    public PatientLkPage clickFavoriteDoctors() {
        webDriverWait.until(ExpectedConditions.visibilityOf(favoritesDoctors));
        favoritesDoctors.click();
        logger.info("Клик по кнопке Избранные врачи");
        return this;
    }

    public PatientLkPage checkNewFavoriteDoctor(String nameDoctor) {
        webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement
                (By.xpath("//div[contains(text(),'" + nameDoctor + "')]"))));
        Assertions.assertTrue(driver.findElement
                (By.xpath("//div[contains(text(),'" + nameDoctor + "')]")).isDisplayed());
        logger.info("Ранее добавленный в избранное доктор отображается");
        webDriverWait.until(ExpectedConditions.visibilityOf(likeButtonOfDoctorInFavoritesDoctor));
        likeButtonOfDoctorInFavoritesDoctor.click();
        logger.info("Клик сердца на краточке доктора, для повторных запусков теста");
        return this;
    }

    public PatientLkPage checkSuccessPopupAfterPayment() {
        webDriverWait.until(ExpectedConditions.urlContains("doct24"));
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy
                (By.xpath("//section[@class='sc-lcoedz kgFISY']")));
        Assertions.assertTrue(driver.findElement
                (By.xpath("//p[contains(text(),'успешно')]")).isDisplayed());
        return this;
    }
    public PatientLkPage checkUrl(String expectedUrl) {
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
        logger.info("URL корректен");
        return this;
    }
}
