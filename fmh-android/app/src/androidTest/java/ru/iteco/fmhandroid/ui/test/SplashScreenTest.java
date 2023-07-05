package ru.iteco.fmhandroid.ui.test;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.step.SplashStep;

@RunWith(AllureAndroidJUnit4.class)
public class SplashScreenTest {

    SplashStep splashStep = new SplashStep();

    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Test
    @DisplayName("SS-0001 Приветственный экран Splash Screen (позитивный). " +
            "Наличие элементов картинка, индикатор загрузки, цитата")
    @Description("Элементы экрана Splash Screen присутствуют и отображены корректно")
    public void testCheckSplashScreenElements() {
        splashStep.checkSplashElements();
    }
}