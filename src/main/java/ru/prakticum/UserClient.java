package ru.prakticum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class UserClient extends Client {
    public static final String URL = "https://stellarburgers.nomoreparties.site/api";
    public static final String ApiLogin = "/auth/login";
    public static final String ApiDelete = "/auth/user";

    @Step("Получить accessToken пользователя")
    public String getAccessTokenOnLogin(User user) {
        return spec()
                .body(user)
                .when()
                .post(URL + ApiLogin)
                .extract()
                .path("accessToken");
    }

    @Step("Удаление пользовательских данных из системы")
    public ValidatableResponse delete(String accessToken) {
        return spec()
                .auth().oauth2(accessToken.replace("Bearer ", ""))
                .when()
                .delete(URL + ApiDelete)
                .then().log().all();
    }
}
