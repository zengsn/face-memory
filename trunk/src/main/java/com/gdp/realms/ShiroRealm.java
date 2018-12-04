package com.gdp.realms;

import com.gdp.entity.SysUser;
import com.gdp.service.SysUserService;
import com.gdp.util.LogUtils;
import com.gdp.util.SpringInit;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jashon
 * @since 2018-10-27
 */
public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LogUtils.logger;

    /**
     * 认证
     *
     * 使用 盐值加密
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        System.out.println("===> Realms -> doGetAuthenticationInfo: " + token.hashCode());

        Subject subject = SecurityUtils.getSubject();
        String role = (String) subject.getSession().getAttribute("role");

        // 1. 把 AuthenticationToken 转换为 UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        // 2. 从 UsernamePasswordToken 中获取 username
        String username = usernamePasswordToken.getUsername();

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();

        if("wx".equals(role)) {
            // 微信登录
            logger.info("--> SimpleAuthenticationInfo wx login !");
            subject.getSession().setAttribute("openid", username);
            Object principal = username;
            Object credentials = MD5("123", username);
            // 用户名作为盐值
            ByteSource credentialsSalt = ByteSource.Util.bytes(username);
            info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, getName());
logger.info("————> 微信登陆成功：" + info.toString());
        } else {
            logger.info("--> SimpleAuthenticationInfo admin login !");
            // 3. 调用数据库的方法，从数据库查询 username对应的用户记录
            SysUserService sysUserService = (SysUserService) SpringInit.getSpringContext().getBean("sysUserService");
            List<SysUser> user = new ArrayList<SysUser>();

            try {
                logger.info("===> username:" + username);
                user = sysUserService.listByUsername(username);
            } catch (Exception e) {
                System.out.println("===> 数据库查询用户失败!!! ");
                e.printStackTrace();
            }
            // System.out.println("---> 从数据库中获取username： " + username + "所对应的信息");

            // 4. 根据用户信息的情况，决定是否需要抛出其他异常 AuthenticationException
            if("monster".equals(username)) {
                throw new LockedAccountException("===> 用户被锁定！");
            }

            // 5. 若用户不存在，则可以抛出 UnKnownAccountException 异常
            if(user.isEmpty()) {
                throw new UnknownAccountException("===> 用户不存在！");
            } else {
                // 6. 根据用户信息的情况，来构建 AuthenticationInfo 对象，并返回
                //
                // 以下信息是从数据库中获取的
                // 1) principal: 认证的实体信息，可以是 username 也可以是数据库对应用户的实体对象
                // 2) credentials: 密码(来自数据库)
                // 3) realmName: 当前 realm 对象的name, 调用父类的 getName() 方法即可.

                Object principal = username;
                Object credentials = user.get(0).getPassword();	// 加密后的密码； 原始密码为 123456，盐为 "admin"
                String realmName = getName();

                // 用户名作为盐值
                ByteSource credentialsSalt = ByteSource.Util.bytes(username);
                info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
                // 保存登录信息到session中
                subject.getSession().setAttribute("username", user.get(0).getUsername());
            }
        }

        return info;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        System.out.println("===> Realms -> doGetAuthorizationInfo: 授权" );
        // 1. 从 PrincipalCollection 中获取登陆用户的信息
        Object principal = principals.getPrimaryPrincipal();

        // 2. 利用登陆的用户信息来授权给当前用户的角色或者权限(可能需要查询数据库)
        Set<String> roles = new HashSet<String>();
        roles.add("user");      // 添加角色
        if ("admin".equals(principal)) {
            roles.add("admin");
        }

        // 3. 创建 SimpleAuthorizationInfo, 并设置其 role 属性
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        System.out.println(info.getRoles());
        // 4. 返回 对象
        return info;
    }



/////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////      以下为测试用计算加密后的密码值        ////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 盐值加密
     *
     * @param source
     * @param salt      // 盐值
     * @return
     */
    public static String MD5(String source, Object salt){
        String algorithmName = "MD5";   // 加密算法
        int hashIterations = 1024;      // 加密的次数

        String result = String.valueOf(new SimpleHash(algorithmName, source, salt, hashIterations));
        System.out.println("MD5 result: " + result);
        return result;
    }

    public static void main(String[] args) {
        String algorithmName = "MD5";
        Object source = "123456";	// 原始密码
        Object salt = "admin";		// 盐值
        int hashIterations = 1024;	// 加密的次数

        Object result = new SimpleHash(algorithmName, source, salt, hashIterations);// hashIterations 加密的次数

        System.out.println("盐值加密后的密码：" + result);
    }
}
