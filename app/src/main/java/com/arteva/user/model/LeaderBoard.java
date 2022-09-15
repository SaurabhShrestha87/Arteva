package com.arteva.user.model;


import java.util.ArrayList;

public class LeaderBoard {
    public ArrayList<LeaderBoard> topList;
    private String rank, name, score, userId, profile;

    public LeaderBoard(ArrayList<LeaderBoard> topList) {
        this.topList = topList;
    }

    public LeaderBoard() {
    }

    public LeaderBoard(String rank, String name, String score, String userId, String profile) {
        this.rank = rank;
        this.name = name;
        this.score = score;
        this.userId = userId;
        this.profile = profile;
    }

    public ArrayList<LeaderBoard> getTopList() {
        return topList;
    }

    public String getProfile() {
        return profile;
    }

    public String getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public String getUserId() {
        return userId;
    }

}
