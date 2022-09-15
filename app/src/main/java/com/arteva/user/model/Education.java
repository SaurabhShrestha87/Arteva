package com.arteva.user.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Education implements Serializable, Parcelable {
    @SerializedName("boardList")
    public ArrayList<Education> boardList;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    protected Education(Parcel in) {
        boardList = in.createTypedArrayList(Education.CREATOR);
        id = in.readString();
        title = in.readString();
    }

    public static final Creator<Education> CREATOR = new Creator<Education>() {
        @Override
        public Education createFromParcel(Parcel in) {
            return new Education(in);
        }

        @Override
        public Education[] newArray(int size) {
            return new Education[size];
        }
    };

    public ArrayList<Education> getBoardList() {
        return boardList;
    }

    public void setBoardList(ArrayList<Education> boardList) {
        this.boardList = boardList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(boardList);
        dest.writeString(id);
        dest.writeString(title);
    }

    @Override
    public String toString() {
        return "Education{" +
                "boardList=" + boardList +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
