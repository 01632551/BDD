package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.time.Duration.*;

public class DashboardPage {
    private SelenideElement header = $("[data-test-id=dashboard]");
    private ElementsCollection topUpBalanceButtons = $$("[data-test-id=action-deposit]");
    private ElementsCollection cards = $$(".list__item div");
    private SelenideElement updateButton = $("[data-test-id=action-reload]");

    private final String balanceStart = ", баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage () {
        header.shouldBe(Condition.visible);
    }

    public int getCardBalance(int index) {
        var text = cards.get(index).text();
        return extractBalance(text);
    }

    public int extractBalance (String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPageC1 enterFirstTransferPage () {
        topUpBalanceButtons.get(0).click();
        return new TransferPageC1();
    }

    public TransferPageC2 enterSecondTransferPage () {
        topUpBalanceButtons.get(1).click();
        return new TransferPageC2();
    }

    public void pageUpdating() {
        updateButton.click();
    }
}