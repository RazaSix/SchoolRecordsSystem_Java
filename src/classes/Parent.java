package classes;

import java.time.LocalDate;
import java.util.ArrayList;

public class Parent extends Person{

	private String email;
	private String address;
	public Parent(int parentID,String pFname,String pSname,String pAddress,LocalDate dob, String email) {
		super(parentID,pFname,pSname,dob);
		this.address = pAddress;
		this.email = email;
	}


	//Setters
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	//Getters
	public String getEmail() {
		return email;
	}
	
	public String getAddress() {
		return address;
	}
	
}
