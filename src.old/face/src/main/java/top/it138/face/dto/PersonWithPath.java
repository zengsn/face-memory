package top.it138.face.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import top.it138.face.entity.Person;

public class PersonWithPath extends Person{
	private List<String> urls;
	private List<String> paths;

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}
	
	public void addUrl(String url) {
		if (urls == null) {
			urls = new ArrayList<String>();
		}
		urls.add(url);
	}

	@JsonIgnore
	public List<String> getPaths() {
		return paths;
	}

	public void setPaths(List<String> paths) {
		this.paths = paths;
	}
	
	public void addPath(String path) {
		if (paths == null) {
			paths = new ArrayList<String>();
		}
		paths.add(path);
	}
}
