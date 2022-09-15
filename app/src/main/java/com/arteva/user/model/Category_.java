package com.arteva.user.model;

import java.util.ArrayList;

public class Category_ {
    public ArrayList<SubCategory> subCategoryList;
    public boolean adsShow;
    private String id;
    private String name;
    private String image;
    private String maxLevel;
    private String noOfCate;
    private String message;
    private String date;
    private String ttlQues;
    private String video_id;

    public Category_() {
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public boolean isAdsShow() {
        return adsShow;
    }

    public void setAdsShow(boolean adsShow) {
        this.adsShow = adsShow;
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

    public String getNoOfCate() {
        return noOfCate;
    }

    public void setNoOfCate(String noOfCate) {
        this.noOfCate = noOfCate;
    }

    public String getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(String maxLevel) {
        this.maxLevel = maxLevel;
    }

    public String getTtlQues() {
        return ttlQues;
    }

    public void setTtlQues(String ttlQues) {
        this.ttlQues = ttlQues;
    }

    public ArrayList<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(ArrayList<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }
}
