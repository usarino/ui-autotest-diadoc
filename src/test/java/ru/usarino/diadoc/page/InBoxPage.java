package ru.usarino.diadoc.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

public class InBoxPage {
    @FindBy(css = "a[data-product-name=diadoc]")
    public SelenideElement diadoc;

    @FindBy(css = "div[data-tid=profile]")
    public SelenideElement checkOut;

    @FindBy(xpath = "//span[text()='Выйти']")
    public SelenideElement exit;

    public SelenideElement filter = Selenide.element(Selectors.byTagAndText("span", "Фильтры"));
    public SelenideElement status = Selenide.element(Selectors.byTagAndText("span", "Статусы"));
    public SelenideElement apply = Selenide.element(Selectors.byTagAndText("span", "Применить"));

    @FindBy (xpath = "//button//div[text()='Требуется подпись']")
    public SelenideElement select;

    @FindBy (css = "article[data-tid=singleLetter]")
    public ElementsCollection docs;

    @FindBy(css = "label[data-tid=selectAllDocuments]")
    public SelenideElement selectAll;

    @FindBy (css = "div[data-tid=batchSignButton]")
    public SelenideElement buttonSig;

    public SelenideElement signatory = Selenide.element(Selectors.byTagAndText("div","Подписант"));
    public ElementsCollection setToday = Selenide.elements(Selectors.byTagAndText("span", "сегодня"));

    @FindBy (css = "button[type=submit]")
    public SelenideElement sinatureButt;

    @FindBy (css = "span[data-tid=close-button] button")
    public SelenideElement cancelButton;

    @FindBy (css="div.b-lightbox-wrap span[data-tid=close-button]")
    public SelenideElement closeDocSign;
    @FindBy (css="div[data-tid=nanoboxCross]")
    public SelenideElement closeNeedPay;

    public SelenideElement reset = Selenide.element(Selectors.byTagAndText("span", "Сбросить"));
}
