package com.arteva.user.model;

import java.util.ArrayList;

public class Question {
    public int id;
    public boolean isAttended, isCorrect;
    public boolean adsShow;
    private String question, note, level, trueAns, image, ansOption, selectedAns, langId, selectedOpt, queType;
    private ArrayList<String> options = new ArrayList<String>();

    public Question() {
    }


    public Question(String question) {
        super();
        this.question = question;
    }

    public Question(boolean adsShow) {
        this.adsShow = adsShow;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    public String getSelectedAns() {
        return selectedAns;
    }

    public void setSelectedAns(String selectedAns) {
        this.selectedAns = selectedAns;
    }

    public boolean isAttended() {
        return isAttended;
    }

    public void setAttended(boolean attended) {
        isAttended = attended;
    }

    public boolean isAdsShow() {
        return adsShow;
    }

    public void setAdsShow(boolean adsShow) {
        this.adsShow = adsShow;
    }

    public String getAnsOption() {
        return ansOption;
    }

    public void setAnsOption(String ansOption) {
        this.ansOption = ansOption;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean addOption(String option) {
        return this.options.add(option);
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public String getTrueAns() {
        return trueAns;
    }

    public void setTrueAns(String trueAns) {
        this.trueAns = trueAns;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSelectedOpt() {
        return selectedOpt;
    }

    public void setSelectedOpt(String selectedOpt) {
        this.selectedOpt = selectedOpt;
    }

    public String getQueType() {
        return queType;
    }

    public void setQueType(String queType) {
        this.queType = queType;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
