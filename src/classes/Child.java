package classes;

import java.time.LocalDate;
import java.util.ArrayList;

public class Child extends Person{


	private String allergyInfo;
	private boolean preschool;
	private boolean grant = false;
	private boolean sibling = false;
	private Parent[] childParent = new Parent[1];
	
	
	public Child(int childID,String cFname,String cSname,LocalDate dob, String allergyInfo, boolean preschool, boolean grant, boolean sibling) {
		super(childID,cFname,cSname,dob);
		this.allergyInfo = allergyInfo;
		this.preschool = preschool;
		this.grant = grant;
		this.sibling = sibling;
	}



	//Setters
	public void setAllergyInfo(String allergyInfo) {
		this.allergyInfo = allergyInfo;
	}

	public void setPreschool(boolean preschool) {
		this.preschool = preschool;
	}

	public void setGrant(boolean grant) {
		this.grant = grant;
	}

	public void setSibling(boolean sibling) {
		this.sibling = sibling;
	}



	//Add parent to child's parent list
	public boolean addParent(Parent parent) {
		boolean result = false;
		if(childParent[0] == null ) {
			childParent[0] = parent;
			result = true;
		}
		
		return result;
		
	}
	

	//Getters
	public String getAllergyInfo() {
		return allergyInfo;
	}

	
	public Parent getParent() {
		return childParent[0];
	}
	
	public Boolean getPreschool() {
		return preschool;
	}
	
	public Boolean getGrant() {
		return grant;
	}
	
	public Boolean getSiblingCheck() {
		return sibling;
	}










}
