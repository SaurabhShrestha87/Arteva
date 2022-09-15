package com.arteva.user.SignInSignUp.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.arteva.user.Categories.MainActivityCat;
import com.arteva.user.Constant;
import com.arteva.user.Home.ui.HomeActivity;
import com.arteva.user.R;
import com.arteva.user.activity.PrivacyPolicy;
import com.arteva.user.helper.ApiConfig;
import com.arteva.user.helper.Session;
import com.arteva.user.helper.Utils;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.annotations.NotNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {
    private final String TAG = "LOGINACTIVITY";
    public TextInputEditText edtEmail;
    public TextInputLayout inputEmail;
    CallbackManager mCallbackManager;
    String token;
    TextView tvPrivacy;
    ProgressDialog mProgressDialog;
    String id;
    CheckBox chkPrivacy;
    LoginActivity loginActivity;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;
    private Dialog verifyDialog;

    public SignInFragment(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public static void GetUpadate(final Activity activity) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.GET_SYSTEM_CONFIG, "1");
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject obj = new JSONObject(response);

                    boolean error = obj.getBoolean("error");
                    if (!error) {
                        JSONObject jsonobj = obj.getJSONObject("data");
                        Constant.APP_LINK = jsonobj.getString(Constant.KEY_APP_LINK);
                        Constant.MORE_APP_URL = jsonobj.getString(Constant.KEY_MORE_APP);
                        Constant.VERSION_CODE = jsonobj.getString(Constant.KEY_APP_VERSION);
                        Constant.REQUIRED_VERSION = jsonobj.getString(Constant.KEY_APP_VERSION);

                        String versionName = "";
                        try {
                            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
                            versionName = packageInfo.versionName;
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }

                        if (Constant.FORCE_UPDATE.equals("1")) {
                            if (compareVersion(versionName, Constant.VERSION_CODE) < 0) {
                                OpenBottomDialog(activity);
                            } else if (compareVersion(versionName, Constant.REQUIRED_VERSION) < 0) {
                                OpenBottomDialog(activity);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, params);

    }

    public static void OpenBottomDialog(final Activity activity) {
        View sheetView = activity.getLayoutInflater().inflate(R.layout.lyt_terms_privacy, null);
        ViewGroup parentViewGroup = (ViewGroup) sheetView.getParent();
        if (parentViewGroup != null) {
            parentViewGroup.removeAllViews();
        }
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(activity);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ImageView imgclose = sheetView.findViewById(R.id.imgclose);
        Button btnNotNow = sheetView.findViewById(R.id.btnNotNow);
        Button btnUpadateNow = sheetView.findViewById(R.id.btnUpdateNow);

        mBottomSheetDialog.setCancelable(false);
        imgclose.setOnClickListener(v -> {
            if (mBottomSheetDialog.isShowing())
                mBottomSheetDialog.dismiss();
        });
        btnNotNow.setOnClickListener(v -> {
            if (mBottomSheetDialog.isShowing())
                mBottomSheetDialog.dismiss();
        });
        btnUpadateNow.setOnClickListener(view -> {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.APP_LINK)));
            //System.out.println("Packge Name::=" + Constant.APP_LINK + activity.getPackageName());

        });
    }

    public static int compareVersion(String version1, String version2) {
        String[] arr1 = version1.split("\\.");
        String[] arr2 = version2.split("\\.");

        int i = 0;
        while (i < arr1.length || i < arr2.length) {
            if (i < arr1.length && i < arr2.length) {
                if (Integer.parseInt(arr1[i]) < Integer.parseInt(arr2[i])) {
                    return -1;
                } else if (Integer.parseInt(arr1[i]) > Integer.parseInt(arr2[i])) {
                    return 1;
                }
            } else if (i < arr1.length) {
                if (Integer.parseInt(arr1[i]) != 0) {
                    return 1;
                }
            } else {
                if (Integer.parseInt(arr2[i]) != 0) {
                    return -1;
                }
            }
            i++;
        }

        return 0;
    }

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        tvPrivacy = v.findViewById(R.id.tvPrivacy);
        edtEmail = v.findViewById(R.id.edtEmail);
        chkPrivacy = v.findViewById(R.id.chkPrivacy);
        inputEmail = v.findViewById(R.id.inputEmail);
        LoginActivity.mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        MobileBottomshit(v);
        if (!Utils.isNetworkAvailable(getActivity())) {
            setSnackBar(getString(R.string.msg_no_internet), getString(R.string.retry));
        }
        token = Session.getDeviceToken(getActivity());
        if (token == null) {
            token = "token";
        }
        Random rand = new Random();
        id = String.format("%04d", rand.nextInt(10000));
        System.out.println("valuesGEtt::=" + id);
        PrivacyPolicy();
        Utils.GetSystemConfig(getActivity());
        GetUpadate(getActivity());
        return v;
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void PrivacyPolicy() {
        tvPrivacy.setClickable(true);
        tvPrivacy.setMovementMethod(LinkMovementMethod.getInstance());

        String message = getString(R.string.term_privacy);
        String s2 = getString(R.string.terms);
        String s1 = getString(R.string.privacy_policy);
        final Spannable wordtoSpan = new SpannableString(message);

        wordtoSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PrivacyPolicy.class);
                intent.putExtra("type", "privacy");
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);
                ds.isUnderlineText();
            }
        }, message.indexOf(s1), message.indexOf(s1) + s1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PrivacyPolicy.class);
                intent.putExtra("type", "terms");
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);
                ds.isUnderlineText();
            }
        }, message.indexOf(s2), message.indexOf(s2) + s2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPrivacy.setText(wordtoSpan);
    }

    public void MobileBottomshit(View sheetView) {
        final TextInputEditText editTextPhone;
        editTextPhone = sheetView.findViewById(R.id.editTextPhone);
        sheetView.findViewById(R.id.emailsubmit).setOnClickListener(view -> {
            final String code = "91";
            final String number = editTextPhone.getText().toString().trim();
            InitFirebaseLogin(code, editTextPhone);
            if (!chkPrivacy.isChecked()) {
                Snackbar.make(chkPrivacy, "Accept terms and conditions", Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (number.isEmpty()) {
                editTextPhone.setError(getString(R.string.enternumber));
                return;
            }
            final String phoneNumber = "+" + code + number;
            sendVerificationCode(phoneNumber);
        });
    }

    public void VerifyBottomSheet(String phonenumber, String tendigitnumber) {
        verifyDialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        verifyDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        View sheetView = getLayoutInflater().inflate(R.layout.verify_bottom, null);
        verifyDialog.setCancelable(false);
        verifyDialog.setCanceledOnTouchOutside(false);
        sheetView.findViewById(R.id.imgClose).setOnClickListener(view -> verifyDialog.dismiss());
        final OtpTextView edtcode;
        edtcode = sheetView.findViewById(R.id.editTextCode);
        sheetView.findViewById(R.id.emailsubmit).setOnClickListener(view -> {
            String code = edtcode.getOTP();
            if (code.isEmpty() || code.length() < 6) {
                edtcode.showError();
                edtcode.requestFocus();
                Snackbar.make(edtcode, "Enter 6 Digit code sent!", Snackbar.LENGTH_SHORT).show();
                return;
            }
            verifyCode(code, phonenumber, tendigitnumber);
        });
        verifyDialog.setContentView(sheetView);
        verifyDialog.show();
    }

    private void verifyCode(String code, String phoneNumber, String tendigitnumber) {
        showProgressDialog();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(Constant.verificationCode, code);
        signInWithCredential(credential, phoneNumber, tendigitnumber);
    }

    private void signInWithCredential(PhoneAuthCredential credential, String phoneNumber, String tendigitnumber) {
        LoginActivity.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = LoginActivity.mAuth.getCurrentUser();
                        LoginUser(user.getUid(), tendigitnumber);
                        if (verifyDialog != null && verifyDialog.isShowing()) {
                            verifyDialog.dismiss();
                        }
                    } else {
                        String message = "Something is wrong, we will fix it soon...";
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered...";
                        }
                        Toast.makeText(loginActivity, message, Toast.LENGTH_SHORT).show();
                        hideProgressDialog();
                    }
                });
    }

    private void InitFirebaseLogin(String code, TextInputEditText editTextPhone) {
        LoginActivity.mAuth = FirebaseAuth.getInstance();
        mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NotNull PhoneAuthCredential phoneAuthCredential) {
                setSnackBar(getActivity(), "onVerificationCompleted", getString(R.string.ok), Color.RED);
            }

            @Override
            public void onVerificationFailed(@NotNull FirebaseException e) {
                hideProgressDialog();
                setSnackBar(getActivity(), e.getLocalizedMessage(), getString(R.string.ok), Color.RED);
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                hideProgressDialog();
                Constant.verificationCode = s;
                final String number = editTextPhone.getText().toString().trim();
                if (number.isEmpty()) {
                    editTextPhone.setError("Valid number is required");
                    return;
                }
                final String phoneNumber = code + number;
                VerifyBottomSheet(phoneNumber, number);
            }
        };
    }

    private void sendVerificationCode(String number) {
        Log.e("TAG", "sendVerificationCode: STARTED");
        showProgressDialog();
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(LoginActivity.mAuth)
                .setPhoneNumber(number)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(loginActivity)                 // Activity (for callback binding)
                .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void LoginUser(final String authId, final String number) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.userLogIn, "1");
        params.put(Constant.AUTH_ID, authId);
        params.put(Constant.fcmId, token);
        params.put(Constant.mobile, number);
        ApiConfig.RequestToVolley((result, response) -> {
            Log.e(TAG, "LoginUser: " + response);
            if (result) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("error").equals("false")) {
                        JSONObject jsonobj = obj.getJSONObject("data");
                        if (!jsonobj.getString(Constant.status).equals(Constant.DE_ACTIVE)) {
                            Session.saveUserDetail(getActivity(),
                                    jsonobj.getString(Constant.userId),
                                    jsonobj.getString(Constant.name),
                                    jsonobj.getString(Constant.email),
                                    jsonobj.getString(Constant.mobile),
                                    jsonobj.getString(Constant.PROFILE),
                                    jsonobj.getString(Constant.REFER_CODE),
                                    "mobile",
                                    jsonobj.getString(Constant.class_),
                                    jsonobj.getString(Constant.subject_),
                                    jsonobj.getString(Constant.is_subscribed));
                            Intent intent = new Intent(loginActivity,  MainActivityCat.class);
                            if(!TextUtils.isEmpty(Session.getUserData(Session.IS_SUBSCRIBED, loginActivity)) && Session.getUserData(Session.IS_SUBSCRIBED, loginActivity).equalsIgnoreCase("1")) {
                                intent = new Intent(loginActivity, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("type", "default");
                            }
                            startActivity(intent);
                            getActivity().finish();
                            hideProgressDialog();
                        } else
                            setSnackBarStatus();
                    } else if (obj.getBoolean(Constant.newUser)) {
                        if (loginActivity != null) {
                            Session.setLoginMobileNumber(number, getActivity());
                            Session.setLoginToken(token, getActivity());
                            Session.setLoginAuthId(authId, getActivity());
                            loginActivity.showUserSignUp(authId, token, number);
                        }
                    } else {
                        LoginManager.getInstance().logOut();
                        Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    System.out.println("urlResponse:=" + e);
                    e.printStackTrace();
                }
            }
        }, params);
    }

    public void setSnackBar(final Activity activity, String message, String action, int color) {
        final Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(action, view -> snackbar.dismiss());
        snackbar.setActionTextColor(color);
        View snackbarView = snackbar.getView();
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbar.show();
    }

    public void setSnackBar(String message, String action) {
        final Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(action, view -> {
            if (Utils.isNetworkAvailable(getActivity())) {
                snackbar.dismiss();
            } else {
                snackbar.show();
            }
        });
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    public void setSnackBarStatus() {
        final Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.account_deactivate), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.ok), view -> {

            Session.clearUserSession(getActivity());
            LoginActivity.mAuth.signOut();
            LoginManager.getInstance().logOut();

        });

        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
        hideProgressDialog();
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

}