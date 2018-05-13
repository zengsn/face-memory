package top.it138.face.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import top.it138.face.entity.User;


@Service
public class UserServiceImpl  extends BaseServiceImpl<User> implements UserService{

	/**
	 * 通过邮箱查找用户
	 */
	@Override
	public User selectUserByEmail(String email) {
		User user = new User();
		user.setEmail(email);
		List<User> uList = mapper.select(user);
		
		if (uList.size() > 1) {
			throw new RuntimeException("数据不满足唯一预约");
		}
		
		if (uList.isEmpty()) {
			return null;
		} else {
			return uList.get(0);
		}
	}

	@Override
	public User selectByUserName(String username) {
		User user = new User();
		user.setUsername(username);
		List<User> uList = mapper.select(user);
		
		if (uList.size() > 1) {
			throw new RuntimeException("数据不满足唯一预约");
		}
		
		if (uList.isEmpty()) {
			return null;
		} else {
			return uList.get(0);
		}
	}

	@Override
	public User selectByCode(String code) {
		User user = new User();
		user.setCode(code);
		List<User> uList = mapper.select(user);
		
		if (uList.size() > 1) {
			throw new RuntimeException("数据不满足唯一预约");
		}
		
		if (uList.isEmpty()) {
			return null;
		} else {
			return uList.get(0);
		}
	}

	@Override
	public Page<User> selectPage(int pageNum, int pageSize) {
		Page<User> pageBeen = PageHelper.startPage(pageNum, pageSize);
		User record = new User();
		mapper.select(record );
		
		return pageBeen;
	}

}
