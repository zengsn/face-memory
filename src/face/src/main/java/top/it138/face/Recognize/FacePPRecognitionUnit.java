package top.it138.face.Recognize;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import top.it138.face.dto.PhotoData;
import top.it138.face.dto.RecognitionData;

@Service("facePPRecognitionUnit")
public class FacePPRecognitionUnit implements RecognitionUnit {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public String getName() {
		return "face++ api集成";
	}

	@Override
	public Double recognize(RecognitionData data) {
		byte[] bys = data.getRecognitionPhoto().getImgBytes();
		String image1Base64 = toBase64String(bys);
		List<PhotoData> photos = data.getPhotos();
		int num = photos.size();
		double[] result = new double[num];
		double sum = 0;
		CountDownLatch countDownLatch = new CountDownLatch(num);
		ExecutorService service = Executors.newCachedThreadPool();// 使用并发库，创建缓存的线程池
		for (int i = 0; i < num; i++) {
			PhotoData photo = photos.get(i);
			String image2Base64 = toBase64String(photo.getImgBytes());
			MyRunnable command = new MyRunnable(countDownLatch, result, i, image1Base64, image2Base64);
			service.execute(command);
		}
		service.shutdown();
		try {
			countDownLatch.await(20, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1.0;
		}
		for (int i = 0; i < num; i++) {
			logger.debug("第" + i + "个，分数为：" + result[i]);
			sum += result[i];
		}
		double d = sum / num;
		logger.info("识别结果为：" + d + ", sum=" + sum);
		return d;
	}

	class MyRunnable implements Runnable {
		private CountDownLatch countDownLatch;
		private double[] result;
		private String image1Base64;
		private String image2Base64;
		private int index;

		public MyRunnable(CountDownLatch countDownLatch, double[] result, int index, String image1Base64,
				String image2Base64) {
			super();
			this.result = result;
			this.image1Base64 = image1Base64;
			this.image2Base64 = image2Base64;
			this.index = index;
			this.countDownLatch = countDownLatch;
		}

		public void run() {
			Content content;
			try {
				content = Request.Post("https://api-cn.faceplusplus.com/facepp/v3/compare")
						.bodyForm(Form.form().add("api_key", "o8Xrt0L98Gxx_K9SQq2WpoRdaXne4LGv")
								.add("api_secret", "lx6eeRfCMNrlJ7tOgKikMCBjcbbpx6TQ")
								.add("image_base64_1", image1Base64).add("image_base64_2", image2Base64).build())
						.execute().returnContent();

				String json = content.asString();
				ObjectMapper mapper = new ObjectMapper(); // 转换器
				Map<?, ?> map = mapper.readValue(json, Map.class);
				Double d = (Double) map.get("confidence");
				if (d == null) {
					result[index] = 0;
				} else {
					result[index] = d / 100;
				}
				countDownLatch.countDown();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private String toBase64String(byte[] bys) {

		byte[] base64bys = Base64.encodeBase64(bys);
		String base64String = new String(base64bys);

		return base64String;
	}

}
