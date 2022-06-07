package ru.netology.web.page;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import ru.netology.web.data.DataHelper;

public class EqualizationOfBalance {

    private DashboardPage dashboard = new DashboardPage();
    private TransferPageC1 topUpC1= new TransferPageC1();
    private TransferPageC2 topUpC2 = new TransferPageC2();
    private final int firstBalance = dashboard.getCardBalance(0); // получаем из DashboardPage
    private final int secondBalance = dashboard.getCardBalance(1); // так же как и с первым
    private final int delta = (firstBalance - secondBalance)/2;

    public void equalization () {
        if (delta >= 0) {
            dashboard.enterSecondTransferPage();
            topUpC2.validTransaction(Integer.toString(delta));
        } else {
            dashboard.enterFirstTransferPage();
            topUpC1.validTransaction(Integer.toString(delta));
        }

        dashboard.pageUpdating();
    }
}
