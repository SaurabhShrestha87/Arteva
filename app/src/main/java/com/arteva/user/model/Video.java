package com.arteva.user.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Video extends ArrayList<Parcelable> implements Parcelable {
    private String id;
    private String subject_id;
    private String name;
    private String image;
    private String message;
    private String date;
    private String video_url;

    public Video() {
    }

    protected Video(Parcel in) {
        id = in.readString();
        subject_id = in.readString();
        name = in.readString();
        image = in.readString();
        message = in.readString();
        date = in.readString();
        video_url = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(subject_id);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeString(message);
        parcel.writeString(date);
        parcel.writeString(video_url);
    }
}
