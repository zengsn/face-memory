package top.it138.face.entity;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "t_person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identification;

    private String name;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    private String phone;

    private String identity;

    @Column(name = "group_id")
    @NotNull
    private Long groupId;

    @JsonIgnore
    @Column(name = "gmt_create")
    private Date gmtCreate;

    @JsonIgnore
    @Column(name = "gmt_modified")
    private Date gmtModified;

    @JsonIgnore
    @Column(name = "user_id")
    private Long userId;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return identification
     */
    public String getIdentification() {
        return identification;
    }

    /**
     * @param identification
     */
    public void setIdentification(String identification) {
        this.identification = identification;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return birth
     */
    public Date getBirth() {
        return birth;
    }

    /**
     * @param birth
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return identity
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * @param identity
     */
    public void setIdentity(String identity) {
        this.identity = identity;
    }

    /**
     * @return group_id
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * @return gmt_create
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return gmt_modified
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * @param gmtModified
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}