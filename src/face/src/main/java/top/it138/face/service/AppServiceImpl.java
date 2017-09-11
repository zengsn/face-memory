package top.it138.face.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import top.it138.face.entity.App;

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
		App record = new App();
		record.setAppKey(appKey);
		List<App> list = mapper.select(record );
		
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
}
