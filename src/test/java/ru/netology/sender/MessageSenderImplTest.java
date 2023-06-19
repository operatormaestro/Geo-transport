package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderImplTest {
    @Test
    public void sendTest() {
        Map<String, String> headers = new HashMap<>();
        Map<String, String> headers1 = new HashMap<>();
        Map<String, String> headers2 = new HashMap<>();
        Map<String, String> headers3 = new HashMap<>();
        Map<String, String> headers4 = new HashMap<>();
        Map<String, String> headers5 = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.LOCALHOST);
        headers1.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.MOSCOW_IP);
        headers2.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.NEW_YORK_IP);
        headers3.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.255.255.255");
        headers4.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.255.255.255");
        headers5.put(null, null);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        Mockito.when(localizationService.locale(Country.BRAZIL)).thenReturn("Welcome");
        Mockito.when(localizationService.locale(Country.GERMANY)).thenReturn("Welcome");
        Mockito.when(localizationService.locale(null)).thenReturn("Welcome");
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(GeoServiceImpl.LOCALHOST)).thenReturn(new Location(null, null, null, 0));
        Mockito.when(geoService.byIp(GeoServiceImpl.MOSCOW_IP)).thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
        Mockito.when(geoService.byIp(GeoServiceImpl.NEW_YORK_IP)).thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));
        Mockito.when(geoService.byIp("172.255.255.255")).thenReturn(new Location("Moscow", Country.RUSSIA,  null, 0));
        Mockito.when(geoService.byIp("96.255.255.255")).thenReturn(new Location("New York", Country.USA,  null, 0));
        Mockito.when(geoService.byIp(null)).thenReturn(null);
        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        String expectedRU = "Добро пожаловать";
        String expectedEN = "Welcome";

        String result = messageSender.send(headers);
        String result1 = messageSender.send(headers1);
        String result2 = messageSender.send(headers2);
        String result3 = messageSender.send(headers3);
        String result4 = messageSender.send(headers4);



        Assertions.assertEquals(expectedEN, result);
        Assertions.assertEquals(expectedRU, result1);
        Assertions.assertEquals(expectedEN, result2);
        Assertions.assertEquals(expectedRU, result3);
        Assertions.assertEquals(expectedEN, result4);
        Assertions.assertThrows(NullPointerException.class,() -> messageSender.send(headers5));
    }
}
