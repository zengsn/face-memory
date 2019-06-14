package top.it138.face.service;

import com.github.pagehelper.Page;

import top.it138.face.entity.App;
import top.it138.face.entity.Log;

public interface LogService extends BaseService<Log>{
	/**
	 * 指定用户的App
	 * @param userId 用户id
	 * @param pageNum 页码
	 * @param pageSize 一页大小
	 * @return
	 */
	Page<Log> selectPage(int pageNum, int pageSize);
}
