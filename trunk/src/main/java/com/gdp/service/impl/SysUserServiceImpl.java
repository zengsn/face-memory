package com.gdp.service.impl;

import com.gdp.entity.SysUser;
import com.gdp.entity.SysUserExample;
import com.gdp.mapper.SysUserMapper;
import com.gdp.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jashon
 * @since 2018-10-27
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> listByUsername(String username) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);

        List<SysUser> list = this.sysUserMapper.selectByExample(example);
        return list;
    }
}
