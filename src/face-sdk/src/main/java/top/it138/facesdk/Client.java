package top.it138.facesdk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class Client {
	private final String BASE_URL = Constants.BASE_URL + "/api/" + Constants.VERSION;
	private final String PERSONS = BASE_URL + "/persons";
	@SuppressWarnings("unused")
	private final String appKey;
	@SuppressWarnings("unused")
	private final String appSecret;
	private final Header[] headers;
	private Gson gson = new Gson();

	public Client(String appKey, String appSecret) {
		super();
		this.appKey = appKey;
		this.appSecret = appSecret;
		headers = new Header[] { new BasicHeader("appKey", appKey), new BasicHeader("appSecret", appSecret) };
	}

	public List<Person> getPersons() throws FaceAppException {
		String json = get(PERSONS, null);
		List<Person> persons = gson.fromJson(json,  new TypeToken<List<Person>>(){}.getType());
		for (Person p : persons) {
			p.setClient(this);
		}
		return persons;
	}

	public Person getPersonById(Long id) throws FaceAppException {
		String json = get(PERSONS + "/" + id, null);
		
		Person person = gson.fromJson(json, Person.class);
		person.setClient(this);
		return person;
	}
	
	public void deletePerson(Long id) throws FaceAppException {
		String url = PERSONS  + "/" + id;
		delete(url, null);
		
	}
	
	public void addPhoto(Long personId, byte[] image, String imageType) throws FaceAppException {
		String url = PERSONS + "/" + personId + "/photos";
		if (image.length > 1024 * 1024 * 2) {
			throw new FaceAppException("照片太大，请确保图片小于2M");
		}
		String base64 = new String(Base64.encodeBase64(image));
		String imageBase64 = imageType + ";" + base64;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(nameValuePair("image", imageBase64));
		String json = post(url, params);
		if (!"上传成功".equals(json)) {
			throw new FaceAppException("上传失败,原因:" + json);
		}
	}

	public Long createNewPersonId(String name, String desc) throws FaceAppException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(nameValuePair("name", name));
		params.add(nameValuePair("des", desc));
		String json = post(PERSONS, params);
		Person person = gson.fromJson(json, Person.class);
		return person.getId();
	}
	
	public void deleteAllPhotos(Long personId) throws FaceAppException {
		String url = PERSONS + "/" + personId + "/photos";
		delete(url, null);
	}
	
	public Double recognition(Long personId, byte[] image, String imageType) throws FaceAppException {
		String url = PERSONS + "/" + personId + "/recognition";
		if (image.length > 1024 * 1024 * 2) {
			throw new FaceAppException("照片太大，请确保图片小于2M");
		}
		String base64 = new String(Base64.encodeBase64(image));
		String imageBase64 = imageType + ";" + base64;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(nameValuePair("image", imageBase64));
		String json = post(url, params);
		Double d = gson.fromJson(json, Double.class);
		
		return d;
	}
	
	private NameValuePair nameValuePair(String key, String value) {
		return new BasicNameValuePair(key, value);
	}

	private String get(String url, List<NameValuePair> params) throws FaceAppException {
		HttpClient  httpclient = new DefaultHttpClient();
		String str = "";
		if (params != null && params.size() > 0) {
			// 转换为键值对
			try {
				str = EntityUtils.toString(new UrlEncodedFormEntity(params));
			} catch (IOException e) {
				throw new FaceAppException("参数转换异常", e);
			}
		}
		HttpGet httpget = new HttpGet(url + "?" + str);
		httpget.setHeaders(headers);
		ResponseHandler<String> responseHandler = new StringResponseHandler();
		String webText = null;
		try {
			webText = httpclient.execute(httpget, responseHandler);
		} catch (IOException e) {
			throw new FaceAppException("结果异常", e);
		}
		return webText;
	}
	
	private String delete(String url, List<NameValuePair> params) throws FaceAppException {
		HttpClient httpclient = new DefaultHttpClient();
		String str = "";
		if (params != null && params.size() > 0) {
			// 转换为键值对
			try {
				str = EntityUtils.toString(new UrlEncodedFormEntity(params));
			} catch (IOException e) {
				throw new FaceAppException("参数转换异常", e);
			}
		}
		if (!str.equals("")) {
			url = url + "?" + str;
		}
		HttpDelete httpdelete = new HttpDelete(url);
		httpdelete.setHeaders(headers);
		ResponseHandler<String> responseHandler = new StringResponseHandler();
		String webText = null;
		try {
			webText = httpclient.execute(httpdelete, responseHandler);
		} catch (IOException e) {
			throw new FaceAppException("结果异常", e);
		}

		return webText;
	}

	private String post(String url, List<NameValuePair> params) throws FaceAppException {
		//用HttpClient发送请求，分为五步
        //第一步：创建HttpClient对象
        HttpClient httpCient = new DefaultHttpClient();
        //第二步：创建代表请求的对象,参数是访问的服务器地址
        HttpPost httpPost = new HttpPost(url);
		httpPost.setHeaders(headers);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params));
		} catch (UnsupportedEncodingException e1) {
			throw new FaceAppException("参数错误", e1);
		}
		ResponseHandler<String> responseHandler = new StringResponseHandler();
		String webText = null;
		try {
			webText = httpCient.execute(httpPost, responseHandler);
		} catch (IOException e) {
			throw new FaceAppException("结果异常", e);
		}

		return webText;
	}

	class StringResponseHandler implements ResponseHandler<String> {
		public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			int status = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			String result = entity != null ? EntityUtils.toString(entity) : null;
			if (status >= 200 && status < 300) {
				return result;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status + ", 响应:" + result);
			}
		}

	};
}
