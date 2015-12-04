package edu.udcs.udromeapp.currency;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.udcs.udromeapp.currency.model.Country;
import edu.udcs.udromeapp.currency.model.Currency;
import edu.udcs.udromeapp.currency.model.database.CurrencyDbSchema.CurrenciesTable;
import edu.udcs.udromeapp.currency.model.database.CurrencyDbSchema.CountriesTable;
import edu.udcs.udromeapp.currency.model.database.DataBaseHelper;
import edu.udcs.udromeapp.currency.model.database.DataCursorWrapper;

/**
 * Created by kbeine on 11/13/15.
 */
public class CurrencyLabeling {

    private static CurrencyLabeling sCurrencyLabeling;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private CurrencyLabeling(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DataBaseHelper(mContext).getWritableDatabase();
    }

    public static CurrencyLabeling get(Context context) {
        if (sCurrencyLabeling == null) {
            sCurrencyLabeling = new CurrencyLabeling(context);
        }
        return sCurrencyLabeling;
    }

    private static ContentValues getContentValues(Currency currency) {
        ContentValues values = new ContentValues();
        values.put(CurrenciesTable.Cols.CURRENCY_CODE, currency.getCode());
        values.put(CurrenciesTable.Cols.FULL_NAME, currency.getName());
        values.put(CurrenciesTable.Cols.RATE, currency.getRate());
        values.put(CurrenciesTable.Cols.AS_OF_DATE, currency.getAsOfDate());
        return values;
    }

    private static ContentValues getContentValues(Country country) {
        ContentValues values = new ContentValues();
        values.put(CountriesTable.Cols.COUNTRY_CODE, country.getCode());
        values.put(CountriesTable.Cols.FULL_NAME, country.getName());
        values.put(CountriesTable.Cols.MAIN_CURRENCY, country.getPrimaryCurrency());
        return values;
    }

    public void addCurrency(Currency c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CurrenciesTable.NAME, null, values);
    }

    public void addCountry(Country c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CountriesTable.NAME, null, values);
    }

    public void updateCurrency(Currency c) {
        String codeString = c.getCode();
        ContentValues values = getContentValues(c);
        mDatabase.update(CurrenciesTable.NAME, values,
                CurrenciesTable.Cols.CURRENCY_CODE + " = ?",
                new String[]{codeString});
    }

    private DataCursorWrapper queryCurrencies(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CurrenciesTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new DataCursorWrapper(cursor);
    }

    private DataCursorWrapper queryCountries(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CountriesTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new DataCursorWrapper(cursor);
    }

    public List<Currency> getCurrencies() {
        List<Currency> tasks = new ArrayList<>();

        DataCursorWrapper cursor = queryCurrencies(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tasks.add(cursor.getCurrency());
            cursor.moveToNext();
        }
        cursor.close();

        return tasks;
    }

    public List<String> getCurrencyNames() {
        List<String> names = new ArrayList<>();
        List<Currency> currencies = getCurrencies();

        for(Currency v : currencies){
            names.add(v.getName());
        }
        return names;
    }

    public List<Double> getRates(){
        List<Double> rates = new ArrayList<>();

        DataCursorWrapper cursor = queryCurrencies(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            rates.add(cursor.getRate());
            cursor.moveToNext();
        }
        cursor.close();
        return rates;
    }

    public long getDate(){
        DataCursorWrapper cursor = queryCurrencies(
                CurrenciesTable.Cols.CURRENCY_CODE + " = ?",
                new String[]{"USD".toString()});

        try {
            if (cursor.getCount() == 0) {
                return 1319730758;
            }

            cursor.moveToFirst();
            return cursor.getCurrency().getAsOfDate();
        } finally {
            cursor.close();
        }
    }

    public List<Country> getCountries() {
        List<Country> countries = new ArrayList<>();

        DataCursorWrapper cursor = queryCountries(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            countries.add(cursor.getCountry());
            cursor.moveToNext();
        }
        cursor.close();

        return countries;
    }

    public Country getCountry(String name){
        DataCursorWrapper cursor = queryCountries(
                CountriesTable.Cols.FULL_NAME + " = ?",
                new String[]{name.toString()});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCountry();
        } finally {
            cursor.close();
        }
    }

    public Currency getCurrency(String code){
        DataCursorWrapper cursor = queryCurrencies(
                CurrenciesTable.Cols.CURRENCY_CODE + " = ?",
                new String[]{code.toString()});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCurrency();
        } finally {
            cursor.close();
        }
    }

    public List<String> getCountryNames(){
        List<String> names = new ArrayList<>();
        List<Country> countries = getCountries();

        for(Country v : countries){
            names.add(v.getName());
        }
        return names;
    }
}