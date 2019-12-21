package domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserLogin {
	//@NotNull(message="is required")
	@NotEmpty(message="User name cannot be empty")
	String userId;
	//@NotNull(message="is required")
	@NotEmpty(message="Password cannot be empty")
	String password;
	public UserLogin(){
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
