package edu.udcs.udromeapp.currency;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import android.content.Intent;

import org.joda.time.DateTime;
import org.w3c.dom.Text;

import java.util.List;

import edu.udcs.udromeapp.R;
import edu.udcs.udromeapp.currency.model.ConversionHelper;
import edu.udcs.udromeapp.currency.model.Country;
import edu.udcs.udromeapp.currency.model.Currency;
import edu.udcs.udromeapp.currency.model.JSONParser;
import edu.udcs.udromeapp.currency.model.LocationServices;

public class ConverterFragment extends Fragment {
    private static final String ARG_CONVERTER_ID = "converter_id";

    private Context mContext;
    private ConversionHelper mConverter;
    private EditText mNum_currencyFrom;
    private EditText mNum_currencyTo;
    private Spinner mSpinnerCurrencyFrom;
    private List<String> mCurrenciesFrom;
    private Spinner mSpinnerCurrencyTo;
    private List<String> mCurrenciesTo;
    private Button mSwapButton;
    private Button mViewAllButton;
    private TextView mYouAreHere;
    private TextView mHereCurrencyView;
    private TextView mLastUpdatedString;
    private TextView mLastUpdatedView;
    private Button mRefreshButton;
    private Country mCountry;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.converter_fragment, container, false);

        mContext = getActivity();
        CurrencyLabeling cl = CurrencyLabeling.get(mContext);
        mConverter = new ConversionHelper();
        new UpdateTask().execute();

        mSpinnerCurrencyFrom = (Spinner) v.findViewById(R.id.spinner_currency_from);
        mSpinnerCurrencyTo = (Spinner) v.findViewById(R.id.spinner_currency_to);

        mCurrenciesFrom = cl.getCurrencyNames();
        ArrayAdapter<String> spinnerFromVals = new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item, mCurrenciesFrom);
        spinnerFromVals.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCurrencyFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mConverter.setCurrencyFrom(mCurrenciesTo.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCurrenciesTo = cl.getCurrencyNames();
        ArrayAdapter<String> spinnerToVals = new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item, mCurrenciesTo);
        spinnerToVals.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCurrencyTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mConverter.setCurrencyTo(mCurrenciesTo.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mNum_currencyFrom = (EditText) v.findViewById(R.id.num_currency_from);
        mNum_currencyFrom.setText("1.00");
        mConverter.setAmtFrom(1.00);

        /**
         * get rid of the try catch block once you load the actual currencies
         */
        try{mConverter.setCurrencyFrom(mSpinnerCurrencyFrom.getSelectedItem().toString());
        mConverter.setCurrencyTo(mSpinnerCurrencyTo.getSelectedItem().toString());}
        catch(NullPointerException e){
            e.printStackTrace();
        }
        mNum_currencyTo = (EditText) v.findViewById(R.id.num_currency_to);
        mNum_currencyTo.setEnabled(false);
        mNum_currencyTo.setText(mConverter.getTxtAmtTo());
        mNum_currencyFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    mConverter.setAmtFrom(Double.parseDouble(s.toString()));
                    mNum_currencyTo.setText(mConverter.getTxtAmtTo());
                } catch (java.lang.NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSwapButton = (Button) v.findViewById(R.id.swap_button);
        mSwapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConverter.swap()) {
                    mNum_currencyTo.setText(mConverter.getTxtAmtTo());
                    mNum_currencyFrom.setText(mConverter.getTxtAmtFrom());
                }
            }
        });

        mViewAllButton = (Button) v.findViewById(R.id.view_all_button);
        mViewAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CurrencyPagerActivity.class);
                startActivity(intent);
            }
        });

        LocationServices ls = LocationServices.get(mContext);
        mYouAreHere = (TextView) v.findViewById(R.id.you_are_here_view);
        String mHereText = "You are in " + ls.getLocationChars();
        System.out.println("*******************" + mHereText + "******************");
        mYouAreHere.setText(mHereText);
        mCountry = cl.getCountry(mYouAreHere.getText().toString());

        mHereCurrencyView = (TextView) v.findViewById(R.id.here_currency_view);
        Currency mHereCurrency;
        try {
            mHereCurrency = cl.getCurrency(mCountry.getPrimaryCurrency());
        }catch(NullPointerException e){
            mHereCurrency = new Currency("US Dollar");
            mHereCurrency.setRate(1.00);
            mHereCurrency.setCode("USD");
            e.printStackTrace();
        }
        String mCurrencyText = ls.getLocationChars() + "'s currency is the " +
                mHereCurrency.getName();
        mHereCurrencyView.setText(mCurrencyText);

        mLastUpdatedString = (TextView) v.findViewById(R.id.last_updated_string);
        mLastUpdatedView = (TextView) v.findViewById(R.id.last_updated_view);
        mLastUpdatedView.setText(new DateTime(cl.getDate()).toString("d MMMM, yyyy"));

        mRefreshButton = (Button) v.findViewById(R.id.refresh_button);
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateTask().execute();
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_currency, menu);
    }

    private class UpdateTask extends AsyncTask<String, String, String> {
        JSONParser jsonParser = JSONParser.getParser(mContext);
                
        @Override
        protected String doInBackground(String... params) {
            jsonParser.getLatest();
            jsonParser.populateCountriesDatabase();
            jsonParser.populateCurrenciesDatabase();
            return jsonParser.parseDate();
        }
    }
}