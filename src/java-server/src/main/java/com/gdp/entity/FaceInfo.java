package com.gdp.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.ToString;

@ToString
@Table(name = "face_info")
public class FaceInfo {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 照片存放路径
     */
    @Column(name = "photo_path")
    private String photoPath;

    /**
     * 性别
     */
    private String gender;

    /**
     * 颜值
     */
    @Column(name = "face_value")
    private Double faceValue;

    /**
     * 年龄
     */
    private Byte age;

    /**
     * 健康值
     */
    @Column(name = "health_value")
    private Double healthValue;

    /**
     * 表情
     */
    private String emotion;

    /**
     * 人脸图像的阈值, 目前该字段值没有其作用, 后续扩展功能可能需要
     */
    @Column(name = "face_quality")
    private String faceQuality;

    /**
     * 面部特征识别结果, 包括健康、色斑、青春痘、黑眼圈
     */
    @Column(name = "skin_status")
    private String skinStatus;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 微信id, 即获取的openid
     */
    private String wxid;

    /**
     * 微信昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 识别结果被查看状态; 0为未查看;1为已查看
     */
    private Integer status;

    /**
     * 获取主键, 自增
     *
     * @return id - 主键, 自增
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键, 自增
     *
     * @param id 主键, 自增
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取照片存放路径
     *
     * @return photo_path - 照片存放路径
     */
    public String getPhotoPath() {
        return photoPath;
    }

    /**
     * 设置照片存放路径
     *
     * @param photoPath 照片存放路径
     */
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    /**
     * 获取性别
     *
     * @return gender - 性别
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置性别
     *
     * @param gender 性别
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 获取颜值
     *
     * @return face_value - 颜值
     */
    public Double getFaceValue() {
        return faceValue;
    }

    /**
     * 设置颜值
     *
     * @param faceValue 颜值
     */
    public void setFaceValue(Double faceValue) {
        this.faceValue = faceValue;
    }

    /**
     * 获取年龄
     *
     * @return age - 年龄
     */
    public Byte getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Byte age) {
        this.age = age;
    }

    /**
     * 获取健康值
     *
     * @return health_value - 健康值
     */
    public Double getHealthValue() {
        return healthValue;
    }

    /**
     * 设置健康值
     *
     * @param healthValue 健康值
     */
    public void setHealthValue(Double healthValue) {
        this.healthValue = healthValue;
    }

    /**
     * 获取表情
     *
     * @return emotion - 表情
     */
    public String getEmotion() {
        return emotion;
    }

    /**
     * 设置表情
     *
     * @param emotion 表情
     */
    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    /**
     * 获取人脸图像的阈值, 目前该字段值没有其作用, 后续扩展功能可能需要
     *
     * @return face_quality - 人脸图像的阈值, 目前该字段值没有其作用, 后续扩展功能可能需要
     */
    public String getFaceQuality() {
        return faceQuality;
    }

    /**
     * 设置人脸图像的阈值, 目前该字段值没有其作用, 后续扩展功能可能需要
     *
     * @param faceQuality 人脸图像的阈值, 目前该字段值没有其作用, 后续扩展功能可能需要
     */
    public void setFaceQuality(String faceQuality) {
        this.faceQuality = faceQuality;
    }

    /**
     * 获取面部特征识别结果, 包括健康、色斑、青春痘、黑眼圈
     *
     * @return skin_status - 面部特征识别结果, 包括健康、色斑、青春痘、黑眼圈
     */
    public String getSkinStatus() {
        return skinStatus;
    }

    /**
     * 设置面部特征识别结果, 包括健康、色斑、青春痘、黑眼圈
     *
     * @param skinStatus 面部特征识别结果, 包括健康、色斑、青春痘、黑眼圈
     */
    public void setSkinStatus(String skinStatus) {
        this.skinStatus = skinStatus;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取微信id, 即获取的openid
     *
     * @return wxid - 微信id, 即获取的openid
     */
    public String getWxid() {
        return wxid;
    }

    /**
     * 设置微信id, 即获取的openid
     *
     * @param wxid 微信id, 即获取的openid
     */
    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    /**
     * 获取微信昵称
     *
     * @return nick_name - 微信昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置微信昵称
     *
     * @param nickName 微信昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取识别结果被查看状态; 0为未查看;1为已查看
     *
     * @return status - 识别结果被查看状态; 0为未查看;1为已查看
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置识别结果被查看状态; 0为未查看;1为已查看
     *
     * @param status 识别结果被查看状态; 0为未查看;1为已查看
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}