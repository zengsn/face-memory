package top.it138.face.service;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import top.it138.face.entity.App;
import top.it138.face.util.StringUtil;

@Service
public class AppServiceImpl extends BaseServiceImpl<App> implements AppService {

	@Override
	public Page<App> selectPage(long userId, int pageNum, int pageSize) {
		Page<App> pageBeen = PageHelper.startPage(pageNum, pageSize);
		App record = new App();
		record.setUserId(userId);
		mapper.select(record );
		
		return pageBeen;
	}
	
	@Override
	public App selectByAppKey(String appKey) {
		if (StringUtil.isEmpty(appKey)) {
			return null;
		}
		App record = new App();
		record.setAppKey(appKey);
		return mapper.selectOne(record);
	}
}
