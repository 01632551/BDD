package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.AuxiliaryMethods.EqualizationOfBalance;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    // BUGS: 1 - transfer of sum that bigger than balance (negative other balance);
    // 2 - not throw exception while transfer the same cards;
    // 3 - with double sum transfers one as integer (5,xx the same 5xx (not transfer kopecks))

    public void login() {
        // entrance
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
    }

    @BeforeEach
    public void equalize() {
        login();

        var equalize = new EqualizationOfBalance();
        equalize.equalization();

        Selenide.closeWindow();

        login();
    }

// TODO test: сумма больше 10000. С обеих карт
    @Test
    public void shouldTransferBiggerAmountThatBalanceFromC2ToC1(){
        var dashboardPage = new DashboardPage();
        dashboardPage.enterTransferPage(0);
        var transferPage = new TransferPage();
        transferPage.validTransaction("110000", "5559000000000002");
        assertEquals(120000, dashboardPage.getCardBalance(0));
        assertEquals(-100000, dashboardPage.getCardBalance(1));
    }

    @Test
    public void shouldTransferBiggerAmountThatBalanceFromC1ToC2(){
        var dashboardPage = new DashboardPage();
        dashboardPage.enterTransferPage(1);
        var transferPage = new TransferPage();
        transferPage.validTransaction("110000", "5559000000000001");
        assertEquals(120000, dashboardPage.getCardBalance(1));
        assertEquals(-100000, dashboardPage.getCardBalance(0));
    }

    // TODO test: сумма меньше 10000. С обеих карт
    @Test
    public void shouldTransferNormalAmountFromC2ToC1(){
        var dashboardPage = new DashboardPage();
        dashboardPage.enterTransferPage(0);
        var transferPage = new TransferPage();
        transferPage.validTransaction("1001", "5559000000000002");
        assertEquals(11001, dashboardPage.getCardBalance(0));
        assertEquals(8999, dashboardPage.getCardBalance(1));
    }

    @Test
    public void shouldTransferNormalAmountFromC1ToC2(){
        var dashboardPage = new DashboardPage();
        dashboardPage.enterTransferPage(1);
        var transferPage = new TransferPage();
        transferPage.validTransaction("1001", "5559000000000001");
        assertEquals(11001, dashboardPage.getCardBalance(1));
        assertEquals(8999, dashboardPage.getCardBalance(0));
    }

    // TODO test: перевод на 0 рублей. С обеих карт (проверено в Card1-2NumberTest)

    // TODO test: перевод сумммы с копейками
    // эти два должны упасть (баг)
    @Test
    public void shouldTransferAmountWithKopecksFromC2ToC1(){
        var dashboardPage = new DashboardPage();
        dashboardPage.enterTransferPage(0);
        var transferPage = new TransferPage();
        transferPage.validTransaction("100.50", "5559000000000002");
        assertEquals(10100.50, dashboardPage.getCardBalance(0));
        assertEquals(9899.50, dashboardPage.getCardBalance(1));
    }

    @Test
    public void shouldTransferAmountWithKopecksFromC1ToC2(){
        var dashboardPage = new DashboardPage();
        dashboardPage.enterTransferPage(1);
        var transferPage = new TransferPage();
        transferPage.validTransaction("100.50", "5559000000000001");
        assertEquals(10100.50, dashboardPage.getCardBalance(1));
        assertEquals(9899.50, dashboardPage.getCardBalance(0));
    }
}
