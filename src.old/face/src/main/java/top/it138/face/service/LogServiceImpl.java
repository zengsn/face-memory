package top.it138.face.service;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import top.it138.face.entity.Log;

@Service
public class LogServiceImpl extends BaseServiceImpl<Log> implements LogService {

	@Override
	public Page<Log> selectPage(int pageNum, int pageSize) {
		Page<Log> pageBeen = PageHelper.startPage(pageNum, pageSize);
		Log record = new Log();
		mapper.select(record);
		return pageBeen;
	}

}
