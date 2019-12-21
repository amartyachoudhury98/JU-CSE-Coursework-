package domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {
	@NotEmpty(message="is required")
	String userId;
	@NotEmpty(message="is required")
	String firstname;
	@NotEmpty(message="is required")
	String lastname;
	@NotEmpty(message="is required")
	@Size(min=10, max=10,message="must be 10 digits")
	String mobileNo;
	@NotEmpty(message="is required")
	String password;
	public User(String userId,String firstname,String lastname,String mobileNo,String password){
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.mobileNo = mobileNo;
		this.password = password;
	}
	public User(){
		
	}
	public User(String userId,String password) {
		this.userId = userId;
		this.password = password;
	}
	public String getId() {
		return userId;
	}
	public String firstname() {
		return firstname;
	}
	public String lastname() {
		return lastname;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String mobileNo() {
		return mobileNo;
	}
	public String password() {
		return password;
	}
}

