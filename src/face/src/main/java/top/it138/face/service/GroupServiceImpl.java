package top.it138.face.service;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import top.it138.face.entity.Group;


@Service
@Validated
public class GroupServiceImpl extends BaseServiceImpl<Group> implements GroupService {

	@Override
	public Page<Group> selectPage(@NotBlank String appkey, int pageNum, int pageSize) {
		Page<Group> pageBeen = PageHelper.startPage(pageNum, pageSize);
		Group record = new Group();
		record.setAppKey(appkey);;
		mapper.select(record);
		
		return pageBeen;
	}

	@Override
	public List<Group> selectByAppKey(@NotBlank String appKey) {
		Group record = new Group();
		record.setAppKey(appKey);
		List<Group> groups = mapper.select(record);
		
		return groups;
	}
}
