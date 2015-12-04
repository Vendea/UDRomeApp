package edu.udcs.udromeapp.currency.model.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import org.joda.time.DateTime;

import edu.udcs.udromeapp.currency.model.Country;
import edu.udcs.udromeapp.currency.model.Currency;
import edu.udcs.udromeapp.currency.model.ConversionHelper;
import edu.udcs.udromeapp.currency.model.database.CurrencyDbSchema.CurrenciesTable;
import edu.udcs.udromeapp.currency.model.database.CurrencyDbSchema.CurrenciesTable;
import edu.udcs.udromeapp.currency.model.database.CurrencyDbSchema.CountriesTable;


import java.util.Date;
import java.util.UUID;

public class DataCursorWrapper extends CursorWrapper {

    public DataCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Country getCountry() {
        String country_code = getString(getColumnIndex(CountriesTable.Cols.COUNTRY_CODE));
        String full_name = getString(getColumnIndex(CountriesTable.Cols.FULL_NAME));
        String main_currency = getString(getColumnIndex(CountriesTable.Cols.MAIN_CURRENCY));

        Country country = new Country();
        country.setCode(country_code);
        country.setName(full_name);
        country.setPrimaryCurrency(main_currency);

        return country;
    }
    public Currency getCurrency() {
        String currency_code = getString(getColumnIndex(CurrenciesTable.Cols.CURRENCY_CODE));
        String full_name = getString(getColumnIndex(CurrenciesTable.Cols.FULL_NAME));
        Double rate = getDouble(getColumnIndex(CurrenciesTable.Cols.RATE));
        Long asOfDate = getLong(getColumnIndex(CurrenciesTable.Cols.AS_OF_DATE));

        Currency currency = new Currency();
        currency.setCode(currency_code);
        currency.setName(full_name);
        currency.setRate(rate);
        currency.setAsOfDate(asOfDate);

        return currency;
    }

    /**
     * move into currencyLabeling
     * @return
     */
    public Double getRate(){
        return getDouble(getColumnIndex(CurrenciesTable.Cols.RATE));
    }
}
