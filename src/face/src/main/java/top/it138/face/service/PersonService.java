package top.it138.face.service;

import java.util.List;

import top.it138.face.entity.Person;

public interface PersonService extends BaseService<Person>{
	List<Person> getAllByAppkey(String appKey);
}
