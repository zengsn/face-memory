package top.it138.face.service;

import java.util.List;

import top.it138.face.entity.UserRole;

public interface UserRoleService extends BaseService<UserRole>{
	/**
	 * 通过用户id查找角色
	 * @param id
	 * @return
	 */
	List<UserRole> selectByUserId(Long id);

}
