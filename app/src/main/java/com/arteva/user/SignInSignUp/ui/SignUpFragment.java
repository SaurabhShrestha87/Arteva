package com.arteva.user.SignInSignUp.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arteva.user.Categories.MainActivityCat;
import com.arteva.user.Constant;
import com.arteva.user.R;
import com.arteva.user.SignInSignUp.adapter.ClassAdapter;
import com.arteva.user.SignInSignUp.adapter.SubjectAdapter;
import com.arteva.user.SignInSignUp.models.ClassData;
import com.arteva.user.SignInSignUp.models.SpinnerData;
import com.arteva.user.SignInSignUp.models.SubjectData;
import com.arteva.user.helper.ApiConfig;
import com.arteva.user.helper.Session;
import com.arteva.user.model.Education;
import com.facebook.login.LoginManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SignUpFragment extends Fragment {

    private static final String TAG = "SIGNUP";
    public static FirebaseAuth mAuth;
    private final LoginActivity activity;
    private final String authId;
    private final String number;
    public TextInputEditText edtName, edtFatherName, edtEmail, edtRefer;
    public TextInputLayout inputName,inputFatherName, inputEmail;
    public ProgressDialog mProgressDialog;
    CardView signupryt;
    CardView classNextBtn;
    CardView subjectNextBtn;
    RecyclerView rvInterestMulti;
    RecyclerView rv_subject_multi;
    int page = 1;
    ScrollView infoSV;
    ScrollView classSV;
    ScrollView subjectSV;
    String token;
    private LinearLayout go_to_login;
    private RelativeLayout back_icon;
    private ArrayList<String> subjectList = new ArrayList<>();
    private int classPosition = 0;
    private String classID = "";
    private String subjectID = "";
    private ArrayList<ClassData> classList = null;

    public CardView BoardView;
    private SpinnerData spinnerData;
    public Spinner spinnerCity, spinnerEdu, spinnerBoard;
    private ArrayList<String> cityList, eduList, boardList;
    int cityPos = 0, educationPos = 0, boardPos = -1;


    public SignUpFragment(LoginActivity activity, String authId, String token, String number) {
        this.activity = activity;
        this.authId = authId;
        this.token = token;
        this.number = number;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_sign_up, container, false);

        go_to_login = v.findViewById(R.id.go_to_login);
        go_to_login.setOnClickListener(view -> {
            if (activity != null) {
                activity.showUserLogin();
            }
        });

        back_icon = v.findViewById(R.id.back_icon);
        back_icon.setOnClickListener(view -> {
            if (page > 1) {
                page -= 1;
                setVisible(page);
            } else {
                activity.showUserLogin();
            }
        });

        BoardView = v.findViewById(R.id.BoardView);
        edtName = v.findViewById(R.id.edtName);
        edtFatherName = v.findViewById(R.id.edtFatherName);
        edtEmail = v.findViewById(R.id.edtEmail);
        spinnerCity = v.findViewById(R.id.spinnerCity);
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    cityPos = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerBoard = v.findViewById(R.id.spinnerBoard);
        spinnerBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    boardPos = i;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerEdu = v.findViewById(R.id.spinnerEdu);
        spinnerEdu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                educationPos = position;
                if (position > 0) {
                    boardList = new ArrayList<>();
                    if(spinnerData.getEducation_data().get(position - 1).getBoardList().isEmpty()){
                        boardPos = -1;
                        BoardView.setVisibility(View.GONE);
                        return;
                    } // RETURN IF EMPTY BOARD
                    boardList.add("Select Board");
                    for (int i = 0; i < spinnerData.getEducation_data().get(position - 1).getBoardList().size(); i++) {
                        boardList.add(spinnerData.getEducation_data().get(position - 1).getBoardList().get(i).getTitle());
                    }
                        BoardView.setVisibility(View.VISIBLE);
                        ArrayAdapter bardoAdapter = new ArrayAdapter(activity, R.layout.simple_spinner_item, boardList);
                        bardoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerBoard.setAdapter(bardoAdapter);
                        boardPos = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        setSpinners();
        edtRefer = v.findViewById(R.id.edtRefer);
        inputName = v.findViewById(R.id.inputName);
        inputFatherName = v.findViewById(R.id.inputFatherName);
        inputEmail = v.findViewById(R.id.inputEmail);

        signupryt = v.findViewById(R.id.signupryt);
        classNextBtn = v.findViewById(R.id.classNextBtn);
        subjectNextBtn = v.findViewById(R.id.subjectNextBtn);

        infoSV = v.findViewById(R.id.infoSV);
        classSV = v.findViewById(R.id.classSV);
        subjectSV = v.findViewById(R.id.subjectSV);

        rvInterestMulti = v.findViewById(R.id.rv_interest_multi);
        rv_subject_multi = v.findViewById(R.id.rv_subject_multi);
        mAuth = FirebaseAuth.getInstance();
        setInfoFields();
        new GetClassData().execute();
        return v;
    }

    private void setSpinners() {
        Map<String, String> ApiParams = new HashMap<>();
        try {
            ApiParams.put(Constant.getEducation, "1");
            ApiConfig.RequestToVolley((result, response) -> {
                if (result) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String error = jsonObject.getString(Constant.ERROR);
                        if (error.equalsIgnoreCase("false")) {
                            Gson gson = new Gson();
                            spinnerData = gson.fromJson(response, SpinnerData.class);
                            eduList = new ArrayList<>();
                            eduList.add("Select Education");
                            for (int i = 0; i < spinnerData.getEducation_data().size(); i++) {
                                eduList.add(spinnerData.getEducation_data().get(i).getTitle());
                            }
                            ArrayAdapter eduAdapter = new ArrayAdapter(activity, R.layout.simple_spinner_item, eduList);
                            eduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerEdu.setAdapter(eduAdapter);

                            cityList = new ArrayList<>();
                            cityList.add("Select City");
                            for (int i = 0; i < spinnerData.getCity_data().size(); i++) {
                                cityList.add(spinnerData.getCity_data().get(i).getCity_name());
                            }
                            ArrayAdapter cityAdapter = new ArrayAdapter(activity, R.layout.simple_spinner_item, cityList);
                            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerCity.setAdapter(cityAdapter);

                            signupryt.setOnClickListener(view -> {
                                final String name = edtName.getText().toString();
                                final String father_name = edtFatherName.getText().toString();
                                final String email = edtEmail.getText().toString();
                                final String city = (String) spinnerCity.getItemAtPosition(cityPos);
                                final String education = (String) spinnerEdu.getItemAtPosition(educationPos);
                                final String board = (String) spinnerBoard.getItemAtPosition(boardPos);
                                final String referral = edtRefer.getText().toString();
                                if (!validateForm()) {
                                    return;
                                }
                                Random r = new Random();
                                char randomChar = (char) (r.nextInt(26) + 'a');
                                showClassInfo(authId, number, randomChar + name, referral, name, email, city, education, board, "", "mobile");
                            });
                        } else {
                            Toast.makeText(activity, "There was an error getting education List...", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, ApiParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setInfoFields() {
        if (token == null) {
            token = "token";
        }
    }

    public void showClassInfo(String authId, String number, String referral_code, String referral, String name, String email, String city, String education, String board, String profile, String type) {
        page = 2;
        setVisible(page);
        classNextBtn.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(classID)) {
                showSubjectInfo(authId, number, referral_code, referral, name, email, city, education,board, profile, type, classID);
            } else {
                Snackbar.make(rvInterestMulti, "Must Select One Class", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void showSubjectInfo(String authId, String number, String referral_code, String referral, String name, String email, String city, String education, String board, String profile, String type, String classString) {
        page = 3;
        setVisible(page);
        SubjectAdapter classAdapterMulti = new SubjectAdapter(this, classList.get(classPosition).getSubjectData());
        rv_subject_multi.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_subject_multi.setAdapter(classAdapterMulti);
        subjectNextBtn.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(subjectID)) {
                UserSignUpWithSocialMedia(authId, number, referral_code, referral, name, email, city, education, board,  profile, type, classString, subjectID);
            } else {
                Snackbar.make(rv_subject_multi, "Must Select At-least One Subject", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void setVisible(int page) {
        if (page == 1) {
            subjectSV.setVisibility(View.GONE);
            crossfade(infoSV, classSV);
        } else if (page == 2) {
            subjectSV.setVisibility(View.GONE);
            crossfade(classSV, infoSV);
        } else if (page == 3) {
            infoSV.setVisibility(View.GONE);
            crossfade(subjectSV, classSV);
        }
    }

    private boolean validateForm() {
        final String name = edtName.getText().toString().trim();
        final String father_name = edtFatherName.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String city = (String) spinnerCity.getItemAtPosition(cityPos);
        final String education = (String) spinnerEdu.getItemAtPosition(educationPos);
        final String board = (String) spinnerBoard.getItemAtPosition(boardPos);

        if (TextUtils.isEmpty(name)) {
            inputName.requestFocus();
            inputName.setError(getString(R.string.entername));
            Toast.makeText(activity, getString(R.string.entername), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            inputName.setError(null);
        }
        if (TextUtils.isEmpty(father_name)) {
            inputFatherName.requestFocus();
            inputFatherName.setError(getString(R.string.entername));
            Toast.makeText(activity, getString(R.string.entername), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            inputName.setError(null);
        }

        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.requestFocus();
            inputEmail.setError(getString(R.string.email_alert_1));
            Toast.makeText(activity, getString(R.string.email_alert_1), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            inputEmail.setError(null);
        }

        if (TextUtils.isEmpty(city) || cityPos==0) {
            Snackbar.make(spinnerCity, "City Required", Snackbar.LENGTH_SHORT).setAnchorView(spinnerCity).show();
            return false;
        }
        if (TextUtils.isEmpty(education) || educationPos==0) {
            Snackbar.make(spinnerEdu, "Education Required", Snackbar.LENGTH_SHORT).setAnchorView(spinnerEdu).show();
            return false;
        }
        if (boardPos==0) {
            Snackbar.make(spinnerBoard, "Board Required", Snackbar.LENGTH_SHORT).setAnchorView(spinnerBoard).show();
            return false;
        }
        return true;
    }

    public void UserSignUpWithSocialMedia(final String authId, final String number, final String fCode, final String referCode, final String name, final String email, final String city,
                                          final String education,final String board, final String profile, final String type, final String classString, final String subjectString) {
        showProgressDialog();
        Map<String, String> params = new HashMap<>();
        params.put(Constant.userSignUp, "1");
        params.put(Constant.email, email);
        params.put(Constant.AUTH_ID, authId);
        params.put(Constant.name, name);
        params.put(Constant.city, city);
        params.put(Constant.education, education);
        if(!TextUtils.isEmpty(board))
            params.put(Constant.board, board);
        else
            params.put(Constant.board, "");
        params.put(Constant.PROFILE, profile);
        params.put(Constant.fcmId, token);
        params.put(Constant.type, type);
        params.put(Constant.mobile, number);
        params.put(Constant.class_, classString);
        params.put(Constant.subject_, subjectString);
        params.put(Constant.REFER_CODE, referCode);
        params.put(Constant.FRIENDS_CODE, fCode);
        params.put(Constant.ipAddress, "0.0.0.0");
        Log.e(TAG, "UserSignUpWithSocialMedia: " + params);
        ApiConfig.RequestToVolley((result, response) -> {
            Log.e(TAG, "UserSignUpWithSocialMedia: " + response);
            if (result) {
                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("urlResponse:=" + response);
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
                                    type, classString, subjectString,
                                    jsonobj.getString(Constant.is_subscribed));

                            startActivity(new Intent(activity, MainActivityCat.class));
                            getActivity().finish();
                            hideProgressDialog();
                        } else {
                            Toast.makeText(getContext(), "Some Error Occured! x_x", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        LoginManager.getInstance().logOut();
                        Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "UserSignUpWithSocialMedia: " + e);
                    e.printStackTrace();
                }
            }
        }, params);
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

    private void crossfade(View contentView, final View loadingView) {
        contentView.setAlpha(0f);
        contentView.setVisibility(View.VISIBLE);
        contentView.animate()
                .alpha(1f)
                .setDuration(200)
                .setListener(null);
        loadingView.animate()
                .alpha(0f)
                .setDuration(209)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loadingView.setVisibility(View.GONE);
                    }
                });
    }

    private void initClass(ArrayList<ClassData> classList) {
        this.classList = classList;
        ClassAdapter classAdapter = new ClassAdapter(getActivity(), this, classList, false);
        rvInterestMulti.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvInterestMulti.setAdapter(classAdapter);
    }

    public void setUserSubjectListData(List<SubjectData> modifiedListUserData) {
        subjectList = new ArrayList<>();
        for (int i = 0; i < modifiedListUserData.size(); i++) {
            if (modifiedListUserData.get(i).isSelected()) {
                subjectList.add(modifiedListUserData.get(i).getId());
            }
        }
        subjectID = subjectList.toString().replaceAll("[\\[.\\].\\s+]", "");
    }

    public void setUserClass(String selectedClassId, int position) {
        classID = selectedClassId;
        classPosition = position;
    }

    private class GetClassData extends AsyncTask<String, String, ArrayList<ClassData>> {
        @Override
        protected ArrayList<ClassData> doInBackground(String... params) {
            ArrayList<ClassData> classList = new ArrayList<>();
            Map<String, String> ApiParams = new HashMap<>();
            try {
                ApiParams.put(Constant.getClassSubject, "1");
                ApiParams.put(Constant.type, "1");
                ApiConfig.RequestToVolley((result, response) -> {
                    Log.e("TAG", "doInBackground: " + response);
                    if (result) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String error = jsonObject.getString(Constant.ERROR);
                            if (error.equalsIgnoreCase("false")) {
                                JSONArray classArray = jsonObject.getJSONArray(Constant.DATA);
                                for (int i = 0; i < classArray.length(); i++) {
                                    ClassData classData = new ClassData();
                                    JSONObject classObject = classArray.getJSONObject(i);
                                    classData.setId(classObject.getString(Constant.ID));
                                    classData.setName(classObject.getString(Constant.CLASS_NAME));
                                    classData.setSelected(false);
                                    JSONArray subjectArray = classObject.getJSONArray(Constant.SUBJECT_DATA);
                                    ArrayList<SubjectData> subjectDataArrayList = new ArrayList<>();
                                    for (int k = 0; k < subjectArray.length(); k++) {
                                        JSONObject subjectObject = subjectArray.getJSONObject(k);
                                        SubjectData subjectData = new SubjectData();
                                        subjectData.setId(subjectObject.getString(Constant.ID));
                                        subjectData.setName(subjectObject.getString(Constant.SUBJECT_NAME));
                                        subjectData.setSelected(false);
                                        subjectDataArrayList.add(subjectData);
                                    }
                                    classData.setSubjectData(subjectDataArrayList);
                                    classList.add(classData);
                                }
                                initClass(classList);
                            } else {
                                Toast.makeText(activity, "There was an error...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, ApiParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return classList;
        }
    }
}
