package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

public class Card2NumberTest {
    private final String amount = "0";

    @BeforeEach
    public void login() {
        Selenide.open("http://localhost:9999/");
        Configuration.holdBrowserOpen=true;

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

        // go to transfer page about 2-nd card
        dashboardPage.enterTransferPage(1);
    }

    // transfer from "fromCard"
    // должен упасть
    @Test
    public void shouldAppearErrorNotificationWithTheSameCards() {
        var transferPage = new TransferPage();
        var fromCard = DataHelper.getFromC1(); // 5559 0000 0000 0002
        transferPage.invalidTransaction(amount, fromCard.getFromCard());
    }

    // valid card number
    @Test
    public void shouldGoToDashboardPageWithValidCardNumber() {
        var transferPage = new TransferPage();
        var fromCard = DataHelper.getFromC2();
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
}
