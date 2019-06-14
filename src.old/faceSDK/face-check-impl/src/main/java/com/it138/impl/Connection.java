package com.it138.impl;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.it138.facecheck.FindForm;
import top.it138.facecheck.Group;
import top.it138.facecheck.Person;
import top.it138.facecheck.RecoginitionException;

public class Connection implements top.it138.facecheck.Connection {
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public static final String APP_SECRET = "appSecret";
	public static final String APP_KEY = "appKey";
	public static final String SERVER = "server";
	private static final String READ_TIMEOUT = "readTimeout";
	private static final String DEFAULT_READ_TIMEOUT = "20";
	public static final String CONNECT_TIMEOUT = "connectTimeOut";
	public static final String DEFAULT_CONNECT_TIMEOUT = "10";
	public static final String SUCCESS = "success";
	public static final String ERROR_MSG = "errorMsg";
	public static final String DATA = "data";
	private final String appKey;
	private final String appSecret;
	private final String server;
	private final String baseUrl;
	private final String compareBase64Url;
	private final String compareURL;
	private final String allGroupUrl;
	private final String getPersonsWithGroupUrl; 
	private final String getAllPersonUrl;  
	private final String findUrl;
	private Gson gson;
	private JsonParser parser;
	private OkHttpClient client;
	private Headers headers;
	
	public Connection(Properties properties) throws IllegalArgumentException {
		Builder builder = new OkHttpClient.Builder();
		long connectTimeout = Long.parseLong(properties.getProperty(CONNECT_TIMEOUT, DEFAULT_CONNECT_TIMEOUT));
		long readTimeout = Long.parseLong(properties.getProperty(READ_TIMEOUT, DEFAULT_READ_TIMEOUT));
		appKey = properties.getProperty(APP_KEY);
		appSecret = properties.getProperty(APP_SECRET);
		String s = properties.getProperty(SERVER);
		if (appKey == null || appSecret == null || s == null) {
			throw new IllegalArgumentException("请填写appKey和appSecret和服务器地址参数");
		}
		if (!s.endsWith("/") || s.endsWith("\\")) {
			s += "/";
		}
		server = s;
		headers = new Headers.Builder().add("appKey", appKey)
				.add("appSecret", appSecret)
				.add("Content-Type", "application/json").build();
		client = builder.connectTimeout(connectTimeout, TimeUnit.SECONDS)  
		        .readTimeout(readTimeout, TimeUnit.SECONDS)
		        .build();
		gson = new Gson();
		parser = new JsonParser();
		
		baseUrl = server + "api/2.0/";
		compareBase64Url = baseUrl + "compareBase64";
		compareURL = baseUrl + "compareURL";
		allGroupUrl = baseUrl + "group";
		getPersonsWithGroupUrl = baseUrl + "{groupId}/person"; 
		getAllPersonUrl = baseUrl + "person";  
		findUrl = baseUrl + "find";
	}

	public double distance(byte[] img1, byte[] img2) throws RecoginitionException {
		String base64Img1 = Base64Util.encode(img1);
		String base64Img2 = Base64Util.encode(img2);
		
		return distance(base64Img1, base64Img2);
	}

	public double distance(String base64Img1, String base64Img2) throws RecoginitionException {
		JsonObject json = new JsonObject();
		json.addProperty("img1", base64Img1);
		json.addProperty("img2", base64Img2);
		String result = null;
		try {
			result = post(compareBase64Url, json.toString());
			
		} catch (IOException e) {
			throw new RecoginitionException("网络IO异常");
		}
		
		 JsonObject obj = parser.parse(result).getAsJsonObject();
		 if (!obj.get(SUCCESS).getAsBoolean()) {
			 throw new RecoginitionException("服务器异常:" + obj.get(ERROR_MSG).getAsString());
		 }
		 return obj.get(DATA).getAsDouble();
	}
	
	public double distance(URL url1, URL url2) throws RecoginitionException {
		JsonObject json = new JsonObject();
		json.addProperty("img1Url", url1.toString());
		json.addProperty("img2Url", url2.toString());
		String result = null;
		try {
			result = post(compareURL, json.toString());
			
		} catch (IOException e) {
			throw new RecoginitionException("网络IO异常");
		}
		
		 JsonObject obj = parser.parse(result).getAsJsonObject();
		 if (!obj.get(SUCCESS).getAsBoolean()) {
			 throw new RecoginitionException("服务器异常:" + obj.get(ERROR_MSG).getAsString());
		 }
		 return obj.get(DATA).getAsDouble();
	}

	public List<Group> getAllGroups() {
		try {
			String result = get(allGroupUrl);
			ResultGroupList resultObj = gson.fromJson(result, ResultGroupList.class);
			if (resultObj.isSuccess()) {
				return resultObj.getData();
			} else {
				throw new RuntimeException("服务器错误:" + resultObj.getErrorMsg());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}



	public String post(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder().url(url).post(body).headers(headers).build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	public String get(String url) throws IOException {
		Request request = new Request.Builder().url(url).headers(headers).build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	public List<Person> getPersons(Long GroupId) throws IOException {
		String url = getPersonsWithGroupUrl.replace("{groupId}", GroupId.toString());
		try {
			String result = get(url);
			ResultPersonList resultObj = gson.fromJson(result, ResultPersonList.class);
			if (resultObj.isSuccess()) {
				return resultObj.getData();
			} else {
				throw new RuntimeException("服务器错误:" + resultObj.getErrorMsg());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Person> getAllPersons() throws IOException {
		try {
			String result = get(getAllPersonUrl);
			ResultPersonList resultObj = gson.fromJson(result, ResultPersonList.class);
			if (resultObj.isSuccess()) {
				return resultObj.getData();
			} else {
				throw new RuntimeException("服务器错误:" + resultObj.getErrorMsg());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Person find(FindForm form) throws RecoginitionException {
		String json = gson.toJson(form);
		try {
			String result = post(findUrl, json);
			ResultPerson resultObj = gson.fromJson(result, ResultPerson.class);
			if (resultObj.isSuccess()) {
				return resultObj.getData();
			}
			
			throw new RecoginitionException("服务器错误:" + resultObj.getErrorMsg());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RecoginitionException("IO异常");
		}
	}

	
}
