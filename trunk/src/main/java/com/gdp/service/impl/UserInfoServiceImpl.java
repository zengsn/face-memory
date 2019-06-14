package com.gdp.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdp.base.BaseServiceImpl;
import com.gdp.entity.UserInfo;
import com.gdp.mapper.UserInfoMapper;
import com.gdp.service.UserInfoService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

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
	public int countBetweenToday(Date from, Date end) {
		Example example = new Example(UserInfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andBetween("createTime", from, end);
		
		int count = this.userInfoMapper.selectCountByExample(example);
		return count;
	}

	@Override
	public int countAll() {
		return this.userInfoMapper.selectCountByExample(null);
	}

}
