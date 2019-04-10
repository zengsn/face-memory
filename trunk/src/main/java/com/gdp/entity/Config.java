package com.gdp.entity;

import javax.persistence.*;

import lombok.ToString;

@ToString
public class Config {
    /**
     * 配置项的名称
     */
    @Id
    private String keyes;

    /**
     * 配置项的值
     */
    private String valuees;

    /**
     * 配置项说明
     */
    private String description;

    /**
     * 获取配置项的名称
     *
     * @return keyes - 配置项的名称
     */
    public String getKeyes() {
        return keyes;
    }

    /**
     * 设置配置项的名称
     *
     * @param keyes 配置项的名称
     */
    public void setKeyes(String keyes) {
        this.keyes = keyes;
    }

    /**
     * 获取配置项的值
     *
     * @return valuees - 配置项的值
     */
    public String getValuees() {
        return valuees;
    }

    /**
     * 设置配置项的值
     *
     * @param valuees 配置项的值
     */
    public void setValuees(String valuees) {
        this.valuees = valuees;
    }

    /**
     * 获取配置项说明
     *
     * @return description - 配置项说明
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置配置项说明
     *
     * @param description 配置项说明
     */
    public void setDescription(String description) {
        this.description = description;
    }
}