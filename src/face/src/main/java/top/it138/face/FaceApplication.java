package top.it138.face;

import java.util.concurrent.TimeUnit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import okhttp3.OkHttpClient;
import top.it138.face.interceptor.AppKeyLoginInterceptor;
import top.it138.face.service.AppService;

@SpringBootApplication
@MapperScan(basePackages = "top.it138.face.mapper")
public class FaceApplication extends WebMvcConfigurerAdapter {
	@Autowired
	private AppKeyLoginInterceptor appKeyLoginInterceptor;

	public static void main(String[] args) {
		SpringApplication.run(FaceApplication.class, args);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration Interceptor = registry.addInterceptor(appKeyLoginInterceptor);
		Interceptor.addPathPatterns("/api/**");
		super.addInterceptors(registry);
	}
	
	
	@Bean
	public OkHttpClient getOkHttpClient() {
		OkHttpClient client = new OkHttpClient.Builder()  
		        .connectTimeout(10, TimeUnit.SECONDS)  
		        .readTimeout(20, TimeUnit.SECONDS)  
		        .build(); 
		return client;
	}
}
