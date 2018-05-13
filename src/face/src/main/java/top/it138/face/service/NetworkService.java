package top.it138.face.service;

import java.io.IOException;


public interface NetworkService {
	byte[] getByteArrayByURL(String url) throws IOException;
	String post(String url, String json) throws IOException;
	String get(String url) throws IOException;
}
