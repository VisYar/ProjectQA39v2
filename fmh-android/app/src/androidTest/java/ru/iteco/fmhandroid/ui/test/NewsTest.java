package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.swipeDown;
import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomCategory;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.data.Data;
import ru.iteco.fmhandroid.ui.element.PanelElement;
import ru.iteco.fmhandroid.ui.element.NewsElement;
import ru.iteco.fmhandroid.ui.step.AuthStep;
import ru.iteco.fmhandroid.ui.step.GeneralStep;
import ru.iteco.fmhandroid.ui.step.PanelSteps;
import ru.iteco.fmhandroid.ui.step.CreateNewsStep;
import ru.iteco.fmhandroid.ui.step.EditNewsStep;
import ru.iteco.fmhandroid.ui.step.FilterNewsStep;
import ru.iteco.fmhandroid.ui.step.MainStep;
import ru.iteco.fmhandroid.ui.step.NewsStep;
import ru.iteco.fmhandroid.ui.step.SplashStep;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class NewsTest {

    AuthStep authStep = new AuthStep();
    NewsStep newsStep = new NewsStep();
    MainStep mainStep = new MainStep();
    PanelSteps panelSteps = new PanelSteps();
    PanelElement panelElement = new PanelElement();
    FilterNewsStep filterScreen = new FilterNewsStep();
    Data data = new Data();
    CreateNewsStep createNewsStep = new CreateNewsStep();
    GeneralStep generalStep = new GeneralStep();
    EditNewsStep editNewsStep = new EditNewsStep();
    NewsElement newsElement = new NewsElement();
    SplashStep splashStep = new SplashStep();


    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        splashStep.appDownload();
        try {
            mainStep.mainScreenLoad();
        } catch (NoMatchingViewException e) {
            authStep.authWithValidLoginAndPass(Helper.authInfo());
            authStep.clickSignInButton();
        } finally {
            mainStep.mainScreenLoad();
            mainStep.clickAllNews();
        }
    }

    @Test
    @DisplayName("NC-060 Сортировка новостей во вкладке \"Новости\" (позитивный)")
    @Description("Меняется порядок отображения новостей по дате. Иногда не проходит")
    public void testSortNews() {
        String firstNewsTitle = newsStep.getFirstNewsTitle(0);
        newsStep.clickSortButton();
        newsElement.allNewsBlock.perform(swipeDown());
        newsStep.clickSortButton();
        newsElement.allNewsBlock.perform(swipeDown());
        newsStep.newsListLoad();
        String firstNewsTitleAfterSecondSorting = newsStep.getFirstNewsAfterSecondSort(0);
        assertEquals(firstNewsTitle, firstNewsTitleAfterSecondSorting);
    }


    @Test
    @DisplayName("NC-061-1 Фильтр по дате - есть новости")
    @Description("Новости для данной даты существуют")
    public void testFilterByDate() {
        newsStep.openFilter();
        filterScreen.checkFilterElements();
        filterScreen.fillInStartDateField(data.dateOfPublic);
        filterScreen.fillInEndDateField(data.dateOfPublic);
        filterScreen.clickFilter();
        newsStep.checkPublicationDate();
    }

    @Test
    @DisplayName("NC-061-2 Фильтр по дате - нет новостей")
    @Description("Если нет новостей для этой даты - надпись There is nothing here yet")
    public void testNothingToShowScreen() {
        newsStep.openFilter();
        filterScreen.checkFilterElements();
        filterScreen.fillInStartDateField(data.dateNonNews);
        filterScreen.fillInEndDateField(data.dateNonNews);
        filterScreen.clickFilter();
        generalStep.checkNewsButterfly();
        generalStep.checkNothingToShow();
    }

    @Test
    @DisplayName("NC-062 Отмена фильтрации")
    @Description("Выход из фильтра без фильтрации новостей")
    public void testCancelingFiltering() {
        newsStep.openFilter();
        filterScreen.checkFilterElements();
        filterScreen.fillInStartDateField(data.dateOfPublic);
        generalStep.clickCancelButton();
        newsStep.checkNewsElements();
    }

    @Test
    @DisplayName("NC-063 Перейти в Control panel")
    @Description("Нажать - блокнот с карандашом")
    public void testGoToControlPanel() {
        newsStep.clickEditButton();
        panelSteps.checkPanelElements();
    }

     @Test
    @DisplayName("NC-064 Изменить порядок отображения новостей в панели управления")
    @Description("Не проходит. Кнопка сортировки меняет порядок отображения новостей по дате")
    public void testChangeOrderOfNewsDisplay() {
        newsStep.clickEditButton();
        panelSteps.checkPanelSort();
    }

    @Test
    @DisplayName("NC-065 Проверка чек-боксов расширенного фильтра")
    @Description("При нажатии чек-боксы становятся неактивными")
    public void testCheckingCheckboxesFilter() {
        newsStep.clickEditButton();
        panelSteps.openNewsFilter();
        filterScreen.clickActiveCheckBox();
        filterScreen.checkBoxStatusActive(false);
        filterScreen.clickNotActiveCheckBox();
        filterScreen.checkBoxStatusNotActive(false);
    }

    @Test
    @DisplayName("NC-066 Фильтр новостей через панель управления по статусу")
    @Description("При фильтре новостей по статусу Active/Not Active в списке новостей отображаются " +
            "только новости с этим статусом. Иногда не проходит")
    public void testCheckFilterActiveNotActive() {
        newsStep.clickEditButton();
        panelSteps.openNewsFilter();
        filterScreen.clickNotActiveCheckBox();
        filterScreen.clickFilter();
        newsStep.newsListLoad();
        panelSteps.checkStatusActive();
        panelSteps.openNewsFilter();
        filterScreen.clickActiveCheckBox();
        filterScreen.clickFilter();
        newsStep.newsListLoad();
        panelSteps.checkStatusNotActive();
    }

    @Test
    @DisplayName("NC-067 Отмена фильтра новостей через панель управления")
    @Description("Выход из фильтра с помощью кнопки отмена")
    public void testCancelFilteringNews() {
        newsStep.clickEditButton();
        panelSteps.openNewsFilter();
        generalStep.clickCancelButton();
        panelSteps.checkPanelElements();
    }

    @Test
    @DisplayName("NC-068 Создать новость на кирилице")
    @Description("Заполнение полей данным на кириллице")
    public void testCreateNewsCyrillic() {
        newsStep.clickEditButton();
        panelSteps.clickCreateNewsButton();
        createNewsStep.checkNewsScreenElements();
        createNewsStep.createNews(randomCategory(), data.titleCyr, data.dateOfPublic,
                data.timeOfPublic, data.descriptOnCyr);
        generalStep.clickSaveButton();
        newsStep.newsListLoad();
        panelSteps.checkCreateNews(0, data.titleCyr, data.descriptOnCyr);
        mainStep.goToNews();
        newsElement.allNewsBlock.perform(swipeDown());
        newsStep.checkOpenNews(0);
        String createdDescription = newsStep.getCreateNewsDescription(0);
        assertEquals(data.descriptOnCyr, createdDescription);
    }

    @Test
    @DisplayName("NC-069 Создать новость со спец символами")
    @Description("Допускается ввод спецсимволов - плохо. При заполнении полей спец символами- новость не создается")
    public void testCreateNewsWithSymbols() {
        newsStep.clickEditButton();
        panelSteps.clickCreateNewsButton();
        createNewsStep.checkNewsScreenElements();
        createNewsStep.createNews(randomCategory(), data.titleSymb, data.dateOfPublic,
                data.timeOfPublic, data.descriptSymb);
        generalStep.clickSaveButton();
        generalStep.checkInvalidData("Wrong format data", true);
    }

    @Test
    @DisplayName("NC-070 Создать новость с пустым полем Description")
    @Description("Уведомление о необходимости заполнить поле")
    public void testCreateNewsWithEmptyDescription() {
        newsStep.clickEditButton();
        panelSteps.clickCreateNewsButton();
        createNewsStep.checkNewsScreenElements();
        createNewsStep.createNews(randomCategory(), data.titleCyr, data.dateOfPublic,
                data.timeOfPublic, data.descriptionEmptyText);
        generalStep.clickSaveButton();
        generalStep.checkEmptyFieldError();
    }

    @Test
    @DisplayName("NC-071 Создать новость с пустыми полями")
    @Description("Уведомление о необходимости заполнить поля полях")
    public void testCreateNewsWithEmptyFields() {
        newsStep.clickEditButton();
        panelSteps.clickCreateNewsButton();
        createNewsStep.checkNewsScreenElements();
        generalStep.clickSaveButton();
        generalStep.checkEmptyFieldError();
    }

    @Test
    @DisplayName("NC-072 Отменить создание новости")
    @Description("Нажать и подтвердить отмену - новость не создается")
    public void testCancelNewsCreate() {
        newsStep.clickEditButton();
        panelSteps.clickCreateNewsButton();
        createNewsStep.checkNewsScreenElements();
        createNewsStep.fillInPublicDateField(data.dateOfPublic);
        createNewsStep.fillInTimeField(data.timeOfPublic);
        generalStep.clickCancelButton();
        generalStep.clickOkButton();
        panelSteps.checkPanelElements();
    }

    @Test
    @DisplayName("NC-073 Отменить создание новости и вернуться к созданию")
    @Description("Без подтверждения отмены - можно продолжать создание новости")
    public void testCancelNewsCreationAndReturnCreation() {
        newsStep.clickEditButton();
        panelSteps.clickCreateNewsButton();
        createNewsStep.checkNewsScreenElements();
        createNewsStep.fillInPublicDateField(data.dateOfPublic);
        createNewsStep.fillInTimeField(data.timeOfPublic);
        generalStep.clickCancelButton();
        generalStep.clickCancelInDialog();
        createNewsStep.checkNewsScreenElements();
    }

    @Test
    @DisplayName("NC-074 Изменить статус созданной новости")
    @Description("Изменение статуса с Active на Not Active и обратно. Новость с новым статусом")
    public void testChangeStatusOfCreatedNews() {
        newsStep.clickEditButton();
        panelSteps.clickCreateNewsButton();
        createNewsStep.createNews(randomCategory(), data.titleCyr, data.dateOfPublic,
                data.timeOfPublic, data.descriptOnCyr);
        generalStep.clickSaveButton();
        newsStep.newsListLoad();
        panelSteps.clickEditNews(0);
        editNewsStep.changeStatus();
        generalStep.clickSaveButton();
        newsStep.newsListLoad();
        panelSteps.checkStatusNotActive();
        panelSteps.clickEditNews(0);
        editNewsStep.changeStatus();
        generalStep.clickSaveButton();
        panelSteps.checkStatusActive();
    }

    @Test
    @DisplayName("NC-075 Редактирование новости")
    @Description("Новость с новыми данными")
    public void testEditNews() {
        newsStep.clickEditButton();
        panelSteps.clickCreateNewsButton();
        createNewsStep.createNews(randomCategory(), data.titleCyr, data.dateOfPublic,
                data.timeOfPublic, data.descriptOnCyr);
        generalStep.clickSaveButton();
        newsStep.newsListLoad();
        panelSteps.clickEditNews(0);
        editNewsStep.checkEditNewsElements();
        editNewsStep.changeTitle(data.newTitleEdit);
        editNewsStep.changeDescription(data.newDescriptionEdit);
        generalStep.clickSaveButton();
        panelSteps.clickOneNewsItem(0);
        assertEquals(data.newTitleEdit, panelSteps.getEditNewsTitle(0));
        assertEquals(data.newDescriptionEdit, panelSteps.getEditNewsDescription(0));
    }

    @Test
    @DisplayName("NC-076 Отмена редактирование новости")
    @Description("Нажать кнопку отмены и подтвердить, тогда новость не изменятеся")
    public void testCancelEditNews() {
        newsStep.clickEditButton();
        panelSteps.clickCreateNewsButton();
        createNewsStep.createNews(randomCategory(), data.titleCyr,
                data.dateOfPublic, data.timeOfPublic, data.descriptOnCyr);
        generalStep.clickSaveButton();
        newsStep.newsListLoad();
        panelSteps.clickEditNews(0);
        editNewsStep.checkEditNewsElements();
        editNewsStep.changeTitle(data.newTitle);
        editNewsStep.changeDescription(data.newDescription);
        generalStep.clickCancelButton();
        generalStep.clickOkButton();
        panelSteps.checkPanelElements();
        panelElement.newsList.perform(swipeDown());
        panelSteps.clickOneNewsItem(0);
        assertEquals(data.titleCyr, panelSteps.getEditNewsTitle(0));
    }

    @Test
    @DisplayName("NC-077 Отмена редактирование и возврат к редактированию")
    @Description("Отмена - не подтверждается, редактирование - продолжается")
    public void testCancelNewsEditAndReturnToEditing() {
        newsStep.clickEditButton();
        panelSteps.clickCreateNewsButton();
        createNewsStep.createNews(randomCategory(), data.titleCyr, data.dateOfPublic,
                data.timeOfPublic, data.descriptOnCyr);
        generalStep.clickSaveButton();
        newsStep.newsListLoad();
        panelSteps.clickEditNews(0);
        editNewsStep.checkEditNewsElements();
        editNewsStep.changeTitle(data.newTitle);
        editNewsStep.changeDescription(data.newDescription);
        generalStep.clickCancelButton();
        generalStep.clickCancelInDialog();
        editNewsStep.checkEditNewsElements();
    }
}