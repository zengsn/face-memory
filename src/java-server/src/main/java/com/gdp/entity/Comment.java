package com.gdp.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.ToString;

@ToString
public class Comment {
    /**
     * 主键,自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 满意与否,1 满意, 0 不满意
     */
    private Boolean satisfied;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 照片id, 对应face_info的主键
     */
    @Column(name = "photo_id")
    private Integer photoId;

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
     * 获取评论内容
     *
     * @return content - 评论内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评论内容
     *
     * @param content 评论内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取满意与否,1 满意, 0 不满意
     *
     * @return satisfied - 满意与否,1 满意, 0 不满意
     */
    public Boolean getSatisfied() {
        return satisfied;
    }

    /**
     * 设置满意与否,1 满意, 0 不满意
     *
     * @param satisfied 满意与否,1 满意, 0 不满意
     */
    public void setSatisfied(Boolean satisfied) {
        this.satisfied = satisfied;
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
     * 获取照片id, 对应face_info的主键
     *
     * @return photo_id - 照片id, 对应face_info的主键
     */
    public Integer getPhotoId() {
        return photoId;
    }

    /**
     * 设置照片id, 对应face_info的主键
     *
     * @param photoId 照片id, 对应face_info的主键
     */
    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }
}