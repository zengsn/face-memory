package top.it138.face.service;

import java.util.List;

import org.springframework.stereotype.Service;

import top.it138.face.entity.UserRole;

@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService {

	@Override
	public List<UserRole> selectByUserId(Long id) {
		UserRole userRole = new UserRole();
		userRole.setUserId(id);
		return mapper.select(userRole);
	}


}
