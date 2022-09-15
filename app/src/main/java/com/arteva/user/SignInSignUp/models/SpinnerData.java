package com.arteva.user.SignInSignUp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.arteva.user.model.City;
import com.arteva.user.model.Education;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by e203 on 5/12/17.
 */

public class SpinnerData implements Serializable, Parcelable {

    @SerializedName("error")
    private String error;
    @SerializedName("education_data")
    private ArrayList<Education> education_data;
    @SerializedName("city_data")
    private ArrayList<City> city_data;

    protected SpinnerData(Parcel in) {
        error = in.readString();
        education_data = in.createTypedArrayList(Education.CREATOR);
    }

    public static final Creator<SpinnerData> CREATOR = new Creator<SpinnerData>() {
        @Override
        public SpinnerData createFromParcel(Parcel in) {
            return new SpinnerData(in);
        }

        @Override
        public SpinnerData[] newArray(int size) {
            return new SpinnerData[size];
        }
    };

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<Education> getEducation_data() {
        return education_data;
    }

    public void setEducation_data(ArrayList<Education> education_data) {
        this.education_data = education_data;
    }

    public ArrayList<City> getCity_data() {
        return city_data;
    }

    public void setCity_data(ArrayList<City> city_data) {
        this.city_data = city_data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(error);
        dest.writeTypedList(education_data);
    }
}
