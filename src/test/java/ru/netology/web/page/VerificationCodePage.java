package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class VerificationCodePage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement codeButton = $("[data-test-id=action-verify]");

    public VerificationCodePage() {
        codeField.shouldBe(Condition.visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        codeButton.click();
        return new DashboardPage();
    }
}
