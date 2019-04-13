package com.gdp.web.controller;

import com.alibaba.fastjson.JSON;
import com.gdp.entity.UserInfo;
import com.gdp.pojo.OpenIdVO;
import com.gdp.service.UserInfoService;
import com.gdp.util.HttpRequestUtil;
import com.gdp.util.ParaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 小程序授权的 用户信息
 *
 * @author Jashon
 * @version 1.0
 * @date 2019-02-17
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    private Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 保存用户信息 userInfo
     *
     * @param code
     * @param nickName
     * @param country
     * @param province
     * @param city
     * @param gender
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(String code, String nickName, String country, String province,
                                    String city, Byte gender) {
        Map<String, Object> modelMap = new HashMap<>();

        // 发送获取 openid 的请求
        String openid = getOpenId(code);

        UserInfo userInfo = new UserInfo();
        userInfo.setOpenid(openid);
        userInfo.setNickName(nickName);
        userInfo.setCountry(country);
        userInfo.setProvince(province);
        userInfo.setCity(city);
        userInfo.setGender(gender);
        userInfo.setCreateTime(Date.from(Instant.now()));
        userInfo.setStatus(0);

        UserInfo info = this.userInfoService.getByOpenid(openid);
        int i = 0;
        if(info == null) {
            i = this.userInfoService.save(userInfo);
        } else {
            i = this.userInfoService.updateByOpenid(userInfo);
        }

        if(i == 1) {
            modelMap.put("result", "succeed");
        } else {
            modelMap.put("result", "failed");
        }

        return modelMap;
    }



    /**
     * 获取 openid
     *
     * @param code
     * @return
     */
    private String getOpenId(String code){
        String result = "";
        // 请求url
        String url = "https://api.weixin.qq.com/sns/jscode2session?";
        // 请求所需的参数
        String params = "appid=" + ParaUtil.APPID + "&secret=" + ParaUtil.SECRET
                + "&js_code=" + code + "&grant_type=authorization_code";

        result = HttpRequestUtil.sendPost(url, params);
        logger.info("发送请求获取 openid和session_key 请求结果" + result);

        // 解析请求的返回值, 转成json再使用对象进行封装
        JSON json = JSON.parseObject(result);

        OpenIdVO openIdVO = JSON.toJavaObject(json, OpenIdVO.class);
        String openid = openIdVO.getOpenid();

        return openid;
    }
}
