package org.maktab.digikala.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import org.maktab.digikala.R;
import org.maktab.digikala.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private Context mContext = this;
    private ActivitySplashBinding mSplashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashBinding = DataBindingUtil.setContentView(this,R.layout.activity_splash);

        getSupportActionBar().hide();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Write whatever to want to do after delay specified (1 sec)
                if (!isOnline()) {
                    mSplashBinding.progressBar.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar
                            .make(mSplashBinding.splashLayout,
                                    "No Internet Access",
                                    Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                    snackbar.show();
                } else {
                    Intent intent = new Intent(mContext, HomePageActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}