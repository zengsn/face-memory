package com.gdp.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdp.base.BaseServiceImpl;
import com.gdp.entity.UserInfo;
import com.gdp.mapper.UserInfoMapper;
import com.gdp.service.UserInfoService;

/**
 * @author Jashon
 * @version 1.0
 * @date 2019-02-17
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public int save(UserInfo userInfo) {
        return this.userInfoMapper.insertSelective(userInfo);
    }

    @Override
    public UserInfo getByOpenid(String openid) {
        return this.userInfoMapper.selectByPrimaryKey(openid);
    }

    @Override
    public int updateByOpenid(UserInfo userInfo) {
        return this.userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

	@Override
	public int countBetweenToday(Date from, Date from2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
