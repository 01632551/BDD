package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.time.Duration.*;

public class DashboardPage {
    private final SelenideElement header = $("[data-test-id=dashboard]");
    private final ElementsCollection topUpBalanceButtons = $$("[data-test-id=action-deposit]");
    private final ElementsCollection cards = $$(".list__item div");
    private final SelenideElement updateButton = $("[data-test-id=action-reload]");

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

    public void pageUpdating() {
        updateButton.click();
    }

    public TransferPage enterTransferPage (int num) {
        topUpBalanceButtons.get(num).click();
        return new TransferPage();
    }
}
