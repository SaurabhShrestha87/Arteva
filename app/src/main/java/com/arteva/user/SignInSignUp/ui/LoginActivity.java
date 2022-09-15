package com.arteva.user.SignInSignUp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arteva.user.Categories.MainActivityCat;
import com.arteva.user.Home.ui.HomeActivity;
import com.arteva.user.R;
import com.arteva.user.helper.Session;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    public static TextView coins;
    public static TextView score, coin, tvName;
    public static FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablogin);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Session.isLogin(LoginActivity.this)) {
            Intent intent = new Intent(LoginActivity.this, MainActivityCat.class);
            if(!TextUtils.isEmpty(Session.getUserData(Session.IS_SUBSCRIBED, this)) && Session.getUserData(Session.IS_SUBSCRIBED, this).equalsIgnoreCase("1")) {
                intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("type", "default");
            }
            startActivity(intent);
            finish();
        }
        mAuth = FirebaseAuth.getInstance();
        if (!Session.getLoginMobileNumber(getApplicationContext()).equalsIgnoreCase("") && !Session.getLoginMobileNumber(getApplicationContext()).equalsIgnoreCase("") && !Session.getLoginMobileNumber(getApplicationContext()).equalsIgnoreCase("")) {
            showUserSignUp(Session.getLoginAuthId(getApplicationContext()), Session.getLoginToken(getApplicationContext()), Session.getLoginMobileNumber(getApplicationContext()));
        } else {
            showUserLogin();
        }
    }

    public void showUserSignUp(String authId, String token, String number) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.container, new SignUpFragment(this, authId, token, number))
                .commitNow();
    }

    public void showUserLogin() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.container, new SignInFragment(this))
                .commitNow();
    }
}
