package ru.netology.web.AuxiliaryMrthods;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;

import static com.codeborne.selenide.Selenide.$;

public class EqualizationOfBalance {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");


    private DashboardPage dashboard = new DashboardPage();
    private final String fromCard2Number = "5559000000000001";
    private final String fromCard1Number = "5559000000000002";

    private final int firstBalance = dashboard.getCardBalance(0); // получаем из DashboardPage
    private final int secondBalance = dashboard.getCardBalance(1); // так же как и с первым
    private final int delta = (firstBalance - secondBalance) / 2;

    public DashboardPage transactions(String amount, String fromCardNumber) {
//        amountField.click();
        amountField.setValue(amount);
//        fromField.click();
        fromField.setValue(fromCardNumber);
        transferButton.click();
        return new DashboardPage();
    }

    public void equalization() {

        if (delta >= 0) {
            dashboard.enterTransferPage(1);
            transactions(Integer.toString(delta), fromCard2Number);
        } else {
            dashboard.enterTransferPage(0);
            transactions(Integer.toString(delta), fromCard1Number);
        }

        dashboard.pageUpdating();
    }
}
