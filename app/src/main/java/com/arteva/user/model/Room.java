package com.arteva.user.model;


import com.arteva.user.Constant;

import java.io.Serializable;

public class Room implements Serializable {

    boolean isInvited;
    private String UID, roomName, joinedCount, fcm_id, status, isRoomActive, isStarted, isLeave, rightAns, wrongAns, authId, isJoined, cateLevel, userID, name, image, roomKey, category, roomID, noOfQuestion, isOppositeJoin, time, noOfPlayer;

    public Room() {
    }

 /*   @Override
    public int compareTo(Room o) {
        return getRightAns().compareTo(o.getRightAns());
    }*/

    public Room(String roomKey, String category, String noOfQuestion, String time, String noOfPlayer) {
        this.roomKey = roomKey;
        this.category = category;
        this.noOfQuestion = noOfQuestion;
        this.time = time;
        this.noOfPlayer = noOfPlayer;
    }

    public boolean isInvited() {
        return isInvited;
    }

    public void setInvited(boolean invited) {
        isInvited = invited;
    }

    public String getIsOppositeJoin() {
        return isOppositeJoin;
    }

    public void setIsOppositeJoin(String isOppositeJoin) {
        this.isOppositeJoin = isOppositeJoin;
    }

    public String getJoinedCount() {
        return joinedCount;
    }

    public void setJoinedCount(String joinedCount) {
        this.joinedCount = joinedCount;
    }

    public String getIsLeave() {
        return isLeave == null ? Constant.FALSE : isLeave;
    }

    public void setIsLeave(String isLeave) {
        this.isLeave = isLeave;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsStarted() {
        return "" + isStarted;
    }

    public void setIsStarted(String isStarted) {
        this.isStarted = isStarted;
    }

    public String getRightAns() {

        return rightAns == null ? "0" : rightAns;
    }

    public void setRightAns(String rightAns) {
        this.rightAns = rightAns;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getWrongAns() {
        return wrongAns == null ? "0" : wrongAns;
    }

    public void setWrongAns(String wrongAns) {
        this.wrongAns = wrongAns;
    }

    public String getIsRoomActive() {
        return isRoomActive;
    }

    public void setIsRoomActive(String isRoomActive) {
        this.isRoomActive = isRoomActive;
    }

    public String getCateLevel() {
        return cateLevel;
    }

    public void setCateLevel(String cateLevel) {
        this.cateLevel = cateLevel;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getNoOfQuestion() {
        return noOfQuestion;
    }

    public void setNoOfQuestion(String noOfQuestion) {
        this.noOfQuestion = noOfQuestion;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNoOfPlayer() {
        return noOfPlayer;
    }

    public void setNoOfPlayer(String noOfPlayer) {
        this.noOfPlayer = noOfPlayer;
    }

    public String getFcm_id() {
        return fcm_id;
    }

    public void setFcm_id(String fcm_id) {
        this.fcm_id = fcm_id;
    }

    public String getAuthId() {
        return authId == null ? "" : authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getIsJoined() {
        return isJoined;
    }

    public void setIsJoined(String isJoined) {
        this.isJoined = isJoined;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public String getRoomKey() {
        return roomKey;
    }

 /*   public void setRoomname(String roomname) {
        this.roomname = roomname;
    }*/

    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
