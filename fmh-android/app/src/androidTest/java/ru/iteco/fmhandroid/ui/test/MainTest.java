package ru.iteco.fmhandroid.ui.test;

import androidx.test.espresso.PerformException;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.step.AboutStep;
import ru.iteco.fmhandroid.ui.step.AuthStep;
import ru.iteco.fmhandroid.ui.step.ClaimsStep;
import ru.iteco.fmhandroid.ui.step.GeneralStep;
import ru.iteco.fmhandroid.ui.step.CreateClaimStep;
import ru.iteco.fmhandroid.ui.step.MainStep;
import ru.iteco.fmhandroid.ui.step.NewsStep;
import ru.iteco.fmhandroid.ui.step.MissionStep;
import ru.iteco.fmhandroid.ui.step.SplashStep;

@RunWith(AllureAndroidJUnit4.class)
public class MainTest {
    AuthStep authStep = new AuthStep();
    MainStep mainStep = new MainStep();
    ClaimsStep claimsStep = new ClaimsStep();
    NewsStep newsStep = new NewsStep();
    AboutStep aboutStep = new AboutStep();
    MissionStep missionStep = new MissionStep();
    CreateClaimStep createClaim = new CreateClaimStep();
    GeneralStep generalStep = new GeneralStep();
    SplashStep splashStep = new SplashStep();


    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        splashStep.appDownload();
        try {
            mainStep.mainScreenLoad();
        } catch (PerformException e) {
            authStep.authWithValidLoginAndPass(Helper.authInfo());
            authStep.clickSignInButton();
        } finally {
            mainStep.mainScreenLoad();
        }
    }

    @Test
    @DisplayName("НС-020 Корректное отображение списка вкладок меню")
    @Description("В выпадающем списке есть разделы Main, Claims, News, About")
    public void testCheckMenuScreenList() {
        mainStep.clickMenuButton();
        mainStep.checkMenuList();
    }

    @Test
    @DisplayName("НС-021 Навигация по меню - переход в раздел \"Claims\", \"Main\",  \"News\", " +
            "\"About\"")
    @Description("Переход на соответствующую вкладку из меню приложения")
    public void testCheckTransitionFromMenu() {
        mainStep.goToClaims();
        claimsStep.checkClaimsScreenElements();
        mainStep.goToMain();
        mainStep.checkMainElements();
        mainStep.goToNews();
        newsStep.checkNewsElements();
        mainStep.goToAbout();
        aboutStep.checkScreenElementsAbout();
    }

    @Test
    @DisplayName("НС-022 Переход на вкладку Love is all")
    @Description("Переход на вкладку Love is all из главного экрана приложения. На экране список цитат. " +
            "Наличие title \"Love is all\"")
    public void testCheckTransitionToMissionScreen() {
        mainStep.clickMissionButton();
        missionStep.checkMissionElements();
    }

    @Test
    @DisplayName("MC-023 Корректность отображения всех элементов на главной странице")
    @Description("Присутствуют элементы: на верхней панели кнопка меню (три полоски), " +
            "кнопка в виде бабочки, кнопка в виде человечка, блок News, ссылка на All News, блок Claims, " +
            "ссылка на All Claims, кнопка создания новой заявки")
    public void testCheckMainScreenElements() {
        mainStep.checkMainElements();
    }


    @Test
    @DisplayName("МС-024 Развернуть и свернуть блок News")
    @Description("Блок новостей при нажатии сворачиваются, при повтороноа нажатии - разворачивается")
    public void testExpandAndCollapseNewsBlock() {
        mainStep.checkAllNews();
        mainStep.allNewsNotDisplay();
        mainStep.checkAllNews();
        mainStep.allNewsDisplay();
    }

    @Test
    @DisplayName("МС-025 Переход на вкладку News с помощью All News и возврат на главный экран")
    @Description("На главном экране нажав кнопку All News совершается переход на вкладку News")
    public void testCheckAllNewsButton() {
        mainStep.clickAllNews();
        newsStep.checkNewsElements();
        mainStep.goToMain();
        mainStep.checkMainElements();
    }


    @Test
    @DisplayName("МС-026 Развернуть и свернуть блок Claims")
    @Description("Блок претензий при нажатии сворачиваются, при повтороноа нажатии - разворачивается")
    public void testExpandAndCollapseClaimsBlock() {
        mainStep.checkAllClaims();
        mainStep.allClaimsNotDisplay();
        mainStep.checkAllClaims();
        mainStep.allClaimsDisplay();
    }


    @Test
    @DisplayName("МС-027 Переход на вкладку Claims с помощью All Claims и возврат на главный экран")
    @Description("На главном экране нажав кнопку All Claims совершается переход на вкладку Claims")
    public void testCheckAllClaimsButton() {
        mainStep.clickAllClaims();
        claimsStep.checkClaimsScreenElements();
        mainStep.goToMain();
        mainStep.checkMainElements();
    }

    @Ignore
    @Test
    @DisplayName("МС-028 Развернуть отдельную новость")
    @Description("При нажатии на отдельную новость разворачивается ее содержание")
    public void testExpandSeparateNewsItem() {
        mainStep.checkOneNews(0);
        mainStep.descriptionIsDisplay(0);
    }

    @Test
    @DisplayName("МС-029 Развернуть/свернуть отдельную прeтенезию")
    @Description("При нажатии на претензию открывается окно с претензией и ее содержанием")
    public void testExpandCollapseSeparateClaim() {
        mainStep.clickClaimOnMain(0);
        claimsStep.allClaimElementsLoad();
        claimsStep.checkClaimElements();
        claimsStep.goBackToPreviousScreen();
        mainStep.checkMainElements();
    }

    @Test
    @DisplayName("Начать создавать заявку и вернуться обратно на главный экран")
    @Description("Нажать + , переход на экран создания претензии. " +
            "Нажать Cancel, затем ОК возвращается обратно на главный экран")
    public void testCheckNewClaimButton() {
        mainStep.clickNewClaimButton();
        createClaim.checkCreateClaimElements();
        generalStep.clickCancelButton();
        generalStep.clickOkButton();
        mainStep.checkMainElements();
    }
}