package top.it138.face.vo;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * user registration form bean
 * @author Lenovo
 *
 */
public class UserRegistrationForm {
	@NotEmpty(message="{username.NotEmpty}")
	@Length(min=6, max=11, message="{username.length}")
	private String username;
	
	@NotEmpty(message="{password.NotEmpty}")
	@Length(min=6, max=16, message="{password.length}")
	private String password;
	
	@NotEmpty(message="{confirmPassword.NotEmpty}")
	private String confirmPassword;
	
	@Email(message="{email.invalid}")
	private String email;
	
	@NotEmpty(message = "{code.NotEmpty}")
	private String code;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
