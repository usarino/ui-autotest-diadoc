package ru.usarino.diadoc;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.usarino.diadoc.page.InBoxPage;
import ru.usarino.diadoc.page.LoginPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;

public class DiadocSteps {
    static boolean needSign = true;
    static LoginPage loginPage = Selenide.page(LoginPage.class);
    static InBoxPage inBoxPage = Selenide.page(InBoxPage.class);
@Step("Устанавливаю пользовательские настройки браузера/запустить браузер")
    public static void options(){
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
    }
@Step("Авторизация")
    public static void login(String inn){
        Selenide.open("https://auth.kontur.ru/");
        //вводим в поле поиск инн клиента и кликаем по найденному сертификату(серт должен быть ок и установлен на пк)
        Selenide.sleep(3_000);
        LoginPage loginPage = Selenide.page(LoginPage.class);
        InBoxPage inBoxPage = Selenide.page(InBoxPage.class);
        loginPage.input.setValue(inn);
        Selenide.sleep(3_000);
        loginPage.sert.click();
        Selenide.sleep(3_000);

        // успешно авторизовались и переходим в сервис диадок
        //Selenide.executeJavaScript("window.stop();");
        SelenideElement enter = loginPage.enter;
        Selenide.actions().moveToElement(enter).click(enter).perform();

       //находим вход в сервис, его вход невидимый и открывается на второй вкладке
        SelenideElement element = inBoxPage.diadoc;
        Selenide.actions().moveToElement(element).click(element).perform();
        //перескакиваем на 2ю вкладку
        Selenide.sleep(3_000);
        Selenide.switchTo().window(1);
    }
    @Step("Проверяем документы подписываем и выходим")
    public static void signEndExit(){
    inBoxPage.filter.click();
    System.out.println("включаю фильтр");
    //находим кнопку статусы, кликаем по ней для открытия пунктов статусов
    inBoxPage.status.click();
    System.out.println("выбираю статус требуется подпись");
    // отмечаем галочкой статус, кликаем по нему и убираем всплывающее меню
    inBoxPage.select.click();
    inBoxPage.status.click();
    inBoxPage.select
            .shouldBe(Condition.disappear);
    //принимаем фильтр
    inBoxPage.apply.click();
    System.out.println("Закрываю окно оплаты тарифа");
    if (inBoxPage.closeNeedPay.is(Condition.visible))
        inBoxPage.closeNeedPay.click();
    ElementsCollection docs = inBoxPage.docs;
    try {
        docs.shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1), Duration.ofSeconds(3));
    } catch (Throwable err) {
        System.out.println("ДОКУМЕНТЫ ДЛЯ ПОДПИСИ НЕ ОПРЕДЕЛЕНЫ / НЕТ ДОКУМЕНТОВ ДЛЯ ПОДПИСИ");

        //Выходим (де-авторизируемся)
       DiadocSteps.checkOut();

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
        setToday.shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1), Duration.ofSeconds(30));
        for (SelenideElement today : setToday) {
            today.scrollIntoCenter();
            Selenide.actions().moveToElement(today).click(today).perform();

        }
    } catch (Throwable err){
        System.out.println("Нет ввода поля сегодня");
    }
    System.out.println("Подписываю документы: "+ docs.size()+" шт");
    // нажимаем кнопку подписать
    if (needSign == true) {
        inBoxPage.sinatureButt.click();
        //закрываем всплывающее окно
        if (inBoxPage.closeDocSign.is(Condition.visible, Duration.ofSeconds(7)))
            inBoxPage.closeDocSign.click();
        //сбрасываем фильтр
       // inBoxPage.reset.click();
    } else{
        inBoxPage.cancelButton.is(Condition.visible, Duration.ofSeconds(7));
        inBoxPage.cancelButton.click();
        inBoxPage.cancelButton.shouldBe(Condition.disappear);
    }
    //Де-авторизация и выход
   DiadocSteps.checkOut();

}

    public static void checkOut(){
        System.out.println("Выходим.");
        inBoxPage.checkOut.is(Condition.visible, Duration.ofSeconds(10));
        inBoxPage.checkOut.click();
        SelenideElement exit = inBoxPage.exit;
        Selenide.actions().moveToElement(exit).click(exit).perform();
    }
}
