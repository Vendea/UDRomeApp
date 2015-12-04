package edu.udcs.udromeapp.currency.model.database;

public class CurrencyDbSchema {
    public static final class CurrenciesTable{
        public static final String NAME = "currency_codes";

        public static final class Cols{
            public static final String CURRENCY_CODE = "ThreeLetterID";
            public static final String FULL_NAME = "currencyName";
            public static final String RATE = "conversionRate";
            public static final String AS_OF_DATE = "asOfDate";
        }
    }
    public static final class CountriesTable{
        public static final String NAME = "country_codes";

        public static final class Cols{
            public static final String COUNTRY_CODE = "ThreeLetterID";
            public static final String FULL_NAME = "countryName";
            public static final String MAIN_CURRENCY = "mainCurrency";
        }
    }
    //join table here
    public static final class CurrenciesToCountries{
        public static final String NAME = "currencies_to_countries";

        public static final class Cols{
            public static final String CURRENCY_CODE = "ThreeLetterIDCurrency";
            public static final String CURRENCY_NAME = "currency_name";
            public static final String COUNTRY_CODE = "ThLetterIDCountry";
            public static final String COUNTRY_NAME = "countryName";
        }
    }
}
