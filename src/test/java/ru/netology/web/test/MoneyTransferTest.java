package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    // BUGS: 1 - transfer of sum that bigger than balance (negative other balance);
    // 2 - not throw exception while transfer the same cards;
    // 3 - with double sum transfers one as integer (5,xx the same 5xx (not transfer kopecks))

    @BeforeEach
    public void login() {
        // login
        Selenide.open("http://localhost:9999/");
        Configuration.holdBrowserOpen = true;

        // get help class
        var equal = new EqualizationOfBalance();

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

        // equalization
        equal.equalization();
    }

// TODO test: сумма больше 10000. С обеих карт
    @Test
    public void shouldTransferBiggerAmountThatBalanceFromC2ToC1(){
        var dashboardPage = new DashboardPage();
        dashboardPage.enterFirstTransferPage();
        var transferPage = new TransferPageC1();
        transferPage.validTransaction("110000");
        assertEquals(120000, dashboardPage.getCardBalance(0));
    }

    @Test
    public void shouldTransferBiggerAmountThatBalanceFromC1ToC2(){
        var dashboardPage = new DashboardPage();
        dashboardPage.enterFirstTransferPage();
        var transferPage = new TransferPageC2();
        transferPage.validTransaction("110000");
        assertEquals(120000, dashboardPage.getCardBalance(1));
    }

    // TODO test: сумма меньше 10000. С обеих карт
    @Test
    public void shouldTransferNormalAmountFromC2ToC1(){
        var dashboardPage = new DashboardPage();
        dashboardPage.enterFirstTransferPage();
        var transferPage = new TransferPageC1();
        transferPage.validTransaction("1001");
        assertEquals(11001, dashboardPage.getCardBalance(0));
    }

    @Test
    public void shouldTransferNormalAmountFromC1ToC2(){
        var dashboardPage = new DashboardPage();
        dashboardPage.enterFirstTransferPage();
        var transferPage = new TransferPageC1();
        transferPage.validTransaction("1001");
        assertEquals(11001, dashboardPage.getCardBalance(1));
    }

    // TODO test: перевод на 0 рублей. С обеих карт (проверено в Card1-2NumberTest)

    // TODO test: перевод сумммы с копейками
    @Test
    public void shouldTransferAmountWithKopecksFromC2ToC1(){
        var dashboardPage = new DashboardPage();
        dashboardPage.enterFirstTransferPage();
        var transferPage = new TransferPageC1();
        transferPage.validTransaction("100.50");
        assertEquals(10100.50, dashboardPage.getCardBalance(0));
    }

    @Test
    public void shouldTransferAmountWithKopecksFromC1ToC2(){
        var dashboardPage = new DashboardPage();
        dashboardPage.enterFirstTransferPage();
        var transferPage = new TransferPageC1();
        transferPage.validTransaction("100.50");
        assertEquals(10100.50, dashboardPage.getCardBalance(1));
    }
}
