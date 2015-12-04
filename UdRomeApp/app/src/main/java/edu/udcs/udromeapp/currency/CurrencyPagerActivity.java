package edu.udcs.udromeapp.currency;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

import edu.udcs.udromeapp.R;
import edu.udcs.udromeapp.currency.model.Currency;

/**
 * Created by KatherineMJB on 11/13/2015.
 */
public class CurrencyPagerActivity extends AppCompatActivity{
    private static final String EXTRA_TASK_ID =
            "com.example.kbeine.to_doList3.currency_id";

    private ViewPager mViewPager;
    private List<Currency> mCurrencies;
    private List<Double> mRates;

    /**public static Intent newIntent(Context packageContext, UUID currencyId) {
        Intent intent = new Intent(packageContext, CurrencyPagerActivity.class);
        intent.putExtra(EXTRA_TASK_ID, currencyId);
        return intent;
    }**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_pager);

        UUID currencyId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_TASK_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_currency_pager_view_pager);

        mCurrencies = CurrencyLabeling.get(this).getCurrencies();
        mRates = CurrencyLabeling.get(this).getRates();
        /**FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Currency currency = mCurrencies.get(position);
                return Currency
            }

            @Override
            public int getCount() {
                return mCurrencies.size();
            }
        });**/

        /**mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Currency currency = mCurrencies.get(position);
                if(currency.getName() != null){
                    setTitle(currency.getName());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });**/
    }
}
