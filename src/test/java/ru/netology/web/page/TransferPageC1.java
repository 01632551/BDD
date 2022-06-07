package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPageC1 {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private DataHelper.TransferC1 fromCard = DataHelper.getFromC1();

    public DashboardPage validTransaction (String amount) {
        amountField.doubleClick();
        amountField.setValue(amount);
        fromField.doubleClick();
        fromField.click();
        fromField.setValue(fromCard.getFromCard());
        transferButton.click();
        return new DashboardPage();
    }

    public ErrorNotification invalidTransaction (String amount, String cardNumber) {
        amountField.click();
        amountField.setValue(amount);
        fromField.doubleClick();
        fromField.click();
        fromField.setValue(cardNumber);
        transferButton.click();
        return new ErrorNotification();
    }
}
