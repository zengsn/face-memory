package top.it138.face.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.it138.face.entity.App;
import top.it138.face.entity.Person;
import top.it138.face.mapper.AppMapper;

@Service
public class PersonServiceImpl extends BaseServiceImpl<Person> implements PersonService {
	@Autowired
	private AppMapper AppMapper;
	@Override
	public List<Person> getAllByAppkey(String appKey) {
		App app = new App();
		app.setAppKey(appKey);
		List<App> apps = AppMapper.select(app);
		//找不到对应的app
		if (apps.isEmpty()) {
			return null;
		}
		Person person = new Person();
		person.setAppId(app.getId());
		return mapper.select(person);
	}

}
