package top.it138.face.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import top.it138.face.dto.PersonWithPath;
import top.it138.face.entity.Person;
import top.it138.face.entity.Photo;
import top.it138.face.util.PropertiesUtil;
import top.it138.face.util.RequestUtil;

@Validated
@Service
public class PersonServiceImpl extends BaseServiceImpl<Person> implements PersonService {
	@Autowired
	private PhotoService photoService;
	private String basePath;
	
	public PersonServiceImpl() {
		basePath = PropertiesUtil.getSystemProperties().getProperty("photoSavePath") + "/";
	}
	

	@Override
	public Page<Person> selectPage(@NotNull Long groupId, int pageNum, int pageSize) {
		Page<Person> pageBeen = PageHelper.startPage(pageNum, pageSize);
		Person record = new Person();
		record.setGroupId(groupId);
		mapper.select(record);
		
		return pageBeen;
	}

	@Override
	public List<Person> selectByGroupId(@NotNull Long groupId) {
		Person record = new Person();
		record.setGroupId(groupId);
		return mapper.select(record);
	}

	@Override
	public List<PersonWithPath> parseTo(HttpServletRequest request, List<Person> persons) {
		List<PersonWithPath> list = new ArrayList<>();
		for (Person p : persons) {
			PersonWithPath pwp = parseTo(request, p);
			list.add(pwp);
		}
		return list;
	}

	@Override
	public PersonWithPath parseTo(HttpServletRequest request, Person person) {
		PersonWithPath pwp = new PersonWithPath();
		BeanUtils.copyProperties(person, pwp);
		List<Photo> photos = photoService.selectByPersonId(person.getId());
		for (Photo photo : photos) {
			String path = RequestUtil.PototoURL(request, photo.getPath(), photo.getSuffix());
			pwp.addUrl(path);
			pwp.addPath(basePath+photo.getPath() + photo.getSuffix());
		}
		
		return pwp;
	}
	
	

}
