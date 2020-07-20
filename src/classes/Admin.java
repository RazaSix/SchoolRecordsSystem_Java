package classes;

import java.time.LocalDate;

public class Admin extends Person{

	private String email;
	private String password;
	private String address;	
	private String username;
	
	public Admin(int adminID,String aFname,String aLname,String aAddress,LocalDate dob, String email, String password) {
		super(adminID,aFname,aLname,dob);
		this.address = aAddress;
		this.email = email;
		this.password = password;
		
		this.setUsername(createUsername(adminID,aFname,aLname));
	}

	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	public String getPassword() {
		return password;
	}
	
	
	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getAddress() {
		return address;
	}
	
	public String createUsername(int parentID, String fName, String sName) {
		String id = String.valueOf(parentID);
		String username = fName.substring(0, 1)+sName+id.substring(5, id.length());
		
		return username;
	}





	
	
}
