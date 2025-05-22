# autotest-Diadoc
```shell
C:\Users\Zm500\IdeaProjects\autotest-diadoc\gradlew.bat clean test allureReport

```
# Контур.Диадок: Автотесты для подписания документов (Java)

[![Java](https://img.shields.io/badge/Java-11%2B-blue)](https://www.java.com/)
[![Selenium](https://img.shields.io/badge/Selenium-4.0%2B-orange)](https://www.selenium.dev/)
[![JUnit5](https://img.shields.io/badge/JUnit5-5.8%2B-green)](https://junit.org/junit5/)
[![Gradle](https://img.shields.io/badge/Gradle-7.5%2B-yellow)](https://gradle.org/)

Автоматизация подписания документов во входящих сообщениях Контур.Диадок на Java.

## 📝 Описание
Проект содержит 2 UI-теста:
1. **Базовый тест** — проверка сценария подписания документов (`AuthTest.java`)
2. **Параметризованный тест** — запуск с разными параметрами (ИНН пользователя)

**Основной сценарий:**
- Авторизация через ЭЦП с плагином Контур.Крипто
- Фильтрация входящих документов ("Требуется подпись")
- Подпись доступных документов с текущей датой
- Корректный выход из системы
- Автоматические ожидания элементов интерфейса

## 🛠 Технологии
- **Selenium WebDriver** — управление браузером Chrome
- **JUnit 5** — фреймворк для тестирования
- **WebDriverManager** — автоматическое управление драйверами
- **Gradle** — система сборки
- **Page Object Pattern** — организация тестового кода

## 🗂 Структура проекта
.
├── .idea/
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── src/
│   ├── main/
│   │   └── java/
│   │       
│   └── test/
│       └── java/
│           └── diadoc/
│               └── diadoc/
│                      ├── page/ 
│                      │   ├── LoginPage.java
│                      │   └── InBoxPage.java
│                      ├── DiadocSteps.java
│                      └── AuthTest.java
├── resources/
│   └── junit-platform.properties
├── .gitignore
├── build.gradle
├── gradlew
└── gradlew.bat
## 🚀 Запуск
1. Соберите проект:
```bash
./gradlew clean build
Запустите все тесты:

bash
./gradlew test
Для параметризованного теста (пример):

java
// В коде теста укажите параметры:
Путь к папке браузера
String userDataDir = "C:\\wd\\User Data";
String profileName = "Default"; 

@ParameterizedTest
@CsvSource(value = {
            "inn1", //Имя пользователя1
            "inn2", //Имя пользователя2
            })

⚙️ Конфигурация
Настройте параметры в src/test/resources/junit-platform.properties:

properties
diadoc.url=https://diadoc.kontur.ru
diadoc.inn=ВАШ_ИНН
diadoc.cert.path=/path/to/certificate.p12
Требования к окружению:

Java 11+

Chrome 90+

Установленный плагин Контур.плагин

Установите сертификат ЭП в личные сертификаты пользователя

⚠️ Важно
Используйте тестовый аккаунт

Не запускайте тесты на продакшен-окружении

Обновите пути к сертификатам в конфигурации