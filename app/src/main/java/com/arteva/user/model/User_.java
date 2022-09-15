package com.arteva.user.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User_ implements Parcelable {

    public static final Creator<User_> CREATOR = new Creator<User_>() {
        @Override
        public User_ createFromParcel(Parcel in) {
            return new User_(in);
        }

        @Override
        public User_[] newArray(int size) {
            return new User_[size];
        }
    };
    public String matchingId;
    public String cateId;
    public String langId;
    public String authID;
    public String userID;
    public String isAvail;
    public String status;
    public String email;
    public String name;
    public String image;
    public String user_id;
    public String opponentName;
    public String opponentProfile;
    public String resut;


    public User_() {
    }

    protected User_(Parcel in) {
        matchingId = in.readString();
        cateId = in.readString();
        langId = in.readString();
        authID = in.readString();
        userID = in.readString();
        isAvail = in.readString();
        status = in.readString();
        email = in.readString();
        name = in.readString();
        image = in.readString();
        user_id = in.readString();
        opponentName = in.readString();
        opponentProfile = in.readString();
        resut = in.readString();
    }

    public User_(String first_name, String email, String user_id) {
        this.name = first_name;
        this.email = email;
        this.user_id = user_id;
    }

    public User_(String userID, String name, String image, String isAvail, String langId, String cateId) {
        this.userID = userID;
        this.name = name;
        this.image = image;
        this.isAvail = isAvail;
        this.langId = langId;
        this.cateId = cateId;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public String getOpponentProfile() {
        return opponentProfile;
    }

    public void setOpponentProfile(String opponentProfile) {
        this.opponentProfile = opponentProfile;
    }

    public String getResut() {
        return resut;
    }

    public void setResut(String resut) {
        this.resut = resut;
    }

    public String getCateId() {
        return cateId;
    }

    public String getLangId() {
        return langId;
    }

    public String getStatus() {
        return status;
    }

    public String getMatchingId() {
        return matchingId;
    }

    public String getAuthID() {
        return authID;
    }

    public String getUserID() {
        return userID;
    }

    public String getIsAvail() {
        return isAvail;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getUser_id() {
        return user_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(matchingId);
        parcel.writeString(cateId);
        parcel.writeString(langId);
        parcel.writeString(authID);
        parcel.writeString(userID);
        parcel.writeString(isAvail);
        parcel.writeString(status);
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeString(user_id);
        parcel.writeString(opponentName);
        parcel.writeString(opponentProfile);
        parcel.writeString(resut);
    }
}