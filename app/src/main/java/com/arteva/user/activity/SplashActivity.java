package com.arteva.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.widget.AppCompatImageView;

import com.arteva.user.R;
import com.arteva.user.SignInSignUp.ui.LoginActivity;
import com.arteva.user.helper.Session;

import java.util.Locale;


public class SplashActivity extends Activity {

    private static Locale myLocale;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_2);
        Animation rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        ((AppCompatImageView) findViewById(R.id.splashScreenImgContainer)).setAnimation(rotateAnim);

        handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent;
            if (Session.isFirstTime(this)) {
                intent = new Intent(SplashActivity.this, OnboardingActivity.class);
            }else{
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, 3000);
        changeLocale(Session.getApplanguage(SplashActivity.this));
    }

    public void changeLocale(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);//Set Selected Locale
        Session.setApplanguage(SplashActivity.this, lang);
        Locale.setDefault(myLocale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = myLocale;//set config locale as selected locale
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());//Update the config
    }

}