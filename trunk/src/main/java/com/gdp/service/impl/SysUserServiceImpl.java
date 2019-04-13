package com.gdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdp.base.BaseServiceImpl;
import com.gdp.entity.SysUser;
import com.gdp.mapper.SysUserMapper;
import com.gdp.service.SysUserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Jashon
 * @since 2018-10-27
 */
@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> listByUsername(String username) {
        Example example = new Example(SysUser.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);

        List<SysUser> list = this.sysUserMapper.selectByExample(example);
        return list;
    }
}
