package com.gdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdp.entity.FaceInfo;
import com.gdp.entity.FaceInfoExample;
import com.gdp.mapper.FaceInfoMapper;
import com.gdp.pojo.admin.WeChatInfoVO;
import com.gdp.service.FaceInfoService;

@Service("faceInfoService")
public class FaceInfoServiceImpl implements FaceInfoService {

	@Autowired
	private FaceInfoMapper faceInfoMapper;
	
	public int saveFaceInfo(FaceInfo faceInfo) {
		int i = this.faceInfoMapper.insertSelective(faceInfo);
		if(i == 1) {
			// 返回刚插入数据记录的 id 
			i = this.faceInfoMapper.selectRecentId();
		}
		return i;
	}
	
	@Override
	public List<FaceInfo> listFaceInfoByOpenId(String openid) {
		FaceInfoExample faceInfoExample = new FaceInfoExample();
		faceInfoExample.setDistinct(true);
		faceInfoExample.setOrderByClause("photo_path desc");
		FaceInfoExample.Criteria criteria = faceInfoExample.createCriteria(); 
		criteria.andWxidEqualTo(openid);
		
		List<FaceInfo> list = this.faceInfoMapper.selectByExample(faceInfoExample);
		
		return list;
	}

	@Override
	public List<FaceInfo> listFaceInfoUncheck(String openid, Integer status) {
		FaceInfoExample faceInfoExample = new FaceInfoExample();
		faceInfoExample.setDistinct(true);
		faceInfoExample.setOrderByClause("photo_path desc");
		FaceInfoExample.Criteria criteria = faceInfoExample.createCriteria();
		criteria.andWxidEqualTo(openid);
		criteria.andStatusEqualTo(status);

		List<FaceInfo> list = this.faceInfoMapper.selectByExample(faceInfoExample);

		return list;
	}

	@Override
	public FaceInfo selectFaceInfoById(int id) {
		return faceInfoMapper.selectByPrimaryKey(id); 
	}

	@Override
	public int deleteFaceInfoById(int id) {
		int i = this.faceInfoMapper.deleteByPrimaryKey(id);
		return i;
	}

	@Override
	public List<WeChatInfoVO> listAllWxid() {
		return this.faceInfoMapper.selectAllWxid();
	}

	@Override
	public int updateFaceInfoById(FaceInfo faceinfo) {
		int i = this.faceInfoMapper.updateByPrimaryKeySelective(faceinfo);
		return i;
	}

}
