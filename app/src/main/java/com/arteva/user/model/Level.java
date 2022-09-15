package com.arteva.user.model;

import java.util.ArrayList;

public class Level {

    public String level, question, totalunlock;
    public ArrayList<Question> questionList;
    boolean isunlock, istotalvalue;
    private int levelNo;

    public Level() {
    }

    public String getTotalunlock() {
        return totalunlock;
    }

    public void setTotalunlock(String totalunlock) {
        this.totalunlock = totalunlock;
    }

    public boolean isIstotalvalue() {
        return istotalvalue;
    }

    public void setIstotalvalue(boolean istotalvalue) {
        this.istotalvalue = istotalvalue;
    }

    public boolean isIsunlock() {
        return isunlock;
    }

    public void setIsunlock(boolean isunlock) {
        this.isunlock = isunlock;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(int levelNo) {
        this.levelNo = levelNo;
    }

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }
}
