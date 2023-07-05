package ru.iteco.fmhandroid.ui.test;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Data;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.step.AuthStep;
import ru.iteco.fmhandroid.ui.step.MainStep;
import ru.iteco.fmhandroid.ui.step.MissionStep;
import ru.iteco.fmhandroid.ui.step.SplashStep;

@RunWith(AllureAndroidJUnit4.class)
public class MissionTest {

    AuthStep authStep = new AuthStep();
    MissionStep missionStep = new MissionStep();
    MainStep mainStep = new MainStep();
    SplashStep splashStep = new SplashStep();
    Data data = new Data();

    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        splashStep.appDownload();
        try {
            mainStep.mainScreenLoad();
        } catch (Exception e) {
            authStep.authWithValidLoginAndPass(Helper.authInfo());
            authStep.clickSignInButton();
        } finally {
            mainStep.mainScreenLoad();
            mainStep.clickMissionButton();
        }
    }

    @Test
    @DisplayName("Проверка элементов экрана с цитатами")
    @Description("Корректность отображения всех элементов экрана с цитатами")
    public void testCheckMissionScreenElements() {
        missionStep.checkMissionElements();
    }

    @Test
    @DisplayName("Развернуть и свернуть цитату, во вкладке \"Love is all\" приложения (позитивный)")
    @Description("При нажатии - разворачивается содержимое цитаты")
    public void testExpandAndCollapseQuote() {
        missionStep.checkQuote(2);
        missionStep.descriptionIsDisplay(data.quoteText);
        missionStep.checkQuote(2);
        missionStep.descriptionNotDisplay(data.quoteText);
    }
}
