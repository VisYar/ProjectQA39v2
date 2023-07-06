# Дипломный проект по профессии «Инженер по тестированию»

## Тестирование мобильного приложения «Мобильный хоспис»

[Ссылка на задание](https://github.com/netology-code/qamid-diplom)

## Описание приложения

Приложение даёт функционал по работе с претензиями хосписа и включает в себя:
- информацию о претензиях и функционал для работы с ними;
- новостную сводку хосписа;
- тематические цитаты.

## Документация

- [План тестирования](https://github.com/VisYar/ProjectQA39v2/blob/master/Plan.md)
- [Чек-лист](https://docs.google.com/spreadsheets/d/1n8drndLZfLUC6JWZItOuXtQUB-e9XyBoIkpHJEDPSeQ/edit?usp=sharing)
- [Тест-кейсы](https://docs.google.com/spreadsheets/d/16TlvZ9rxXIExzIDt-xk3M38z6HTEGuZlMSVt3jiFWSo/edit?usp=sharing)

## Запуск приложения и тестов
- Клонировать репозиторий командой `git clone`;
- Открыть проект в Android Studio;
- Запустить приложение на эмуляторе Pixel3 или Pixel4 API 29;
- Запустить тесты в терминале `./gradlew connectedAndroidTest`;
- Подождать пока пройдут все тесты и посмотреть результат.

## Формирование отчета AllureReport
- Выгрузить каталог `/data/data/ru.iteco.fmhandroid/files/allure-results` с эмулятора;
- Выполнить команду `allure serve`, находясь на уровень выше allure-results;
- Подождать генерации отчета и посмотреть его в открывшемся браузере.
