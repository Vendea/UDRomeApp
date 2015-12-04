package edu.udcs.udromeapp.currency.model;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.udcs.udromeapp.currency.CurrencyLabeling;

/**
 * Created by KatherineMJB on 12/1/2015.
 */
public class JSONParser {

    private static JSONParser sjsonParser;
    private Context mContext;
    private CurrencyLabeling sCurrencyLabeling;

    private static final String BASE_URL = "https://openexchangerates.org/api/";
    private static final String API_KEY = "?app_id=de9d7863a7e149519e2db3e4523f0747";
    private static final String LATEST = "latest.json";
    //private static final String CURRENCIES = "currencies.json";
    //private static final String HISTORICAL = "historical/";

    private final String RATES = "rates";
    private final String TIMESTAMP = "timestamp";
    private final String CURR_CODE = "currCode";
    private final String COUNTRY_CODE = "code";
    private final String COUNTRY_NAME = "name";

    private static InputStream is;
    private static JSONArray jsonArray;
    private static String json;
    private int numCountries;

    private static boolean downloadSuccess;
    private static final String SAVED_DATA = "currenciesSavedData.txt";
    private static final String SAVED_CURRENCIES = "currencies.txt";
    private static final String SAVED_COUNTRIES = "countries.txt";

    //public JSONParser(){}
    private JSONParser(Context context){
        mContext = context.getApplicationContext();
        sCurrencyLabeling = CurrencyLabeling.get(mContext);
    }

    public static JSONParser getParser(Context context){
        if(sjsonParser == null) {
            sjsonParser = new JSONParser(context);
        }
        return sjsonParser;
    }

    public JSONObject getLatest(){
        String url = BASE_URL + LATEST + API_KEY;
        return getJSONFromURL(url);
    }

    public JSONObject getJSONFromURL(String url){
        //JSONArray rval = new JSONArray();
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try{
            HttpResponse r = client.execute(httpGet);
            StatusLine sl = r.getStatusLine();
            int statusCode = sl.getStatusCode();

            if(statusCode == 200) {
                downloadSuccess = true;
                HttpEntity entity = r.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while((line = reader.readLine())!= null){
                    builder.append(line);
                }
                FileOutputStream fos = mContext.openFileOutput(SAVED_DATA, mContext.MODE_PRIVATE);
                fos.write(builder.toString().getBytes());
                fos.close();
                //rval = useSavedData();
            }
            else if(statusCode == 304){
                downloadSuccess = true;
               //rval = useSavedData(); //?
            }
            else{
                downloadSuccess = false;
                //rval = useSavedData(); //?
            }
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return useSavedData(); //?
    }

    public JSONObject useSavedData(){
        JSONObject rval;
        try {
            StringBuilder sb = new StringBuilder();
            FileInputStream fis = mContext.openFileInput(SAVED_DATA);
            int content;
            while((content = fis.read()) != -1){
                sb.append((char) content);
            }
            rval = new JSONObject(sb.toString());
        } catch (IOException e){
            e.printStackTrace();
            rval = null;
        } catch(JSONException e){
            e.printStackTrace();
            rval = null;
        }
        return rval;
    }

    public JSONObject getCurrencyNames(){
        InputStream is;
        JSONObject jsa = new JSONObject();
        try {
            is = mContext.getApplicationContext().getAssets().open(SAVED_CURRENCIES);
            int content;
            StringBuilder sb = new StringBuilder();
            while((content = is.read()) != -1){
                sb.append((char) content);
            }
            jsa = new JSONObject(sb.toString());
        } catch (IOException e){
            e.printStackTrace();
        } catch(JSONException e){
            e.printStackTrace();
        }
        return jsa;
    }

    public void populateCountriesDatabase(){
        InputStream is;
        JSONObject jso;
        if(!sCurrencyLabeling.getCountries().isEmpty())
            return;
        try {
            is = mContext.getApplicationContext().getAssets().open(SAVED_COUNTRIES);
            int content;
            StringBuilder sb = new StringBuilder();
            while((content = is.read()) != -1){
                sb.append((char) content);
            }
            jso = new JSONObject(sb.toString());

            for (int i = 1; i < numCountries; i++){
                String str = String.valueOf(i);
                JSONObject vals = jso.getJSONObject(str);
                Country addC = new Country();
                addC.setPrimaryCurrency(vals.getString(CURR_CODE));
                System.out.println("***********************Countries populating***************");
                System.out.println("***********************" + addC.getPrimaryCurrency() + "***************");
                addC.setCode(vals.getString(COUNTRY_CODE));
                System.out.println("***********************" + addC.getCode() + "***************");
                addC.setName(vals.getString(COUNTRY_NAME));
                System.out.println("***********************" + addC.getName() + "***************");
                sCurrencyLabeling.addCountry(addC);
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void populateCurrenciesDatabase(){
        InputStream is;
        JSONObject savedData = useSavedData();
        JSONObject codesToNames = getCurrencyNames();
        try {
            JSONObject jsa = savedData.getJSONObject(RATES);
            long time = Long.parseLong(savedData.getString(TIMESTAMP));
            for (int i = 0; i < jsa.length(); i++){
                Currency addC = new Currency();
                /**
                 * get rid of this line after debugging
                 */
                String code = "AED";
                Double rate = jsa.getDouble(code);
                addC.setCode(code);
                addC.setName((String) codesToNames.get(code));
                addC.setRate(rate);
                addC.setAsOfDate(time);
                /*try{
                    sCurrencyLabeling.updateCurrency(addC);
                } catch(IllegalArgumentException e){
                    sCurrencyLabeling.addCurrency(addC);
                    e.printStackTrace();
                } catch (SQLiteException e){
                    sCurrencyLabeling.addCurrency(addC);
                    e.printStackTrace();
                }*/
                Country addCo = new Country("Australia");
                addCo.setCode("AUS");
                addCo.setPrimaryCurrency("AUD");
                sCurrencyLabeling.addCountry(addCo);
                sCurrencyLabeling.addCurrency(addC);

            }
        } catch (JSONException e){
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public String parseDate() {
        if(sCurrencyLabeling == null) return "3 December, 2015";
        long date = sCurrencyLabeling.getDate();
        DateTime rval = new DateTime(date);
        return rval.toString("d MMMM, yyyy");
    }

    /**
     * write
     * @param name
     * @return
     */
    static public String parsePrimaryCurrency(String name){
        return "USD";
    }
}
