package com.gdp.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.ToString;

@ToString
@Table(name = "user_info")
public class UserInfo {
    /**
     * 用户唯一标识openid
     */
    @Id
    private String openid;

    /**
     * 用户微信昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 性别; 0代表未知, 1代表男, 2代表女
     */
    private Byte gender;

    /**
     * 用户所在国家
     */
    private String country;

    /**
     * 用户所在省份
     */
    private String province;

    /**
     * 用户所在城市
     */
    private String city;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * status: 3 代表用户未确认第一张含人脸照片是本人; 0 初始值, 代表用户未拍照; 1 代表用户已经确认第一张本人照片
     */
    private Integer status;

    /**
     * 获取用户唯一标识openid
     *
     * @return openid - 用户唯一标识openid
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * 设置用户唯一标识openid
     *
     * @param openid 用户唯一标识openid
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * 获取用户微信昵称
     *
     * @return nick_name - 用户微信昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置用户微信昵称
     *
     * @param nickName 用户微信昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取性别; 0代表未知, 1代表男, 2代表女
     *
     * @return gender - 性别; 0代表未知, 1代表男, 2代表女
     */
    public Byte getGender() {
        return gender;
    }

    /**
     * 设置性别; 0代表未知, 1代表男, 2代表女
     *
     * @param gender 性别; 0代表未知, 1代表男, 2代表女
     */
    public void setGender(Byte gender) {
        this.gender = gender;
    }

    /**
     * 获取用户所在国家
     *
     * @return country - 用户所在国家
     */
    public String getCountry() {
        return country;
    }

    /**
     * 设置用户所在国家
     *
     * @param country 用户所在国家
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 获取用户所在省份
     *
     * @return province - 用户所在省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置用户所在省份
     *
     * @param province 用户所在省份
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取用户所在城市
     *
     * @return city - 用户所在城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置用户所在城市
     *
     * @param city 用户所在城市
     */
    public void setCity(String city) {
        this.city = city;
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
     * 获取status: 3 代表用户未确认第一张含人脸照片是本人; 0 初始值, 代表用户未拍照; 1 代表用户已经确认第一张本人照片
     *
     * @return status - 3 代表用户未确认第一张含人脸照片是本人; 0 初始值, 代表用户未拍照; 1 代表用户已经确认第一张本人照片
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置status: 3 代表用户未确认第一张含人脸照片是本人; 0 初始值, 代表用户未拍照; 1 代表用户已经确认第一张本人照片
     *
     * @param status 3 代表用户未确认第一张含人脸照片是本人; 0 初始值, 代表用户未拍照; 1 代表用户已经确认第一张本人照片
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}