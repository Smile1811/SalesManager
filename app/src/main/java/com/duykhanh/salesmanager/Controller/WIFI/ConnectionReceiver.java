package com.duykhanh.salesmanager.Controller.WIFI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import java.net.HttpURLConnection;
import java.net.URL;


public class ConnectionReceiver extends BroadcastReceiver {
    private static final int TIMEOUT = 3000;
    private static final String SERVER_URL = "https://www.google.com";


    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public static int isConnected() {
        int checkI = 1;
        try {

            ConnectivityManager
                    cm = (ConnectivityManager) WifiApp.getInstance().getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null && ni.isConnected()) {
                //Network is available but check if we can get access from the network
                URL url = new URL(SERVER_URL);
                HttpURLConnection urlc = (HttpURLConnection) url
                        .openConnection();
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(TIMEOUT); // Timeout 2 seconds.
                urlc.connect();
                if(urlc.getResponseCode() == 200) // Successful response.
                {
                    return checkI;
                }
                else{
                    checkI = 0;
                    Log.d("NO INTERNET", "NO INTERNET");
                    return checkI;
                }
            }
            else{
                checkI = 0;
                Log.d("NO INTERNET CONNECTION", "NO INTERNET CONNECTION");
                return checkI;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkI;
    }
}
