package edu.udcs.udromeapp.currency.model;

import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Created by KatherineMJB on 11/12/2015.
 */
public class ConversionHelper {

    private UUID mId;
    private String mCurrencyFrom;
    private Currency mFrom;
    private Currency mTo;
    private String mCurrencyTo;
    private DateTime mAsOfDate;
    private double mAmtFrom;
    private double mAmtTo;
    private double mRate;

    public ConversionHelper(){
        mId = UUID.randomUUID();
    }
    public ConversionHelper(UUID id){
        mId = id;
    }

    public void setCurrencyFrom(String cFrom){
        mCurrencyFrom = cFrom;
        mFrom = new Currency(cFrom);
    }
    public String getCurrencyFrom(){
        return mCurrencyFrom;
    }

    public void setAmtFrom(Double amt){
        mAmtFrom = amt;
        convert(amt);
    }
    public double getAmtFrom(){
        return mAmtFrom;
    }
    public String getTxtAmtFrom(){
        return String.valueOf(mAmtFrom);
    }

    public void setCurrencyTo(String cTo){
        mCurrencyTo = cTo;
    }
    public String getCurrencyTo(){
        return mCurrencyTo;
    }

    private void setAmtTo(double amt){
        mAmtTo = amt;
    }
    public double getAmtTo(){
        return mAmtTo;
    }
    public String getTxtAmtTo(){
        return String.valueOf(convert());
    }

    public void setAsOfDate(DateTime date){
        mAsOfDate = date;
    }
    public DateTime getAsOfDate(){
        return mAsOfDate;
    }

    public void setRate(double rate){
        mRate = rate;
    }
    public double setRate(){
        if (mFrom == null || mTo == null) mRate = 0;
        else mRate = mTo.getRate()/mFrom.getRate();
        return mRate;
    }
    public double getRate(){
        return mRate;
    }

    public double convert(double amount){
        setAmtTo(amount * mRate);
        return mAmtTo;
    }

    public double convert(){
        setAmtTo(getAmtFrom() * setRate());
        return mAmtTo;
    }

    public boolean swap(){
        if(mCurrencyFrom == null)
            return false;
        if(mCurrencyFrom.equals(mCurrencyTo))
            return false;
        String tmpS = mCurrencyFrom;
        Currency tmpC = mFrom;
        double tmpD = mAmtFrom;

        mCurrencyFrom = mCurrencyTo;
        mFrom = mTo;
        mAmtFrom = mAmtTo;
        mCurrencyTo = tmpS;
        mTo = tmpC;
        mAmtTo = tmpD;
        mRate = 1/mRate;
        return true;
    }
}
