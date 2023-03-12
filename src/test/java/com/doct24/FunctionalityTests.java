package com.doct24;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;

public class FunctionalityTests {
    WebDriver driver;
    private final static String DOCT24_BASE_URL = "https://doct24-front-react.vercel.app/";
    private final static String tokenPatient = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZX" +
            "hwIjoxNjc4OTAyNzk4LCJpYXQiOjE2NzYzMTA3OTgsImp0aSI6ImRmMmI5MmIxNjZmMDRjZGJiZWYzYzBmYjA0NDQ0NzdkIiwidXNlcl" +
            "9pZCI6IjExY2IyNzRiLWJjNjgtNDQ0MS05ZDlmLTBiNjZhMDI5NTMxNCIsIndob19hbV9pIjoicGF0aWVudCJ9.brcbymtEfQ8qx89WC" +
            "qp22Rs1gaIzqgQC_MY-Wgo9HbA";

    private final static String tokenDoctor = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiw" +
            "iZXhwIjoxNjgwNzIxMzA5LCJpYXQiOjE2NzgxMjkzMDksImp0aSI6Ijg2ZmVlMWU1MTBjNzQ0MjE5MDUyNjk5MDdjYzgyNzBkIiwid" +
            "XNlcl9pZCI6IjExY2IyNzRiLWJjNjgtNDQ0MS05ZDlmLTBiNjZhMDI5NTMxNCIsIndob19hbV9pIjoiZG9jdG9yIn0.1Xdi-XUcsniK" +
            "1HCG9fqzbGE0AU9h8w0I79rD5rUwHmQ";

    public void putToken(String tokenValue) {
        LocalStorage local = ((WebStorage)driver).getLocalStorage();
        local.setItem("token", tokenValue);
        driver.navigate().refresh();
    }

    @BeforeAll
    static void registerDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.get(DOCT24_BASE_URL);
    }

    @Test
    @DisplayName("Проверка кнопок навбара")
    void navBarButtonsTest() {
        new NavBar(driver)
                .clickContactsButtonInHeader()
                .checkUrl("https://doct24-front-react.vercel.app/contacts")
                .clickAboutUsButtonInHeaderInHeader()
                .checkUrl("https://doct24-front-react.vercel.app/about")
                .clickOurDoctorsButtonInHeader()
                .checkUrl("https://doct24-front-react.vercel.app/doctors")
                .clickLogoButtonInHeader()
                .checkUrl("https://doct24-front-react.vercel.app/");
    }

    @Test
    @DisplayName("Проверка конпок врача и пациента")
    void lkButtonsTest() {
        putToken(tokenPatient);
        new NavBar(driver)
                .clickIpatientButtonInHeader()
                .checkUrl("https://doct24-front-react.vercel.app/lkpatient");
            driver.navigate().back();
            new NavBar(driver)
                .clickLogoButtonInHeader()
                .clickIdoctorButtonInHeader()
                .checkUrl("https://doct24-front-react.vercel.app/doctor-profile");
    }

    @ParameterizedTest
    @DisplayName("Проверка саджестов поиска врачей")
    @ValueSource(strings = "терапевт")
    void searchDoctorsTest(String searchRequest) {
        new NavBar(driver)
                .clickSearchButtonInHeader()
                .putSearchRequestAndCheckSuggestions(searchRequest);
    }

    @Test
    @DisplayName("Проверка смены даты рождения пациента")
    void changeBirthdayAsPatientTest() {
        putToken(tokenPatient);
        new NavBar(driver)
                .clickIpatientButtonInHeader()
                .clickChangeDataButton()
                .changeBirthdayDateInProfileAndCheckResult("16","июнь","1997");
    }

    @Test
    @DisplayName("Проверка смены имени пациента")
    void changeNameAsPatientTest() {
        putToken(tokenPatient);
        new NavBar(driver)
                .clickIpatientButtonInHeader()
                .clickChangeDataButton()
                .changeNameInProfileAsPatient("Лебедева","Анна","Михайловна");
    }

    @Test
    @DisplayName("Проверка смены роста, веса и группы крови пациента")
    void changeWeightHeightAndBloodTypeAsPatientTest() {
        putToken(tokenPatient);
        new NavBar(driver)
                .clickIpatientButtonInHeader()
                .clickChangeDataButton()
                .changeDataAboutWeightHeightAndBloodType("167 см", "47 кг", "B (III) Rh-");
    }

    @ParameterizedTest
    @DisplayName("Проверка смены пола пациента")
    @ValueSource(strings = "female")
    void changeSexAsPatientTest(String sex) {
        putToken(tokenPatient);
        new NavBar(driver)
                .clickIpatientButtonInHeader()
                .clickChangeDataButton()
                .changeSexAsPatient(sex);
    }

    @ParameterizedTest
    @DisplayName("Проверка смены email")
    @ValueSource(strings = "test@com.ru")
    void changeEmailAsPatientTest(String email) {
        putToken(tokenPatient);
        new NavBar(driver)
                .clickIpatientButtonInHeader()
                .clickChangeDataButton()
                .changeEmail(email);
    }

    @ParameterizedTest
    @DisplayName("Проверка добавления врача в избранное")
    @ValueSource(strings = "Киселева")
    void addDoctorToFavoritesTest(String lastNameDoctor) {
        putToken(tokenPatient);
        new NavBar(driver)
                .clickSearchButtonInHeader()
                .putSearchRequestAndClickOnMathcedDoctor(lastNameDoctor)
                .clickLikeButton();
        new NavBar(driver)
                .clickIpatientButtonInHeader()
                .clickFavoriteDoctors()
                .checkNewFavoriteDoctor(lastNameDoctor);
    }

    @Test
    @DisplayName("Проверка покупки консультации")
    void buyConsultationTest() {
        putToken(tokenPatient);
        new NavBar(driver)
                .clickChooseDoctorButton()
                .chooseDoctorSpecialization("Аллерголог")
                .clickEnrollOnFirstDoctor()
                .chooseDayOfConsultation()
                .chooseTimeOfConsultation()
                .putSomeDescriptionAndSubmitConsultation("Проблема")
                .clickSubmitConsultationButton()
                .clickPayForConsultationButton()
                .clickUmoneyButton()
                .clickConfirmPaymentButton()
                .clickBackToSiteButton()
                .checkSuccessPopupAfterPayment();


    }


    @AfterEach
    void quitBrowser() {
        driver.quit();
    }

}
