package top.it138.face.service;

import com.github.pagehelper.Page;

import top.it138.face.entity.User;

public interface UserService extends BaseService<User>{

	/**
	 * 通过邮箱查找用户
	 * @param email email地址
	 * @return 找到的用户；return null 当用户不存在时
	 * @throws RuntimeException 当不满足唯一预约，找到多个用户时
	 */
	User selectUserByEmail(String email);

	/**
	 * 通过用户名查找
	 * @param userName
	 * @return
	 */
	User selectByUserName(String userName);
	
	/**
	 * 通过code查找
	 */
	User selectByCode(String code);
	
	Page<User> selectPage(int pageNum, int pageSize);
}
