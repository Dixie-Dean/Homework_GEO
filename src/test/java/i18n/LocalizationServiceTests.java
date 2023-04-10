package i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

import static ru.netology.entity.Country.BRAZIL;

public class LocalizationServiceTests {
    @ParameterizedTest
    @MethodSource("countrySource")
    public void localeTest(Country country, String expected) {
        LocalizationServiceImpl localizationService = new  LocalizationServiceImpl();

        String result = localizationService.locale(country);

        Assertions.assertEquals(expected, result);
    }

    public static Stream<Arguments> countrySource() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome"),
                Arguments.of(BRAZIL, "Welcome")
        );
    }
}
