package com.gdp.service;

import java.util.List;

import com.gdp.base.BaseService;
import com.gdp.entity.SysUser;

import tk.mybatis.mapper.common.Mapper;

/**
 * 后台管理员账号信息服务类
 *
 * @author Jashon
 * @since 2018-10-27
 */
public interface SysUserService extends BaseService<Mapper<SysUser>, SysUser> {

    /**
     * 根据用户名查找管理员账号
     *
     * @param username
     * @return
     */
    public List<SysUser> listByUsername(String username);

}
