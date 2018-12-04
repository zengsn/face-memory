package com.gdp.mapper;

import com.gdp.entity.FaceInfo;
import com.gdp.entity.FaceInfoExample;
import java.util.List;

import com.gdp.pojo.admin.WeChatInfoVO;
import org.apache.ibatis.annotations.Param;

public interface FaceInfoMapper {
	
    /** 查询最近插入的记录id */
    int selectRecentId();
    /** 查询所有用户的 wxid和nick_name */
    List<WeChatInfoVO> selectAllWxid();
    
    int countByExample(FaceInfoExample example);

    int deleteByExample(FaceInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FaceInfo record);

    int insertSelective(FaceInfo record);

    List<FaceInfo> selectByExample(FaceInfoExample example);

    FaceInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FaceInfo record, @Param("example") FaceInfoExample example);

    int updateByExample(@Param("record") FaceInfo record, @Param("example") FaceInfoExample example);

    int updateByPrimaryKeySelective(FaceInfo record);

    int updateByPrimaryKey(FaceInfo record);
}