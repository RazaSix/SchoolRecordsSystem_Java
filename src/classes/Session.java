package classes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Session implements Serializable{
	
	private String sessionName;
	private LocalDate sessionDate;
	
	
	//Arraylists
	// ChildID, Days<Arraylist>, Lunch<Arraylist>
	private ArrayList<Child> preSchoolLunchSession = new ArrayList<Child>();
	private int[] preSchDropoffMinutesLate = new int[15];
	private int[] preSchPickupMinutesLate = new int[15];
	
	//ChildID, Breakfast<Arraylist>, Afterschool<Arraylist>
	private ArrayList<Child> breakfastSchoolSession = new ArrayList<Child>();
	private int[] breakfastDropoffMinutesLate = new int[15];
	private int[]  breakfastPickupMinutesLate = new int[15];
	
	
	private ArrayList<Child> afterSchoolSession = new ArrayList<Child>();
	private int[] afterSchoolDropoffMinutesLate = new int[15];
	private int[] afterSchoolPickupMinutesLate = new int[15];

	
	private static final int maxChildCount = 15; //Constant for the maximum amount of children per session
	
	
	public Session(LocalDate sessionDate) {
		this.sessionDate = sessionDate;
		this.sessionName = createSessionName(sessionDate);
	}
	
	
	//Setters

	//Attendance setters - Pick up	
/*	public void setPreSchPickupMinutesLate(int[] preSchPickupMinutesLate) {
		this.preSchPickupMinutesLate = preSchPickupMinutesLate;
	}*/
	public void setPreSchPickupMinutesLate(int position, int value) {
		this.preSchPickupMinutesLate[position] = value;
	}
	
/*	public void setPreSchDropoffMinutesLate(int[] preSchDropoffMinutesLate) {
		this.preSchDropoffMinutesLate = preSchDropoffMinutesLate;
	}*/
	
	public void setPreSchDropoffMinutesLate(int position, int value) {
		this.preSchDropoffMinutesLate[position] = value;
	}
	
	public void setBreakfastPickupMinutesLate(int[] breakfastPickupMinutesLate) {
		this.breakfastPickupMinutesLate = breakfastPickupMinutesLate;
	}

	public void setBreakfastDropoffMinutesLate(int[] breakfastDropoffMinutesLate) {
		this.breakfastDropoffMinutesLate = breakfastDropoffMinutesLate;
	}

	public void setAfterSchoolPickupMinutesLate(int[] afterSchoolPickupMinutesLate) {
		this.afterSchoolPickupMinutesLate = afterSchoolPickupMinutesLate;
	}
	
	public void setAfterSchoolDropoffMinutesLate(int[] afterSchoolDropoffMinutesLate) {
		this.afterSchoolDropoffMinutesLate = afterSchoolDropoffMinutesLate;
	}


	//Attendance setters - Drop off
	public int[] getPreSchPickupMinutesLate() {
		return preSchPickupMinutesLate;
	}

	public int[] getPreSchDropoffMinutesLate() {
		return preSchDropoffMinutesLate;
	}

	public int[] getBreakfastPickupMinutesLate() {
		return breakfastPickupMinutesLate;
	}

	public int[] getBreakfastDropoffMinutesLate() {
		return breakfastDropoffMinutesLate;
	}

	public int[] getAfterSchoolPickupMinutesLate() {
		return afterSchoolPickupMinutesLate;
	}

	public int[] getAfterSchoolDropoffMinutesLate() {
		return afterSchoolDropoffMinutesLate;
	}



	public String createSessionName(LocalDate sessionDate) {
		String firstPartYear = String.valueOf(sessionDate.getYear());
		String firstPart = String.valueOf(sessionDate.getDayOfMonth())+"."+sessionDate.getMonth().toString().substring(0, 3)+"."+firstPartYear.substring(2, 4);
		
		String createdSessionName = "Session-"+firstPart;
		
		return createdSessionName;
		
	}
	
	//Getters
	public LocalDate getSessionDate() {
		return sessionDate;
	}



	public String getSessionName() {
		return sessionName;
	}
	
	
	
	public boolean addPreSchoolSessionChild(Child child) {
	 	boolean check = false;
	 	int n = preSchoolLunchSession.size();
	 	
	 	//Clear arraylist of previous data
	 	
	 	if(n<maxChildCount) {
	 		preSchoolLunchSession.add(child);
	 		check = true;
	 	}
	 	
	 	
	 	return check;
	}
	
	
	//Add child info to breakfast school session
	public boolean addbreakfastSchoolSessionChild(Child child) {
		boolean check = false;
		int n = breakfastSchoolSession.size();
		
		
		if(n<maxChildCount) {
			breakfastSchoolSession.add(child);
			check = true;
		}
			
		return check;
	}
	
	
	//Add child info to after school session
	public boolean addAfterSchoolSessionChild(Child child) {
		boolean check = false;
		int n = afterSchoolSession.size();
		
		
		if(n<maxChildCount) {
			afterSchoolSession.add(child);
			check = true;
		}
			
		return check;
	}

	
	public ArrayList<Child> getPreSchSession(){
		return preSchoolLunchSession;
	}

	public ArrayList<Child> getBreakfastSchSession(){
		return breakfastSchoolSession;
	}
	
	public ArrayList<Child> getAfterSchSession(){
		return afterSchoolSession;
	}



}
