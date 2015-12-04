package edu.udcs.udromeapp.currency.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import edu.udcs.udromeapp.currency.model.database.CurrencyDbSchema.MasterTable;
import edu.udcs.udromeapp.currency.model.database.CurrencyDbSchema.CurrenciesTable;
import edu.udcs.udromeapp.currency.model.database.CurrencyDbSchema.CountriesTable;
import edu.udcs.udromeapp.currency.model.database.CurrencyDbSchema.CurrenciesToCountries;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "CurrencyBase.db";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CurrenciesTable.NAME + "(" +
                        " _id integer primary key autoincrement, " +
                        CurrenciesTable.Cols.CURRENCY_CODE + ", " +
                        CurrenciesTable.Cols.FULL_NAME + ", " +
                        CurrenciesTable.Cols.RATE + ", " +
                        CurrenciesTable.Cols.AS_OF_DATE +
                        ")"
        );

        db.execSQL("create table " + CountriesTable.NAME + "(" +
                        " _id integer primary key autoincrement, " +
                        CountriesTable.Cols.COUNTRY_CODE + ", " +
                        CountriesTable.Cols.FULL_NAME + ", " +
                        CountriesTable.Cols.MAIN_CURRENCY +
                        ")"
        );

        db.execSQL("create table " + CurrenciesToCountries.NAME + "(" +
                        " _id integer primary key autoincrement, " +
                        CurrenciesToCountries.Cols.CURRENCY_CODE + ", " +
                        CurrenciesToCountries.Cols.CURRENCY_NAME + ", " +
                        CurrenciesToCountries.Cols.COUNTRY_CODE + ", " +
                        CurrenciesToCountries.Cols.COUNTRY_NAME +
                        ")"
        );

        db.execSQL("insert into " + CurrenciesToCountries.NAME + " select " +
                        CountriesTable.NAME + "._id" + ", " +
                        CurrenciesTable.NAME + "." + CurrenciesTable.Cols.CURRENCY_CODE + ", " +
                        CurrenciesTable.NAME + "." + CurrenciesTable.Cols.FULL_NAME + ", " +
                        CountriesTable.NAME + "." + CountriesTable.Cols.COUNTRY_CODE + ", " +
                        CountriesTable.NAME + "." + CountriesTable.Cols.FULL_NAME +
                        " from " + CurrenciesTable.NAME +
                        " inner join " + CountriesTable.NAME +
                        " on " + CurrenciesTable.NAME + "." +
                        CurrenciesTable.Cols.CURRENCY_CODE + "=" +
                        CountriesTable.NAME + "." +
                        CountriesTable.Cols.MAIN_CURRENCY
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
