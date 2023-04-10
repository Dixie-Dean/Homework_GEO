package sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderTests {

    @ParameterizedTest
    @MethodSource("headersSource")
    public void sendTest(String ip, Location location, String expected) {
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(location);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(location.getCountry()))
                .thenReturn(expected);

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        //act
        String answer = messageSender.send(headers);

        //assert
        Assertions.assertEquals(expected, answer);
    }

    public static Stream<Arguments> headersSource() {
        return Stream.of(
                Arguments.of("172.0.32.11",
                        new Location("Moscow", Country.RUSSIA, "Lenina", 15),
                        "Добро пожаловать"),
                Arguments.of("96.44.183.149",
                        new Location("New York", Country.USA, " 10th Avenue", 32),
                        "Welcome")
        );
    }
}