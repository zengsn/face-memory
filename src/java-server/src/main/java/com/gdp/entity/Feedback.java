package com.gdp.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.ToString;

@ToString
public class Feedback {
    /**
     * 主键,自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 微信id, 即获取的openid
     */
    private String wxid;

    /**
     * 默认为0；-1代表删除
     */
    private Byte status;

    /**
     * 获取主键,自增
     *
     * @return id - 主键,自增
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键,自增
     *
     * @param id 主键,自增
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取邮箱地址
     *
     * @return email - 邮箱地址
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱地址
     *
     * @param email 邮箱地址
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取反馈内容
     *
     * @return content - 反馈内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置反馈内容
     *
     * @param content 反馈内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取创建时间
     *
     * @return createtime - 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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
     * 获取默认为0；-1代表删除
     *
     * @return status - 默认为0；-1代表删除
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置默认为0；-1代表删除
     *
     * @param status 默认为0；-1代表删除
     */
    public void setStatus(Byte status) {
        this.status = status;
    }
}