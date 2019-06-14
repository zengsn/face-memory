package top.it138.face.service;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.github.pagehelper.Page;

import top.it138.face.entity.Group;


public interface GroupService extends BaseService<Group>{
	/**
	 * 指定app的Group
	 * @param userId 用户id
	 * @param pageNum 页码
	 * @param pageSize 一页大小
	 * @return
	 */
	Page<Group> selectPage(@NotBlank String appkey, int pageNum, int pageSize);
	
	List<Group> selectByAppKey(@NotBlank String appKey);
}
