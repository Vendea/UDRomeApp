package edu.udcs.udromeapp.currency.model;

import java.util.UUID;

/**
 * Created by KatherineMJB on 11/11/2015.
 */
public class Country {
    private UUID mId;
    private String mCode;
    private String mName;
    private String mPrimaryCurrencyCode;

    public Country(){
        mId = UUID.randomUUID();
    }
    public Country(String name){
        mName = name;
        //mCode = JSONParser.parseCountryCode(mName);
        //mPrimaryCurrencyCode = JSONParser.parsePrimaryCurrency(mName);
    }

    public Country(UUID id){
        mId = id;
    }
    public UUID getId(){
        return mId;
    }

    public void setCode(String code){
        mCode = code;
    }
    public String getCode(){
        return mCode;
    }

    public void  setName(String name){
        mName = name;
    }
    public String getName(){
        return mName;
    }

    public void setPrimaryCurrency(String name){
        mPrimaryCurrencyCode = name;
    }
    public String getPrimaryCurrency(){
        return mPrimaryCurrencyCode;
    }
}
