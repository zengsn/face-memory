package top.it138.face.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import com.github.pagehelper.Page;

import top.it138.face.dto.PersonWithPath;
import top.it138.face.entity.Person;

public interface PersonService extends BaseService<Person>{

	Page<Person> selectPage(@NotNull Long groudId, int pageNum, int pageSize);
	
	List<Person> selectByGroupId(@NotNull Long groupId);

	List<PersonWithPath> parseTo(HttpServletRequest request, List<Person> persons);
	
	PersonWithPath parseTo(HttpServletRequest request, Person person);
}
