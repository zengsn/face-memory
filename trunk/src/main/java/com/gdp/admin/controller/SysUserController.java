package com.gdp.admin.controller;

import com.gdp.util.LogUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台登录权限控制器
 *
 * @author Jashon
 * @since 2018-10-27
 */
@Controller
@RequestMapping("/admin")
public class SysUserController {

    private Logger logger = LogUtils.logger;

    /**
     * 登录
     *
     * @param username  用户名
     * @param password  密码
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> res = new HashMap<>();

        logger.info("---> admin login!");
        // 获取 Subject
        Subject currentUser = SecurityUtils.getSubject();

        // 判断是否已经由登陆过账户，是的话直接返回登陆成功
        if(currentUser.getSession().getAttribute("username")!=null ) {
            res.put("result", "succeed");
        }else {
            if(!currentUser.isAuthenticated()) {
                // 将登陆的手机号和密码封装成 token
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);

                try {
                    // 执行登录
                    currentUser.login(token);

                    // 记住我
                    token.setRememberMe(true);
                    res.put("result", "succeed");
                } catch (AuthenticationException ae) {
                    logger.error("---> 登录失败: " + ae.getMessage());
                    res.put("result", "failed_token_wrong");
                }
            }
        }
        return res;
    }

    /**
     * 仅模拟测试微信登录使用，没有实际意义
     *
     * @param openid
     * @return
     */
    @RequestMapping("/wxlogin")
    @ResponseBody
    public Map<String, Object> wxlogin(String openid) {
        Map<String, Object> res = new HashMap<>();

        logger.info("---> wx login!");
        // 获取 Subject
        Subject currentUser = SecurityUtils.getSubject();

        // 判断是否已经由登陆过账户，是的话直接返回登陆成功
        if(currentUser.getSession().getAttribute("openid") != null ) {
            res.put("result", "openid exist succeed");
        }else {
//            if(!currentUser.isAuthenticated()) {
                System.out.println("--> wxlogin: !currentUser.isAuthenticated()");
                currentUser.getSession().setAttribute("role", "wx");
                // 将登陆的 openid 和密码封装成 token
                UsernamePasswordToken token = new UsernamePasswordToken(openid, "123");

                try {
                    // 执行登录
                    currentUser.login(token);
                    // 记住我
                    token.setRememberMe(true);
                    res.put("result", "succeed");
                } catch (AuthenticationException ae) {
                    logger.error("---> 登录失败: " + ae.getMessage());
                    res.put("result", "failed_token_wrong");
                }
//            }
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
        Map<String, Object> res = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login.html";
    }
}
