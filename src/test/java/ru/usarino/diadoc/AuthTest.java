package ru.usarino.diadoc;

import com.codeborne.selenide.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.usarino.diadoc.page.InBoxPage;
import ru.usarino.diadoc.page.LoginPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;

@Epic("API Tests")
@Feature("User Management")
public class AuthTest {
    static {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
                .includeSelenideSteps(true)
        );
    }

    @Test
    @Disabled
    void testAuth() {

        //String userDataDir = "C:\\Users\\Zm500\\AppData\\Local\\Google\\Chrome";
        String userDataDir = "C:\\wd\\User Data";
        String profileName = "Default"; // Use "Default" for the default profile, or specify your custom profile folder

        // Set up ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=" + userDataDir);
        options.addArguments("--profile-directory=" + profileName);

        // Apply ChromeOptions to Selenide
        Configuration.pageLoadTimeout = 30_000;
        Configuration.timeout = 10000;
        Configuration.browser = "chrome";
        Configuration.browserCapabilities = options;
        Configuration.pageLoadStrategy = "none";

        // открываем сайт авторизации портала контур
       open("https://auth.kontur.ru/");

        LoginPage loginPage = Selenide.page(LoginPage.class);
        InBoxPage inBoxPage = Selenide.page(InBoxPage.class);

        //вводим в поле поиск инн клиента и кликаем по найденному сертификату(серт должен быть ок и установлен на пк)
        Selenide.sleep(3_000);

        loginPage.input.setValue("inn");
        Selenide.sleep(3_000);
        loginPage.sert.click();
        Selenide.sleep(3_000);

        // успешно авторизовались и переходим в сервис диадок

        SelenideElement enter = loginPage.enter;
        Selenide.actions().moveToElement(enter).click(enter).perform();


        //находим вход в сервис, его вход невидимый и открывается на второй вкладке
        SelenideElement element = loginPage.diadocEnter;
        Selenide.actions().moveToElement(element).click(element).perform();
        //переходим на 2ю вкладку
        Selenide.sleep(3_000);
        Selenide.switchTo().window(1);
        //находим кнопку фильтры, кликаем по ней
        inBoxPage.filter.click();
        //находим кнопку статусы, кликаем по ней для открытия пунктов статусов
        inBoxPage.status.click();
        // отмечаем галочкой статус, кликаем по нему и убираем всплывающее меню
        inBoxPage.select.click();
        inBoxPage.status.click();
        inBoxPage.select
                .shouldBe(Condition.disappear);
        //принимаем фильтр
        inBoxPage.apply.click();

        ElementsCollection docs = inBoxPage.docs;
        try {
            docs.shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1), Duration.ofSeconds(3));
        } catch (Throwable err) {
            System.out.println("ДОКУМЕНТЫ ДЛЯ ПОДПИСИ НЕ ОПРЕДЕЛЕНЫ / НЕТ ДОКУМЕНТОВ ДЛЯ ПОДПИСИ");

            //Выходим (де-авторизируемся)
            inBoxPage.checkOut.click();
            SelenideElement exit = inBoxPage.exit;
            Selenide.actions().moveToElement(exit).click(exit).perform();

            return;
        }

        //ставим галочку на все документы
        inBoxPage.selectAll.click();
        //Нажимаем кнопку Подписать
        inBoxPage.buttonSig.click();

        //Подождать появление подписанта
        inBoxPage.signatory.shouldBe(Condition.visible, Duration.ofSeconds(30));
        //Ставим дату сегодня
        ElementsCollection  setToday = inBoxPage.setToday;
        try {
            setToday.shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1), Duration.ofSeconds(10));
            for (SelenideElement today : setToday) {
                today.scrollIntoCenter();
                Selenide.actions().moveToElement(today).click(today).perform();

            }
        } catch (Throwable err){
            System.out.println("Нет ввода поля сегодня");
        }

        // нажимаем кнопку подписать
        inBoxPage.sinatureButt.click();
        //сбрасываем фильтр
        inBoxPage.reset.click();
        //Выходим из диадока
        inBoxPage.checkOut.click();
        SelenideElement exit = inBoxPage.exit;
        Selenide.actions().moveToElement(exit).click(exit).perform();

    }



    @Test
    @Disabled
        @DisplayName("Проверка создания пользователя")
        @Story("Создание нового пользователя")
        @Description("Этот тест проверяет возможность создания пользователя через API")
        @Severity(SeverityLevel.CRITICAL)
        void testAuth2() {

            DiadocSteps.options();
            DiadocSteps.login("inn");
            DiadocSteps.signEndExit();
        }

    @ParameterizedTest

        @DisplayName("Проверка создания пользователя")
        @Story("Создание нового пользователя")
        @Description("Этот тест проверяет возможность создания пользователя через API")
        @Severity(SeverityLevel.CRITICAL)
    @CsvSource(value = {
            "inn1", //Имя пользователя1
            "inn2", //Имя пользователя2
            "inn3", //Имя пользователя3
            "inn4", //Имя пользователя4
            "inn5", //Имя пользователя5
            "inn6", //Имя пользователя6
            "inn7", //Имя пользователя7
            "inn8", //Имя пользователя8
            "inn9", //Имя пользователя9
            "770000000000" // Имя пользователя

    })
    void testAuth3(String inn) {
        DiadocSteps.options();
        DiadocSteps.login(inn);
        DiadocSteps.signEndExit();
    }


    @Test
    @Disabled
    void testCrpt(){
      //  DiadocSteps.optionsYa();
        open("https://markirovka.crpt.ru/login-kep");
        Selenide.element(Selectors.byTagAndText("div", "Войти с электронной подписью")).click();
        Selenide.element("input[placeholder=Поиск]").setValue("401500168879");
        Selenide.element("div.css-zf8fdi").click();
        Selenide.sleep(3000);

    }


    @AfterEach
    void after() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.closeWebDriver();
    }

    }
