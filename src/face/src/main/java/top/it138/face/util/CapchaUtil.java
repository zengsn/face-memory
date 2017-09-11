package top.it138.face.util;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.cage.Cage;
import com.github.cage.GCage;

public class CapchaUtil {
	public static final int CAPCHS_LENGHT = 6;
	private static final Cage cage = new GCage(); // capcha code style
	public static final String SESSION_CAPCHA_TOKEN = "captchaToken";
	private static final char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J','K', 'L',   
			'M', 'N',  'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W','X', 'Y','a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k', 'l',   
			'm', 'n',  'p', 'q', 'r', 's', 't', 'u', 'v', 'w','x', 'y',    
			'Z',  '1', '2', '3', '4', '5', '6', '7', '8', '9' };  

	public static void writeCapcha(HttpServletRequest req, HttpServletResponse resp) {
		//String token = cage.getTokenGenerator().next();
		String token = getRandomToken(CAPCHS_LENGHT);
		HttpSession session = req.getSession(true);
		session.setAttribute(SESSION_CAPCHA_TOKEN, token);
		resp.setContentType("image/" + cage.getFormat());
		resp.setHeader("Cache-Control", "no-cache, no-store");
		resp.setHeader("Pragma", "no-cache");
		long time = System.currentTimeMillis();
		resp.setDateHeader("Last-Modified", time);
		resp.setDateHeader("Date", time);
		resp.setDateHeader("Expires", time);

		try {
			cage.draw(token, resp.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getRandomToken(int size) {
		char[] chs = new char[size];
		for (int i = 0; i < size; i++) {
			int rand = ThreadLocalRandom.current().nextInt(codeSequence.length);
			chs[i] = codeSequence[rand];
		}
		
		return new String(chs);
	}

	/**
	 * get capcha code from session
	 * 
	 * @return the capcha code. return null if can not find in the session
	 */
	public static String getCapcha() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		
		HttpSession session = request.getSession();

		return (String) session.getAttribute(SESSION_CAPCHA_TOKEN);
	}

	/**
	 * clear session capcha
	 */
	public static void clearCapcha() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute(SESSION_CAPCHA_TOKEN);

	}
}
