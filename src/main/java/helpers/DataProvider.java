package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class DataProvider {

    public static Stream<Arguments> provideData() {
        return Stream.of(
                Arguments.of("Маркет", "Электроника", "Смартфоны",
                "Производитель", "Apple", "iPhone"
        ));
    }
}
