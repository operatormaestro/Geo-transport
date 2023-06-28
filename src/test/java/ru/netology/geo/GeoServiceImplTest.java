package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

public class GeoServiceImplTest {
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
    @Test
    public void ByIpTest() {

        //arrange

        GeoServiceImpl geoService = new GeoServiceImpl();

        //act

        Location location = geoService.byIp(localhost);
        Location location1 = geoService.byIp(moscowIp);
        Location location2 = geoService.byIp(newYorkIP);
        Location location3 = geoService.byIp(someMoscowIp);
        Location location4 = geoService.byIp(someNewYorkIp);

        //assert

        Assertions.assertEquals(localhostLoc, location);
        Assertions.assertEquals(moscowLoc, location1);
        Assertions.assertEquals(newYorkLoc, location2);
        Assertions.assertEquals(someMoscowLoc, location3);
        Assertions.assertEquals(someNewYorkLoc, location4);

    }

}
