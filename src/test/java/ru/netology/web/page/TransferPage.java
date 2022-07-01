package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    private SelenideElement errorIcon = $("[data-test-id=error-notification]");

    public void transferMoney(String amount, String fromCardNumber){
        amountField.doubleClick();
//        amountField.sendKeys(Keys.BACK_SPACE);
        amountField.setValue(amount);
//        fromField.sendKeys(Keys.BACK_SPACE);
        fromField.setValue(fromCardNumber);
        transferButton.click();
    }

    public DashboardPage validTransaction (String amount, String fromCardNumber) {
        transferMoney(amount, fromCardNumber);
        return new DashboardPage();
    }

    public SelenideElement invalidTransaction (String amount, String fromCardNumber) {
        transferMoney(amount, fromCardNumber);
        return errorNotification();
    }

    public SelenideElement errorNotification () {
        return errorIcon.shouldBe(Condition.appear);
    }
}
