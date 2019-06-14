package top.it138.face.Recognize;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import top.it138.face.exception.RecognitionException;

@Service("facePPRecognitionUnit")
public class FacePPRecognitionUnit implements RecognitionUnit {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String getName() {
		return "facepp";
	}

	private String toBase64String(byte[] bys) {

		byte[] base64bys = Base64.encodeBase64(bys);
		String base64String = new String(base64bys);

		return base64String;
	}

	@Override
	public Double recognize(String face1Path, String face2Path) throws RecognitionException {
		try {
			String face1 = fileToBase64(face1Path);
			String face2 = fileToBase64(face2Path);
			Content content;
			content = Request.Post("https://api-cn.faceplusplus.com/facepp/v3/compare")
					.bodyForm(Form.form().add("api_key", "o8Xrt0L98Gxx_K9SQq2WpoRdaXne4LGv")
							.add("api_secret", "lx6eeRfCMNrlJ7tOgKikMCBjcbbpx6TQ").add("image_base64_1", face1)
							.add("image_base64_2", face2).build())
					.execute().returnContent();
			String json = content.asString();
			ObjectMapper mapper = new ObjectMapper(); // 转换器
			Map<?, ?> map = mapper.readValue(json, Map.class);
			Double d = (Double) map.get("confidence");
			return d;
		} catch (IOException e) {
			throw new RecognitionException("人脸识别时发生异常,检查路径是否正确，python服务器是否开启", e);
		}
	}

	private String fileToBase64(String path) throws IOException {
		return toBase64String(fileToByteArray(path));
	}

	private byte[] fileToByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		try {
			byte[] bys = IOUtils.toByteArray(fis);
			return bys;
		} finally {
			IOUtils.closeQuietly(fis);
		}
	}

	@Override
	public Double[] recognize(String[] compareFacesPath, String unknowFacePath) {
		throw new UnsupportedOperationException("该方法不支持");
	}
}
