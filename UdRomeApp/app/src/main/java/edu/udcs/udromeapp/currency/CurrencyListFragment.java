package edu.udcs.udromeapp.currency;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.udcs.udromeapp.R;
import edu.udcs.udromeapp.currency.model.ConversionHelper;
import edu.udcs.udromeapp.currency.model.Currency;

public class CurrencyListFragment extends Fragment {
    private Currency mBaseCurrency;
    private ConversionHelper mConverter;

    private RecyclerView mCurrencyRecyclerView;
    private CurrencyAdapter mAdapter;

    /**public static CurrencyListFragment newInstance() {
        CurrencyListFragment fragment = new CurrencyListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.currency_list_fragment, container, false);

        mCurrencyRecyclerView = (RecyclerView) view.findViewById(R.id.currency_recycler_view);
        mCurrencyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void updateUI() {
        CurrencyLabeling currencyLabeling = CurrencyLabeling.get(getActivity());
        List<Currency> currencies = currencyLabeling.getCurrencies();

        if (mAdapter == null) {
            mAdapter = new CurrencyAdapter(currencies);
            mCurrencyRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCurrencies(currencies);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class CurrencyHolder extends RecyclerView.ViewHolder{

        private TextView mTitleTextView;
        private TextView mRateTextView;
        private TextView mNameTextView;
        private Currency mCurrency;

        public CurrencyHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_currency_code);
            mNameTextView = (TextView) itemView.findViewById(R.id.list_currency_name);
            mRateTextView = (TextView) itemView.findViewById(R.id.list_currency_amt);
        }

        public void bindCurrency(Currency currency) {
            mCurrency = currency;
            mTitleTextView.setText(mCurrency.getCode());
            mNameTextView.setText(mCurrency.getName());
            mRateTextView.setText(String.valueOf(mCurrency.getRate())
                    + mCurrency.getCode());
        }
    }

    private class CurrencyAdapter extends RecyclerView.Adapter<CurrencyHolder> {

        private List<Currency> mCurrencies;

        public CurrencyAdapter(List<Currency> currencies) {
            mCurrencies = currencies;
        }

        @Override
        public CurrencyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.currency_list_element, parent, false);
            return new CurrencyHolder(view);
        }

        @Override
        public void onBindViewHolder(CurrencyHolder holder, int position) {
            Currency currency = mCurrencies.get(position);
            holder.bindCurrency(currency);
        }

        @Override
        public int getItemCount() {
            return mCurrencies.size();
        }

        public void setCurrencies(List<Currency> currencies) {
            mCurrencies = currencies;
        }
    }
}
