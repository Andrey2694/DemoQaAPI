# Автотесты для сайта https://demoqa.com

___

## Используемые технологии и инструменты

<code><img width="5%" title="Java" src="images/JAVA.svg"></code>
<code><img width="5%" title="Gradle" src="images/Gradle.svg"></code>
<code><img width="5%" title="JUnit5" src="images/Junit5.svg"></code>
<code><img width="5%" title="Jenkins" src="images/Jenkins.svg"></code>
<code><img width="5%" title="Allure TestOps" src="images/Allure TestOps.svg"></code>
<code><img width="5%" title="Allure Report" src="images/Allure Report.svg"></code>
<code><img width="5%" title="REST Assured" src="images/RESTAssured.svg"></code>
<code><img width="5%" title="Telegram" src="images/Telegram.svg"></code>
</p>

___

## Инструкция по запуску тестов

Запуск тестов происходит через джобу в [Jenkins](https://jenkins.autotests.cloud/job/DemoQaAPI/)

### Run tests with filled remote.properties:

```bash
gradle clean demoqa
```

### Serve report:

```bash
allure serve build/allure-results
```

## Оповещение о результатах прохождения тестов через бот в телеграмм

![Telegram]()

## Анализ результатов

Более подробно с результатми тестов (шаги, скриншоты, видео прохождения теста, page source и browser console log) можно
ознакомиться в:

* Jenkins через Allure Reports или Allure TestOps

### Анализ результатов в Jenkins через Allure Reports

![alt "Allure Reports"]()

### Анализ результатов в Allure TestOps

![alt "Allure TestOps"]()

### Интерграция с Jira
#### Отображение тест-кейсов и ланча

![jira]()

