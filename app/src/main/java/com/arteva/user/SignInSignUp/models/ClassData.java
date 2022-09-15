package com.arteva.user.SignInSignUp.models;

import java.util.List;

/**
 * Created by e203 on 5/12/17.
 */

public class ClassData {

    public List<SubjectData> subjectData;
    private String id;
    private String name;
    private boolean isSelected;

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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public List<SubjectData> getSubjectData() {
        return subjectData;
    }

    public void setSubjectData(List<SubjectData> subjectData) {
        this.subjectData = subjectData;
    }
}
