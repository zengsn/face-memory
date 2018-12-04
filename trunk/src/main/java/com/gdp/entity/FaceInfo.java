package com.gdp.entity;

import java.util.Date;

public class FaceInfo {
    private Integer id;

    private String photoPath;

    private String gender;

    private Double faceValue;

    private Byte age;

    @Override
    public String toString() {
        return "FaceInfo{" +
                "id=" + id +
                ", photoPath='" + photoPath + '\'' +
                ", gender='" + gender + '\'' +
                ", faceValue=" + faceValue +
                ", age=" + age +
                ", healthValue=" + healthValue +
                ", emotion='" + emotion + '\'' +
                ", faceQuality='" + faceQuality + '\'' +
                ", skinStatus='" + skinStatus + '\'' +
                ", createTime=" + createTime +
                ", wxid='" + wxid + '\'' +
                ", nickName='" + nickName + '\'' +
                ", status=" + status +
                '}';
    }

    private Double healthValue;

    private String emotion;

    private String faceQuality;

    private String skinStatus;

    private Date createTime;

    private String wxid;

    private String nickName;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath == null ? null : photoPath.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Double getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(Double faceValue) {
        this.faceValue = faceValue;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public Double getHealthValue() {
        return healthValue;
    }

    public void setHealthValue(Double healthValue) {
        this.healthValue = healthValue;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion == null ? null : emotion.trim();
    }

    public String getFaceQuality() {
        return faceQuality;
    }

    public void setFaceQuality(String faceQuality) {
        this.faceQuality = faceQuality == null ? null : faceQuality.trim();
    }

    public String getSkinStatus() {
        return skinStatus;
    }

    public void setSkinStatus(String skinStatus) {
        this.skinStatus = skinStatus == null ? null : skinStatus.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid == null ? null : wxid.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}