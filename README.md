# Автотесты API для сайта https://demoqa.com

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

Запуск тестов происходит через джобу в [Jenkins](https://jenkins.autotests.cloud/job/009-Andrey_Zhmaka-DemoQaAPI/)


### Serve report:

```bash
allure serve build/allure-results
```

## Оповещение о результатах прохождения тестов через бот в телеграмм

![Telegram](images/telegram_notifi.png)

## Анализ результатов

Более подробно с результатми тестов можно ознакомиться в:

* Jenkins через Allure Reports или Allure TestOps

### Анализ результатов в Jenkins через Allure Reports

![alt "Allure Reports"](images/allure_report_notifi.png)
![alt "Allure Reports"](images/allure_report_notifi2.png)

### Анализ результатов в Allure TestOps

![alt "Allure TestOps"](images/testOps_notifi.png)


