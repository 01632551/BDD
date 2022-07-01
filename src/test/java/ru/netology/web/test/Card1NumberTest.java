package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

// проверка с номерами карт
public class Card1NumberTest {
    private final String amount = "0";

    @BeforeEach
    public void login() {
        Selenide.open("http://localhost:9999/");
        Configuration.holdBrowserOpen = true;

        // get data
        var authInfo = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);

        // entrance in the private office
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.validVerify(verificationCode);

        // update dashboard page
        var dashboardPage = new DashboardPage();
        dashboardPage.pageUpdating();

        // go to 1-st card transfer page
        dashboardPage.enterTransferPage(0);
    }

    @Test
    public void shouldGoToDashboardPageWithValidCardNumber() {
        var transferPage = new TransferPage();
        var fromCard = DataHelper.getFromC1();
        transferPage.validTransaction(amount, fromCard.getFromCard());
    }

    // none card number
    @Test
    public void shouldAppearErrorNotificationWithNoneCardNumber() {
        var transferPage = new TransferPage();
        var fromCard = "";
        transferPage.invalidTransaction(amount, fromCard);
    }

    // not completely filled card number
    @Test
    public void shouldAppearErrorNotificationWithNotCompletelyFilledNumber() {
        var transferPage = new TransferPage();
        var fromCard = "5559000000";
        transferPage.invalidTransaction(amount, fromCard);
    }

    // full invalid card number
    @Test
    public void shouldAppearErrorNotificationWithInvalidCardNumber() {
        var transferPage = new TransferPage();
        var fromCard = "0000000000000000";
        transferPage.invalidTransaction(amount, fromCard);
    }

    // transfer from "fromCard"
    // должен упасть
    @Test
    public void shouldAppearErrorNotificationWithTheSameCards() {
        var transferPage = new TransferPage();
        var fromCard = DataHelper.getFromC2(); // 5559 0000 0000 0001
        transferPage.invalidTransaction(amount, fromCard.getFromCard());
    }
}
