
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CardDeliveryOrderSelenideTest {

    @BeforeEach
    public void setUp() {
        Configuration.headless = true;
        open("http://localhost:9999");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys("DELETE");

    }

   public String formatPlus(long day) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate newDay = today.plusDays(day);
        return newDay.format(formatter);
        } 

    // >>>>>>>>>>>>ФУНКЦИОНАЛЬНАЯ ОТПРАВКА ФОРМЫ<<<<<<<<<<<<

    @Test // Успешное оформление заявки
    public void validTestData() throws InterruptedException { // правильный город
        $("[data-test-id='city'] input").setValue("Горно-Алтайск");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Кин Йян");
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + addToDate()));
        $(withText("Успешно!")).shouldBe(visible);
    }

    // >>>>>>>>>>>>ПОЛЕ ДЛЯ ВВОДА ГОРОДА<<<<<<<<<<<<

    @Test // Один из административных центров субъектов РФ. - Кемерово
    public void theRightCity() throws InterruptedException { // правильный город
        $("[data-test-id='city'] input").setValue("Кемерово");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Кин Йян");
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + formatPlus(4)));
        $(withText("Успешно!")).shouldBe(visible);
    }

    @Test  // НЕ один из административных центров - Калмыкия
    public void wrongСity() throws InterruptedException { //не правильный город
        $("[data-test-id='city'] input").setValue("Калмыкия");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Кин Йян");
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(byText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }

    @Test //  НЕ один из административных центров, город с дефисом в названии- Карачаево-Черкесия
    public void wrongСity1() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Карачаево-Черкесия");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Кин Йян");
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(byText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }

    @Test  // Буква й в центре Горно-Алтайск
    public void letterTh() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Горно-Алтайск");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Кин Йян");
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + formatPlus(4)));
        $(withText("Успешно!")).shouldBe(visible);
    }

    @Test // Буква ё в городе
    public void letterE() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Орёл");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Кин Йян");
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + formatPlus(4)));
        $(withText("Успешно!")).shouldBe(visible);
    }

    @Test // Город содержаший пробел в названии Нижний Новгород
    public void space() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Кин Йян");
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + formatPlus(4)));
        $(withText("Успешно!")).shouldBe(visible);
    }

    @Test // Не заполненное поле городом
    public void spaceInput() throws InterruptedException {
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Киндер Ям");
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    // >>>>>>>>>>>>ПОЛЕ ДЛЯ ВВОДА ДАТЫ<<<<<<<<<<<<

    @Test // Не заполненной дата доставки
    public void nullDate() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Горно-Алтайск");
        $("[data-test-id='name'] input").setValue("Ким Йан");
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(byText("Неверно введена дата")).shouldBe(visible);
    }


    @Test // Дата доставки менее 3-х дней
    public void dateMin3Days() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Горно-Алтайск");
        $("[data-test-id='date'] input").setValue(formatPlus(1));
        $("[data-test-id='name'] input").setValue("Ким Йан");
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(byText("Заказ на выбранную дату невозможен")).shouldBe(visible);
    }

    // >>>>>>>>>>>>ПОЛЕ ДЛЯ ВВОДА ФИО<<<<<<<<<<<<

    @Test // Валидация поля Фамилии с именем содержащее букву ё
    public void letterEName() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Горно-Алтайск");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Ёжкин Сергей");
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible, Duration.ofSeconds(15));
        $(withText("Успешно!")).shouldBe(visible);
    }

    @Test // Фамилия содержащая в себе дефиса
    public void hyphen() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Горно-Алтайск");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Кин Йян-Сергей");
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + formatPlus(4)));
        $(withText("Успешно!")).shouldBe(visible);
    }

    @Test // Ввод фамилии на кириллице
    public void сyrillic() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Горно-Алтайск");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Vavilova Nastya");
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(visible);
    }

    @Test // Ввод фамилии со спецсимволами.
    public void specialСharacters() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Горно-Алтайск");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Вавилова Настя,");
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(visible);
    }

    @Test //Ввод фамилии с буквой й.
    public void letterThSurname() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Горно-Алтайск");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Кин Йян");
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + formatPlus(4)));
        $(withText("Успешно!")).shouldBe(visible);
    }

    @Test  // Не заполнено поле Фамилии
    public void nullSurname() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Горно-Алтайск");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    // >>>>>>>>>>>>ПОЛЕ ДЛЯ ВВОДА ТЕЛЕФОН<<<<<<<<<<<<

    @Test // Отправка заявки с номером начинающимся с цифры 8.
    public void number8() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Горно-Алтайск");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Кин Йян");
        $("[data-test-id='phone'] input").setValue("89499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }

    @Test // Отправка заявки с номером начинающимся с цифры 7
    public void number7() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Горно-Алтайск");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Кин Йян");
        $("[data-test-id='phone'] input").setValue("79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }

    @Test // Отправка заявки с номером более 11 цифр
    public void number12() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Горно-Алтайск");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Кин Йян");
        $("[data-test-id='phone'] input").setValue("+7949999994674");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }

    @Test // Отправка заявки с номером состоящим из менее 11 цифр.
    public void number6() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Горно-Алтайск");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Кин Йян");
        $("[data-test-id='phone'] input").setValue("+7949999674");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }

    @Test // Незаполненно поле телефона
    public void nullNumber() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Горно-Алтайск");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Кин Йян");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test // Отправка формы с незаполненным чек боксом
    public void nullCheckbox() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Горно-Алтайск");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Кин Йян");
        $("[data-test-id='phone'] input").setValue("+79499996744");
        $(".button").click();
        $(byText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).shouldBe(visible);
    }

    // >>>>>>>>>>>>ТАЙМАУТ<<<<<<<<<<<<

    @Test // Время ожидания меньше 15 секунд
    public void timeOut() throws InterruptedException {
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").setValue(formatPlus(4));
        $("[data-test-id='name'] input").setValue("Кин Йян");
        $("[data-test-id='phone'] input").setValue("+79499999944");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible, Duration.ofSeconds(10)).shouldBe(exactText("Встреча успешно забронирована на " + formatPlus(4)));
        $(withText("Успешно!")).shouldBe(visible);
    }
}
