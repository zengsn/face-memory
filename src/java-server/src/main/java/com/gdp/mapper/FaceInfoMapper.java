package com.gdp.mapper;

import java.util.List;

import com.gdp.entity.FaceInfo;
import com.gdp.pojo.admin.WeChatInfoVO;

import tk.mybatis.mapper.common.Mapper;

public interface FaceInfoMapper extends Mapper<FaceInfo> {
    /** 查询所有用户的 wxid和nick_name */
    List<WeChatInfoVO> selectAllWxid();
}