package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;

public class LocalizationServiceImplTest {
    String messageRU = "Добро пожаловать";
    String messageEN = "Welcome";
    @Test
    public void localeTest() {

        //arrange

        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

        //act

        String expect = localizationService.locale(Country.RUSSIA);
        String expect1 = localizationService.locale(Country.USA);
        String expect2 = localizationService.locale(Country.GERMANY);
        String expect3 = localizationService.locale(Country.BRAZIL);

        //assert

        Assertions.assertEquals(expect, messageRU);
        Assertions.assertEquals(expect1, messageEN);
        Assertions.assertEquals(expect2, messageEN);
        Assertions.assertEquals(expect3, messageEN);
    }
}
