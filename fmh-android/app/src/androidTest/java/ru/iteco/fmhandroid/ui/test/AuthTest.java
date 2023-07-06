package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.authInfo;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.step.AuthStep;
import ru.iteco.fmhandroid.ui.step.GeneralStep;
import ru.iteco.fmhandroid.ui.step.MainStep;
import ru.iteco.fmhandroid.ui.step.SplashStep;

@RunWith(AllureAndroidJUnit4.class)
public class AuthTest {

    AuthStep authStep = new AuthStep();
    MainStep mainStep = new MainStep();
    GeneralStep generalStep = new GeneralStep();
    SplashStep splashStep = new SplashStep();

    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        splashStep.appDownload();
        try {
            authStep.loadAuthPage();
            authStep.checkAuthScreenElements();

        } catch (Exception e) {
            mainStep.clickLogOutButton();
            authStep.loadAuthPage();
        }
    }

    @Test
    @DisplayName("AC-001 Наличие всех элементов формы авторизации (позитивный)")
    @Description("Элементы формы авторизации отображены корректно")
    public void testCheckAuthScreenElements() {
        authStep.checkAuthScreenElements();
    }

    @Test
    @DisplayName("AC-002 Авторизация в приложении под валидными данными (позитивный)")
    @Description("После ввода валидного логина и пароля происходит переход на главный экран приложения")
    public void testLoginWithValidLoginAndPass() {
        authStep.authWithValidLoginAndPass(authInfo());
        authStep.clickSignInButton();
        mainStep.mainScreenLoad();
        mainStep.checkMainElements();
    }

    @Test
    @DisplayName("AC-003 Выход из учетной записи (позитивный) ")
    @Description("Авторизованный пользователь выходит из приложения с помощью кнопки Log out")
    public void testLogOutApplication() {
        authStep.authWithValidLoginAndPass(authInfo());
        authStep.clickSignInButton();
        mainStep.mainScreenLoad();
        mainStep.checkMainElements();
        mainStep.clickLogOutButton();
        authStep.checkAuthScreenElements();
    }
    @Test
    @DisplayName("АС-008 Авторизация в приложении, когда поле \"Логин\" и \"Пароль\" заполнено " +
            "данными незарегистрированного пользователя (негативный)")
    @Description("При вводе невалидных значений логина и пароля всплывает сообщение о неверных данных" +
            "Wrong login or password")
    public void testLoginWithInvalidLoginAndPass() {
        authStep.authWithInvalidLoginAndPass(authInfo());
        authStep.clickSignInButton();
        generalStep.checkInvalidAuthDataToast();
    }

    @Test
    @DisplayName("АС-009 Авторизация в приложении, когда поле \"Пароль\" заполнено данными " +
            "незарегистрированного пользователя (негативный)")
    @Description("При вводе невалидных значений в поле Пароль всплывает сообщение о неверных данных" +
            "Wrong login or password")
    public void testLoginWithInvalidPass() {
        authStep.authWithInvalidPass(authInfo());
        authStep.clickSignInButton();
        generalStep.checkInvalidAuthDataToast();
    }

    @Test
    @DisplayName("АС-010 Авторизация в приложении, когда поле \"Логин\" заполнено данными " +
            "незарегистрированного пользователя (негативный)")
    @Description("При вводе невалидных значений логина всплывает сообщение о неверных данных" +
            "Wrong login or password")
    public void testLoginWithInvalidLogin() {
        authStep.authWithInvalidLogin(authInfo());
        authStep.clickSignInButton();
        generalStep.checkInvalidAuthDataToast();
    }

    @Test
    @DisplayName("АС-011 Авторизация в приложении, когда поле \"Логин\" пустое (негативный)")
    @Description("При авторизации с пустым логином пользователь не авторизуется. " +
            "Ошибка: Login and password cannot be empty")
    public void testLoginWithEmptyLogin() {
        authStep.authWithEmptyLogin(authInfo());
        authStep.clickSignInButton();
        generalStep.checkEmptyAuthDataToast();
    }

    @Test
    @DisplayName("АС-012 Авторизация в приложении, когда поле \"Пароль\" пустое (негативный)")
    @Description("При авторизации с пустым паролем пользователь не авторизуется. " +
            "Ошибка: Login and password cannot be empty")
    public void testLoginWithEmptyPassword() {
        authStep.authWithEmptyPass(authInfo());
        authStep.clickSignInButton();
        generalStep.checkEmptyAuthDataToast();
    }

    @Test
    @DisplayName("АС-013 Авторизация в приложении, когда поле \"Логин\" и \"Пароль\" пустое (негативный)")
    @Description("При авторизации с пустым логином и паролем пользователь не авторизуется. " +
            "Ошибка: Login and password cannot be empty")
    public void testLoginWithEmptyLoginAndPass() {
        authStep.clickSignInButton();
        generalStep.checkEmptyAuthDataToast();
    }
}
