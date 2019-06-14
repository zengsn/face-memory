package com.gdp.admin.controller;

import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdp.entity.SysUser;
import com.gdp.service.SysUserService;
import com.gdp.util.SecurityUtil;

/**
 * 后台登录权限控制器
 *
 * @author Jashon
 * @since 2018-10-27
 */
@RestController
@RequestMapping("/admin")
public class SysUserController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     *
     * @param username  用户名
     * @param password  密码
     * @return
     */
    @RequestMapping("/login")
    public Map<String, Object> login(String username, String password, HttpSession httpSession) {
        Map<String, Object> res = new HashMap<>();

        List<SysUser> list = sysUserService.listByUsername(username);
        if (list == null) {
        	res.put("result", "failed");
        	res.put("resultMsg", "该账户不存在!");
			return res;
		}
        
        String pwd = list.get(0).getPassword();	
        String decodePassword = SecurityUtil.getPassword(password, username, 1024);
        
        logger.info("decodePassword: {}", decodePassword);
        
        if(pwd.equals(decodePassword)) {
        	logger.info("{} - 管理员 {} 登录管理系统!", Date.from(Instant.now()), username);
        	httpSession.setAttribute("role", "admin");
        	res.put("result", "succeed");
        } else {
        	res.put("result", "failed_token_wrong");
        	res.put("resultMsg", "密码错误");
        }

        return res;
    }
    
    /**
     * 退出登录
     *
     * @return 重定向到登录页
     */
    @RequestMapping("/logout")
    public String logout(HttpSession httpSession){
    	httpSession.invalidate();
    	return "redirect:/login.html";
    }
    
//
//    /**
//     * 仅模拟测试微信登录使用，没有实际意义
//     *
//     * @param openid
//     * @return
//     */
//    @RequestMapping("/wxlogin")
//    @ResponseBody
//    public Map<String, Object> wxlogin(String openid) {
//        Map<String, Object> res = new HashMap<>();
//
//        logger.info("---> wx login!");
//        // 获取 Subject
//        Subject currentUser = SecurityUtils.getSubject();
//
//        // 判断是否已经由登陆过账户，是的话直接返回登陆成功
//        if(currentUser.getSession().getAttribute("openid") != null ) {
//            res.put("result", "openid exist succeed");
//        }else {
////            if(!currentUser.isAuthenticated()) {
//                System.out.println("--> wxlogin: !currentUser.isAuthenticated()");
//                currentUser.getSession().setAttribute("role", "wx");
//                // 将登陆的 openid 和密码封装成 token
//                UsernamePasswordToken token = new UsernamePasswordToken(openid, "123");
//
//                try {
//                    // 执行登录
//                    currentUser.login(token);
//                    // 记住我
//                    token.setRememberMe(true);
//                    res.put("result", "succeed");
//                } catch (AuthenticationException ae) {
//                    logger.error("---> 登录失败: " + ae.getMessage());
//                    res.put("result", "failed_token_wrong");
//                }
////            }
//        }
//
//        return res;
//    }
//
}
