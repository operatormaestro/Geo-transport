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
    String localhost = GeoServiceImpl.LOCALHOST;
    String moscowIp = GeoServiceImpl.MOSCOW_IP;
    String newYorkIP = GeoServiceImpl.NEW_YORK_IP;
    String someMoscowIp = "172.255.255.255";
    String someNewYorkIp = "96.255.255.255";

    Location localhostLoc = new Location(null, null, null, 0);
    Location moscowLoc = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
    Location newYorkLoc = new Location("New York", Country.USA, " 10th Avenue", 32);
    Location someMoscowLoc = new Location("Moscow", Country.RUSSIA, null, 0);
    Location someNewYorkLoc = new Location("New York", Country.USA, null, 0);
    String messageRU = "Добро пожаловать";
    String messageEN = "Welcome";

    @Test
    public void sendTest() {

        //arrange

        Map<String, String> headers = new HashMap<>();
        Map<String, String> headers1 = new HashMap<>();
        Map<String, String> headers2 = new HashMap<>();
        Map<String, String> headers3 = new HashMap<>();
        Map<String, String> headers4 = new HashMap<>();
        Map<String, String> headers5 = new HashMap<>();

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, localhost);
        headers1.put(MessageSenderImpl.IP_ADDRESS_HEADER, moscowIp);
        headers2.put(MessageSenderImpl.IP_ADDRESS_HEADER, newYorkIP);
        headers3.put(MessageSenderImpl.IP_ADDRESS_HEADER, someMoscowIp);
        headers4.put(MessageSenderImpl.IP_ADDRESS_HEADER, someNewYorkIp);
        headers5.put(null, null);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        Mockito.when(localizationService.locale(Country.BRAZIL)).thenReturn("Welcome");
        Mockito.when(localizationService.locale(Country.GERMANY)).thenReturn("Welcome");
        Mockito.when(localizationService.locale(null)).thenReturn("Welcome");

        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(localhost)).thenReturn(localhostLoc);
        Mockito.when(geoService.byIp(moscowIp)).thenReturn(moscowLoc);
        Mockito.when(geoService.byIp(newYorkIP)).thenReturn(newYorkLoc);
        Mockito.when(geoService.byIp(someMoscowIp)).thenReturn(someMoscowLoc);
        Mockito.when(geoService.byIp(someNewYorkIp)).thenReturn(someNewYorkLoc);
        Mockito.when(geoService.byIp(null)).thenReturn(null);
        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        //act

        String result = messageSender.send(headers);
        String result1 = messageSender.send(headers1);
        String result2 = messageSender.send(headers2);
        String result3 = messageSender.send(headers3);
        String result4 = messageSender.send(headers4);

        //assert

        Assertions.assertEquals(messageEN, result);
        Assertions.assertEquals(messageRU, result1);
        Assertions.assertEquals(messageEN, result2);
        Assertions.assertEquals(messageRU, result3);
        Assertions.assertEquals(messageEN, result4);
        Assertions.assertThrows(NullPointerException.class, () -> messageSender.send(headers5));
    }
}