package com.duykhanh.salesmanager.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.duykhanh.salesmanager.Controller.WIFI.ConnectionReceiver;
import com.duykhanh.salesmanager.R;
import com.duykhanh.salesmanager.View.Login.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private TextView txtVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        txtVersion = findViewById(R.id.txtVersion);


        try {
            // Check your network connection have or no
            int ret = ConnectionReceiver.isConnected();
            // end Check your network connection have or no

            if (ret == 1) {// return ret == 1 is have internet
                PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                txtVersion.setText(getString(R.string.string_version) + " " + packageInfo.versionName);

                // Set time intent activity
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent iLogin = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        startActivity(iLogin);
                        finish();
                    }
                }, 2000);
                //end Set time intent activity
            } else {
            //    Out app when No internet connection or don't on data internet , wifi data
                showDialogInternet();
            }
        } catch (
                PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showDialogInternet() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_internet);
        dialog.show();
        Button btnOk = dialog.findViewById(R.id.btnOK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });


    }
}
