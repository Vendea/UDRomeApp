package edu.udcs.udromeapp.currency.model;

import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Created by KatherineMJB on 11/11/2015.
 */
public class Currency {
    private UUID mId;
    private String mCode;
    private String mName;
    private double mRate;
    private Long mAsOfDate;

    public Currency(){
        mId = UUID.randomUUID();
    }
    public Currency(String name){
        mId = UUID.randomUUID();
        mName = name;
        //setCode(JSONParser.parseCurrCode(mName));
        //setRate(JSONParser.parseRate(mCode));
    }
    public Currency(UUID id){
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

    public void setRate(double rate){
        mRate = rate;
    }
    public double getRate(){
        return mRate;
    }

    public void setAsOfDate(Long dt){
        mAsOfDate = dt;
    }
    public Long getAsOfDate(){
        return mAsOfDate;
    }
}