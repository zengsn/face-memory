package top.it138.face.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.aspectj.util.FileUtil;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static top.it138.face.util.Constants.PHOTO_PATH;

import top.it138.face.dto.PhotoData;
import top.it138.face.dto.RecognitionData;
import top.it138.face.entity.App;
import top.it138.face.entity.Person;
import top.it138.face.entity.Photo;
import top.it138.face.service.AppService;
import top.it138.face.service.PersonService;
import top.it138.face.service.PhotoService;
import top.it138.face.service.RecognitionService;
import top.it138.face.util.UuidUtil;

@RestController
@RequestMapping("/api/1.0/")
@Validated
@Transactional
public class ApiController {
	private static final String[] SUFFIX_NAMES = { "png", "jpg" };
	@Autowired
	private PersonService personService;
	@Autowired
	private AppService appService;
	@Autowired
	private PhotoService photoService;
	@Autowired
	private RecognitionService recognitionService;
	
	@RequestMapping(value = "app", method = RequestMethod.GET)
	public boolean getApp(String appKey, String appSecret) {
		App app = appService.selectByAppKey(appKey);
		if (app.getAppSecret().equals(appSecret)) {
			return true;
		}
		
		return false;
	}

	@RequestMapping(value = "persons", method = RequestMethod.GET)
	public Object getAll(@NotEmpty @RequestHeader("appKey") String appKey) {
		return personService.getAllByAppkey(appKey);
	}

	@RequestMapping(value = "persons", method = RequestMethod.POST)
	public Person newPerson(@NotEmpty @RequestHeader("appKey") String appKey, @NotEmpty String name, String des) {
		App app = appService.selectByAppKey(appKey);
		Person person = new Person();
		person.setAppId(app.getId());
		person.setName(name);
		String personCode = UuidUtil.getUUID();
		person.setPersonCode(personCode);
		person.setDes(des);
		personService.save(person);

		return person;
	}

	@RequestMapping(value = "persons/{id}", method = RequestMethod.GET)
	public Object getPerson(@NotEmpty @RequestHeader("appKey") String appKey, @PathVariable("id") Long id,
			HttpServletResponse response) {
		App app = appService.selectByAppKey(appKey);
		Person person = personService.selectById(id);
		if (person == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return "person不存在";
		}
		if (!person.getAppId().equals(app.getId())) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return "只能访问自己的person";
		}
		return person;
	}

	@RequestMapping(value = "persons/{id}", method = RequestMethod.DELETE)
	public Object deletePerson(@NotEmpty @RequestHeader("appKey") String appKey, @PathVariable("id") Long id,
			HttpServletResponse response) {
		App app = appService.selectByAppKey(appKey);
		Person person = personService.selectById(id);
		if (person == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return "person不存在";
		}
		if (!person.getAppId().equals(app.getId())) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return "只能删除自己的person";
		}
		personService.delete(person);
		return "删除成功";
	}

	@RequestMapping(value = "persons/{id}/photos", method = RequestMethod.GET)
	public Object getPhotos(@PathVariable("id") Long personId) {
		return photoService.selectByPersonId(personId);
	}

	/**
	 * 添加照片
	 * 
	 * @param appKey
	 * @param personId
	 * @param image
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "persons/{id}/photos", method = RequestMethod.POST)
	public Object addPhoto(@NotEmpty @RequestHeader("appKey") String appKey, @PathVariable("id") Long personId,
			String image, HttpServletResponse response) {
		String[] ss = image.split(";"); // 格式：(后缀;Base64编码图片)
		if (image.isEmpty() || ss.length != 2) {
			return "文件为空";
		}
		// 获取文件的后缀名
		String suffixName = ss[0];
		if (!ArrayUtils.contains(SUFFIX_NAMES, suffixName)) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return "格式错误";
		}

		App app = appService.selectByAppKey(appKey);
		Person person = personService.selectById(personId);
		if (person == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return "person不存在";
		}
		if (!person.getAppId().equals(app.getId())) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return "只能访问自己的person";
		}

		// 保存到数据库
		Photo p = new Photo();
		p.setPersonId(personId);
		p.setSuffixName(suffixName);
		photoService.save(p);

		// 获取文件名
		String fileName = p.getId().toString() + p.getSuffixName();
		
		// 文件上传后的路径
		String filePath = PHOTO_PATH + p.getPersonId() + "\\" + p.getId() + "." + p.getSuffixName();

		// 解决中文问题，liunx下中文路径，图片显示问题
		// fileName = UUID.randomUUID() + suffixName;
		File dest = new File(filePath);
		// 检测是否存在目录
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		byte[] bys = Base64.decodeBase64(ss[1].getBytes());

		try {
			FileUtils.writeByteArrayToFile(dest, bys);
			return "上传成功";
		} catch (IOException e) {
			e.printStackTrace();
		}

		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return "上传失败";
	}

	@RequestMapping(value = "persons/{id}/photos", method = RequestMethod.DELETE)
	public Object deleteAllPhoto(@NotEmpty @RequestHeader("appKey") String appKey, @PathVariable("id") Long personId,
			HttpServletResponse response) {
		App app = appService.selectByAppKey(appKey);
		Person person = personService.selectById(personId);
		if (person == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return "person不存在";
		}
		if (!person.getAppId().equals(app.getId())) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return "只能访问person不在该appkey中";
		}

		// 删除该personid所有的照片
		Photo p = new Photo();
		p.setPersonId(personId);
		photoService.delete(p);
		File file = new File(PHOTO_PATH + personId);
		FileUtil.deleteContents(file);

		return "删除成功";
	}

	@RequestMapping(value = "persons/{id}/recognition", method = RequestMethod.POST)
	public Object recognition(@NotEmpty @RequestHeader("appKey") String appKey, @PathVariable("id") Long personId,
			String image, HttpServletResponse response) {
		//防止越权访问
		App app = appService.selectByAppKey(appKey);
		Person person = personService.selectById(personId);
		if (person == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return "person不存在";
		}
		if (!person.getAppId().equals(app.getId())) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return "只能访问person不在该appkey中";
		}

		Photo example = new Photo();
		example.setPersonId(personId);
		List<Photo> list = photoService.select(example);
		List<PhotoData> photos = new ArrayList<>();
		for (Photo p : list) {
			PhotoData pd = new PhotoData();
			String path = PHOTO_PATH + p.getPersonId() + "\\" + p.getId() + "." + p.getSuffixName();
			pd.setPath(path);
			pd.setSuffix(p.getSuffixName());
			photos.add(pd);
		}
		
		//img
		String[] ss = image.split(";"); // 格式：(后缀;Base64编码图片)
		PhotoData pd = new PhotoData();
		pd.setSuffix(ss[0]);
		pd.setImgBytes(Base64.decodeBase64(ss[1].getBytes()));
		
		//TODO 识别
		RecognitionData data = new RecognitionData();
		data.setPersonId(personId);
		data.setPhotos(photos);
		data.setRecognitionPhoto(pd);
		return recognitionService.recognize(data);
	}
}
