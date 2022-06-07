package ru.netology.web.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {}

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class TransferC1 {
        private String fromCard;
    }

    public static TransferC1 getFromC1() {
        return new TransferC1("5559000000000002");
    }

    @Value
    public static class TransferC2 {
        private String fromCard;
    }

    public static TransferC2 getFromC2() {
        return new TransferC2("5559000000000001");
    }
}
