package ru.netology.web.AuxiliaryMethods;

import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.TransferPage;


public class EqualizationOfBalance {

    private final DataHelper.TransferC1 fromCard2 = DataHelper.getFromC1();
    private final DataHelper.TransferC2 fromCard1 = DataHelper.getFromC2();

    private DashboardPage dashboard = new DashboardPage();

    private final int firstBalance = dashboard.getCardBalance(0); // получаем из DashboardPage
    private final int secondBalance = dashboard.getCardBalance(1); // так же как и с первым
    private final int delta = (firstBalance - secondBalance) / 2;

    public void equalization() {

        if (delta >= 0) {
            dashboard.enterTransferPage(1);
            var transferPage = new TransferPage();
            transferPage.validTransaction(Integer.toString(delta), fromCard1.getFromCard()); // from 1-st to 2-nd
        } else {
            dashboard.enterTransferPage(0);
            var transferPage = new TransferPage();
            transferPage.validTransaction(Integer.toString(delta), fromCard2.getFromCard()); // from 2-nd to 1-st
        }

        dashboard.pageUpdating();
    }
}
