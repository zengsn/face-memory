package top.it138.face.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import top.it138.face.common.CommonResult;
import top.it138.face.dto.PhotoResult;
import top.it138.face.entity.Person;
import top.it138.face.entity.Photo;
import top.it138.face.entity.User;
import top.it138.face.exception.FaceException;
import top.it138.face.service.PersonService;
import top.it138.face.service.PhotoService;
import top.it138.face.util.PropertiesUtil;
import top.it138.face.util.RequestUtil;
import top.it138.face.util.SessionUtil;
import top.it138.face.util.UuidUtil;

@Controller
@Validated
@RequestMapping("photo")
public class PhotoController {
	@Autowired
	private PhotoService photoService;
	@Autowired
	private PersonService personService;
	private File basePath;
	private File tmpPath;

	public PhotoController() {
		Properties systemProperties = PropertiesUtil.getSystemProperties();
		String path = systemProperties.getProperty("photoSavePath");
		String tmp = systemProperties.getProperty("photoTmpPath");
		basePath = new File(path);
		tmpPath = new File(tmp);
		if (!basePath.exists()) {
			basePath.mkdirs();
		}
		if (!tmpPath.exists()) {
			tmpPath.mkdirs();
		}
	}

	@RequestMapping("/show")
	public void showPhoto(String code, HttpServletResponse response) throws Exception {
		response.setContentType("image/jpeg");
		File file = new File(basePath, code);
		InputStream fis;
		if (!file.exists()) {
			fis = this.getClass().getClassLoader().getResourceAsStream("/static/assert/img/404.jpg");
		} else {
			fis = new FileInputStream(file);
		}
		byte[] temp = new byte[1024];
		OutputStream out = response.getOutputStream();
		try {
			int len = -1;
			while ((len = fis.read(temp)) != -1) {
				out.write(temp, 0, len);
			}
		} finally {
			IOUtils.closeQuietly(fis);
			IOUtils.closeQuietly(out);
		}
		
	}

	@RequestMapping("/delete")
	@ResponseBody
	@Transactional
	public Object delete(Long id) throws IOException {
		Photo photo = photoService.selectById(id);
		User user = SessionUtil.getCurrentUser();
		if (photo == null || !photo.getUserId().equals(user.getId())) {
			// 不能修改其他用户的appkey
			return new CommonResult<>("分组不存在");
		}

		photoService.deleteById(photo.getId());
		photoService.deleteFaceByCode(photo.getPath());

		return new CommonResult<>(CommonResult.SUCCESS);
	}

	@RequestMapping("/getFaces")
	@ResponseBody
	public Object getFaces(HttpServletRequest request, Long personId) {
		// 访问权限校验
		Person person = personService.selectById(personId);
		if (person == null || !SessionUtil.getCurrentUserId().equals(person.getUserId())) {
			// 不能修改其他用户的appkey
			return new CommonResult<>("人员不存在或者没有访问权限");
		}

		List<Photo> photos = photoService.selectByPersonId(personId);
		List<PhotoResult> prs = new ArrayList<>(photos.size());
		for (Photo p : photos) {
			PhotoResult pr = new PhotoResult();
			BeanUtils.copyProperties(p, pr);
			pr.setPath(RequestUtil.PototoURL(p.getPath(), p.getSuffix()));
			prs.add(pr);
		}
		return new CommonResult<>(prs);
	}

	@RequestMapping("/faceInput")
	public ModelAndView faceInputHtml(Long personId) {
		ModelAndView mv = new ModelAndView("person/photoFragments");
		mv.addObject("personId", personId);

		return mv;
	}

	@ResponseBody
	@RequestMapping("/upload")
	@Transactional
	public Object uploadPhoto(HttpServletRequest request, @RequestParam("upfile") MultipartFile file, Long personId)
			throws IllegalStateException, IOException, FaceException {
		String uuid = UuidUtil.getUUID();
		String name = file.getOriginalFilename();
		int index = name.lastIndexOf(".");
		String suffix = ".jpg";
		if (index != -1) {
			// suffix = name.substring(index);
		}

		Person person = personService.selectById(personId);
		if (person == null || !SessionUtil.getCurrentUserId().equals(person.getUserId())) {
			// 不能修改其他用户的appkey
			return new CommonResult<>("人员不存在或者没有访问权限");
		}

		File dest = new File(tmpPath, uuid + suffix);
		// 普通上传,到临时文件夹
		file.transferTo(dest);

		// 人脸数判断
		int num = photoService.photoFaceNum(dest.getAbsolutePath());
		if (num != 1) {
			return new FaceException("照片上人脸数量大于1");
		}
		// 照片头像截取并移动到正式文件夹
		File faceFile = new File(basePath, uuid + suffix);
		// dest.renameTo(faceFile);
		try {
			photoService.photoCutFace(dest.getAbsolutePath(), faceFile.getAbsolutePath());
		} finally {
			// 删除临时文件
			if (dest.exists()) {
				dest.delete();
			}
		}

		Photo photo = new Photo();
		photo.setPersonId(personId);
		photo.setPath(uuid);
		photo.setSuffix(suffix);
		photo.setUserId(SessionUtil.getCurrentUserId());
		// 更新数据库
		photoService.save(photo);

		// 转换成显示图像
		String contextpath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		String getPhotoPath = contextpath + "/photo/show?code=";
		PhotoResult pr = new PhotoResult();
		BeanUtils.copyProperties(photo, pr);
		pr.setPath(getPhotoPath + photo.getPath() + photo.getSuffix());

		return new CommonResult<>(pr);
	}

	@RequestMapping("/facetest")
	@ResponseBody
	public Object facetest() throws FaceException {
		String[] ss = new String[] { "C:\\Users\\Lenovo\\Pictures\\wo.jpg", "C:\\Users\\Lenovo\\Pictures\\wei.jpg" };
		String uf = "C:\\Users\\Lenovo\\Pictures\\wo2.jpg";

		return photoService.faceDistance(ss, uf);
	}
}
