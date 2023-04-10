package ru.netology.i18n;

import ru.netology.entity.Country;

public interface LocalizationService {

    String locale(Country country);
}
