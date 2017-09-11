package top.it138.face.service;

import com.github.pagehelper.Page;

import top.it138.face.entity.App;

public interface AppService extends BaseService<App>{
	/**
	 * 指定用户的App
	 * @param userId 用户id
	 * @param pageNum 页码
	 * @param pageSize 一页大小
	 * @return
	 */
	Page<App> selectPage(long userId, int pageNum, int pageSize);

	App selectByAppKey(String appKey);
}
