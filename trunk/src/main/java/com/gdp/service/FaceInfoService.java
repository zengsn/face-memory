package com.gdp.service;

import java.util.List;

import com.gdp.base.BaseService;
import com.gdp.entity.FaceInfo;
import com.gdp.pojo.admin.WeChatInfoVO;

import tk.mybatis.mapper.common.Mapper;

/**
 * 人脸信息服务类
 */
public interface FaceInfoService extends BaseService<Mapper<FaceInfo>, FaceInfo> {
	
	/**
	 * 管理员根据 openid 获取指定用户所有记录
	 * @param openid
	 * @return
	 */
	public List<FaceInfo> listByOpenIdForAdmin(String openid);

	/**
	 * 根据 openid 获取所有status为 0 的记录
	 * 
	 * @param openid
	 * @return
	 */
	public List<FaceInfo> listFaceInfoByOpenId(String openid);
	
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
	 * 根据 openid 和 人脸信息状态查询
	 * 
	 * @return
	 */
	public List<FaceInfo> selectByOpenidAndStatus(String openid, Integer status, String orderBy);

	/**
	 * 根据openid 分页查询用户已经完成识别的记录
	 * 
	 * @param openid
	 * @param pageNum
	 * @return
	 */
	public List<FaceInfo> listFaceInfoByOpenIdWithPage(String openid, Integer pageNum);
	
	/**
	 * 查找指定用户当天识别成功的记录
	 * 
	 * @param openid
	 * @return
	 */
	public List<FaceInfo> listTodayFinished(String openid);

	/**
	 * 查询不含人脸或不是本人的图片信息
	 * 
	 * @param openid
	 * @return
	 */
	public List<FaceInfo> listShouldBedeleted(String openid);

}
