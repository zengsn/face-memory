package com.gdp.service;

import java.util.List;

import com.gdp.entity.FaceInfo;
import com.gdp.pojo.admin.WeChatInfoVO;

/**
 * 人脸信息服务类
 */
public interface FaceInfoService {

	/**
	 * 保存微信用户自拍识别后的数据信息, 返回相应的记录 id 字段
	 * 
	 * @param faceInfo	
	 * @return
	 */
	public int saveFaceInfo(FaceInfo faceInfo);

	/**
	 * 根据 openid 获取所有记录
	 * 
	 * @param openid
	 * @return
	 */
	public List<FaceInfo> listFaceInfoByOpenId(String openid);

	/**
	 * 获取状态为 0 的指定用户记录
	 *
	 * @param openid
	 * @param status
	 * @return
	 */
	public List<FaceInfo> listFaceInfoUncheck(String openid, Integer status);

	/**
	 * 根据 id 查询一条记录
	 * 
	 * @param id
	 */
	public FaceInfo selectFaceInfoById(int id);
	
	/**
	 * 根据 id 删除记录
	 * 
	 * @param id
	 */
	public int deleteFaceInfoById(int id);

	/**
	 * 获取所有的微信用户的唯一id
	 * 
	 * @return
	 */
	public List<WeChatInfoVO> listAllWxid();

    /**
     * 根据 id 更新记录
     *
     * @param faceinfo
     * @return
     */
	public int updateFaceInfoById(FaceInfo faceinfo);
}
