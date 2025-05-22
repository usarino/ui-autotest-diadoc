package ru.usarino.diadoc.page;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
    @FindBy(css = "label[data-tid=Input__root] input")
    public SelenideElement input;

    @FindBy(css = "div.Certificate-module__certificate_EcaJ.Certificate-module__first_FggL")
    public SelenideElement sert;

    @FindBy (css = "a[data-product-name=diadoc]")
    public SelenideElement diadocEnter;

    public SelenideElement enter = Selenide.element(Selectors.byTagAndText("a", "Войти в сервис"));



}
