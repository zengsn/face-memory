package top.it138.face.service;

public interface MailService {

	/**
	 * 发送邮件
	 * @param email 对方邮件地址
	 * @param subject 标题
	 * @param text 发送内容
	 * 
	 */
	void sendText(String email, String subject, String text);

}
