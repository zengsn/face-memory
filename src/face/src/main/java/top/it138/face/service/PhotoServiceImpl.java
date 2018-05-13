package top.it138.face.service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.it138.face.entity.Photo;
import top.it138.face.exception.FaceException;
import top.it138.face.util.PropertiesUtil;

@Service
public class PhotoServiceImpl extends BaseServiceImpl<Photo> implements PhotoService {
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	@Autowired
	private OkHttpClient client;
	@Autowired
	private PhotoService photoservice;
	private String pythonServer;
	private File basePath;

	public PhotoServiceImpl() {
		pythonServer = PropertiesUtil.getSystemProperties().getProperty("pythonServer");
		String path = PropertiesUtil.getSystemProperties().getProperty("photoSavePath");
		basePath = new File(path);
		if (!basePath.exists()) {
			basePath.mkdirs();
		}
	}

	@Override
	public List<Photo> selectByPersonId(Long personId) {
		Photo photo = new Photo();
		photo.setPersonId(personId);
		return mapper.select(photo);
	}

	@Override
	public int photoFaceNum(String path) throws FaceException {
		JsonObject obj = new JsonObject();
		obj.addProperty("path", path);

		Gson gson = new Gson();
		String json = gson.toJson(obj);
		String result = "";
		try {
			result = post("/faceNum", json);
			int num = gson.fromJson(result, Integer.class);
			return num;
		} catch (Exception e) {
			throw new FaceException(result, e);
		}
	}

	@Override
	public void photoCutFace(String srcPath, String dstPath) throws FaceException {
		JsonObject obj = new JsonObject();
		obj.addProperty("srcPath", srcPath);
		obj.addProperty("dstPath", dstPath);

		Gson gson = new Gson();
		String json = gson.toJson(obj);
		try {
			String result = post("/faceCut", json);
			System.out.println(result);
			if (!"success".equals(result)) {
				throw new FaceException(result);
			}
		} catch (IOException e) {
			throw new FaceException("连接python服务器发生异常", e);
		}
	}

	@Override
	public Double[] faceDistance(String[] compareFaces, String unknowFace) throws FaceException {
		JsonObject obj = new JsonObject();

		JsonArray arr = new JsonArray();
		for (String s : compareFaces)
			arr.add(s);
		obj.add("compareFaces", arr);
		obj.addProperty("unknowFace", unknowFace);
		;

		Gson gson = new Gson();
		String json = gson.toJson(obj);
		try {
			String result = post("/faceDistance", json);
			Double[] d = gson.fromJson(result, Double[].class);
			return d;
		} catch (IOException e) {
			throw new FaceException("连接python服务器发生异常", e);
		}
	}

	@Override
	public void deleteFaceByCode(final String code) throws IOException {
		FilenameFilter filter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (dir.equals(basePath) && name.startsWith(code)) {
					return true;
				}
				return false;
			}
		};
		File[] files = basePath.listFiles(filter);
		for (File f : files) {
			if (f.exists()) {
				f.delete();
			}
		}
	}

	@Override
	public void deleteFacceById(Long id) throws IOException {
		Photo photo = photoservice.selectById(id);

		File file = new File(basePath, photo.getPath() + photo.getSuffix());
		if (file.exists()) {
			file.delete();
		}

		// 删除faceEncodingFile
		File face = new File(basePath, photo.getPath() + ".face");
		if (face.exists()) {
			face.delete();
		}
	}

	private String post(String url, String json) throws IOException {
		url = pythonServer + url;
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder().url(url).post(body).build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}
}
