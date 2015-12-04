package edu.udcs.udromeapp.currency.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * Created by KatherineMJB on 12/2/2015.
 */
public class LocationServices {
    private static LocationServices sLocationServices;
    LocationManager lm;
    Location l;
    LocationListener listener;
    private Context mContext;
    private String mCountry;
    private String mLat;
    private String mLong;

    public static LocationServices get(Context context){
        if(sLocationServices == null){
            sLocationServices = new LocationServices(context);
        }
        return sLocationServices;
    }

    private LocationServices(Context context) {
        mContext = context.getApplicationContext();
        lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        listener = new LocationUpdater();
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        try {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }/** should probably fix this at some point
         */
         catch (IllegalArgumentException e){
             e.printStackTrace();
         }
        try{
            l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch(SecurityException e){
            e.printStackTrace();
        } catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    public String getLocationChars(){
        if(mCountry == null){
            System.out.println("*****************************Using null method thingy************************");
            mCountry = "The United States";
            return "The United States";
        }
        mCountry.replaceFirst("^United States", "The United States");
        return mCountry;
    }

    private class LocationUpdater implements LocationListener {

        @Override
        public void onLocationChanged(Location l) {
            mLat =""+ l.getLatitude();
            mLong = "" + l.getLongitude();
            new CountryUpdater().execute(l);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    private class CountryUpdater extends  AsyncTask<Location, Void, String> {
        @Override
        protected String doInBackground(Location... params) {
            Location l = params[0];
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(l.getLatitude(), l.getLongitude(), 1);
                if (addresses.size() < 1)
                    return null;

                Address address = addresses.get(0);
                return address.getCountryCode();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

            @Override
        protected void onPostExecute(String result) {
            mCountry = result;
        }
    }
}
