package com.gdp.service;

import com.gdp.entity.SysUser;

import java.util.List;

/**
 * 后台管理员账号信息服务类
 *
 * @author Jashon
 * @since 2018-10-27
 */
public interface SysUserService {

    /**
     * 根据用户名查找管理员账号
     *
     * @param username
     * @return
     */
    public List<SysUser> listByUsername(String username);

}
