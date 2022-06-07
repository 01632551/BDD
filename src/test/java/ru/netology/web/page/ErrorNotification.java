package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ErrorNotification {
    private SelenideElement errorIcon = $("[data-test-id=error-notification]");

    public ErrorNotification () {
        errorIcon.shouldBe(Condition.appear);
    }
}
