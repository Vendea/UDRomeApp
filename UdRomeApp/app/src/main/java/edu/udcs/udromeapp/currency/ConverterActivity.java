package edu.udcs.udromeapp.currency;

import android.support.v4.app.Fragment;

import edu.udcs.udromeapp.currency.model.SingleFragmentActivity;

public class ConverterActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new ConverterFragment();
    }
}