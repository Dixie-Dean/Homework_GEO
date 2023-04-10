package geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class GeoServiceTests {
    GeoServiceImpl geoService = new GeoServiceImpl();

    @ParameterizedTest
    @MethodSource("ipSource")
    public void byIdTest(String ip, Location expected) {
        Location locationResult = geoService.byIp(ip);

        if (ip.equals(" ")) {
            Assertions.assertNull(locationResult);
        } else {
            Assertions.assertEquals(expected.getCity(), locationResult.getCity());
            Assertions.assertEquals(expected.getCountry(), locationResult.getCountry());
            Assertions.assertEquals(expected.getStreet(), locationResult.getStreet());
            Assertions.assertEquals(expected.getBuiling(), locationResult.getBuiling());
        }
    }

    public static Stream<Arguments> ipSource() {
        return Stream.of(
                Arguments.of("127.0.0.1",
                        new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11",
                        new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149",
                        new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.",
                        new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.",
                        new Location("New York", Country.USA, null,  0)),
                Arguments.of(" ", null)
        );
    }

    @Test
    public void byCoordinatesTest() {
        double latitude = 40.7128, longitude = 74.0060;

        Executable executable = () -> geoService.byCoordinates(latitude, longitude);

        Assertions.assertThrows(RuntimeException.class, executable);
    }

}
