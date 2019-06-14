package com.gdp.service;

import java.util.Date;

import com.gdp.base.BaseService;
import com.gdp.entity.UserInfo;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Jashon
 * @version 1.0
 * @date 2019-02-17
 */
public interface UserInfoService extends BaseService<Mapper<UserInfo>, UserInfo> {

    /**
     * 保存用户信息 小程序userInfo
     *
     * @param userInfo
     * @return
     */
    int save(UserInfo userInfo);

    /**
     * 根据主键 openid 查询用户信息
     *
     * @param openid
     * @return
     */
    UserInfo getByOpenid(String openid);

    /**
     * 根据主键 openid 更新用户信息
     *
     * @param userInfo
     * @return
     */
    int updateByOpenid(UserInfo userInfo);

    /**
     * 查询两个日期之间增加的用户数
     * 
     * @param from
     * @param end
     * @return
     */
	int countBetweenToday(Date from, Date end);

	/**
	 * 查询总的用户数
	 * 
	 * @return
	 */
	int countAll();
	
	
}
