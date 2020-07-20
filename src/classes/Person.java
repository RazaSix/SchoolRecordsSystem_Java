package classes;

import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Serializable{

	private int ID;
	private String fname;
	private String sname;
	private LocalDate dob;
	
	
	public Person(int iD, String fname, String sname, LocalDate dob) {
		ID = iD;
		this.fname = fname;
		this.sname = sname;
		this.dob = dob;
	}
	
	//Setters
	public void setID(int iD) {
		ID = iD;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	
	//Getters
	public int getID() {
		return ID;
	}
	public String getFname() {
		return fname;
	}
	public String getSname() {
		return sname;
	}

	public LocalDate getDob() {
		return dob;
	}
	
	
}
