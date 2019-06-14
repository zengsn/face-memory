package com.gdp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdp.base.BaseServiceImpl;
import com.gdp.entity.FaceInfo;
import com.gdp.mapper.FaceInfoMapper;
import com.gdp.pojo.admin.WeChatInfoVO;
import com.gdp.service.FaceInfoService;
import com.gdp.util.ParaUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("faceInfoService")
public class FaceInfoServiceImpl extends BaseServiceImpl<FaceInfoMapper, FaceInfo> implements FaceInfoService {

	@Autowired
	private FaceInfoMapper faceInfoMapper;
	
	@Override
	public List<FaceInfo> listByOpenIdForAdmin(String openid) {
		Example example = new Example(FaceInfo.class);
		example.setDistinct(true);
		example.setOrderByClause("photo_path desc");
		Criteria criteria = example.createCriteria(); 
		criteria.andEqualTo("wxid", openid);
		
		List<FaceInfo> list = this.faceInfoMapper.selectByExample(example);
		return list;

	}
	
	@Override
	public List<FaceInfo> listFaceInfoByOpenId(String openid) {
		Example example = new Example(FaceInfo.class);
		example.setDistinct(true);
		example.setOrderByClause("photo_path desc");
		Criteria criteria = example.createCriteria(); 
		criteria.andEqualTo("wxid", openid);
		criteria.andEqualTo("status", ParaUtil.FACE_INFO_FINISH);
		
		List<FaceInfo> list = this.faceInfoMapper.selectByExample(example);
		return list;
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
	public List<FaceInfo> selectByOpenidAndStatus(String openid, Integer status, String orderBy) {
		Example example = new Example(FaceInfo.class);
		if (orderBy != null) {
			example.setOrderByClause(orderBy);
		}
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("wxid", openid);
		criteria.andEqualTo("status", status);

		List<FaceInfo> list = this.faceInfoMapper.selectByExample(example);
		return list;
	}

	@Override
	public List<FaceInfo> listFaceInfoByOpenIdWithPage(String openid, Integer pageNum) {
		Example example = new Example(FaceInfo.class);
		example.setOrderByClause("create_time DESC");
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("wxid", openid);
		criteria.andEqualTo("status", ParaUtil.FACE_INFO_FINISH);
		RowBounds rowBounds = new RowBounds((pageNum-1)*10, 10);
		
		List<FaceInfo> list = this.faceInfoMapper.selectByExampleAndRowBounds(example, rowBounds);
		
		return list;
	}

	@Override
	public List<FaceInfo> listTodayFinished(String openid) {
		Example example = new Example(FaceInfo.class);
		example.setOrderByClause("create_time desc");
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("status", ParaUtil.FACE_INFO_FINISH);
		criteria.andEqualTo("wxid", openid);
		LocalDate localDate = LocalDate.now();
		Date todayStart = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		criteria.andGreaterThanOrEqualTo("createTime", todayStart);
		
		return this.faceInfoMapper.selectByExample(example);
	}


	@Override
	public List<FaceInfo> listShouldBedeleted(String openid) {
		Example example = new Example(FaceInfo.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("wxid", openid);
		criteria.andCondition("status IN (2, 4, 5)");
		
		return this.faceInfoMapper.selectByExample(example);
	}

	@Override
	public int countBetween(Date from, Date end) {
		Example example = new Example(FaceInfo.class);
		example.setCountProperty("wxid");
		example.setDistinct(true);
		Criteria criteria = example.createCriteria();
		criteria.andBetween("createTime", from, end);
		
		int count = this.faceInfoMapper.selectCountByExample(example);
		return count;
	}
}
