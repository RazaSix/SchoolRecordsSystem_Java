package classes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;

public class Term implements Serializable{
	
	private int termID;
	private String termName;
	private LocalDate termStartDate;
	private LocalDate termEndDate;
	
	private ArrayList<Session> Sessions = new ArrayList<>();
	private Balance termBalance = new Balance();
	
	public Term(int termID, LocalDate termStartDate, LocalDate termEndDate) {
		this.setTermStartDate(termStartDate);
		this.setTermEndDate(termEndDate);
		this.termName = createTermName(termStartDate, termEndDate);
		sessionSetup();
		System.out.println("Session Size: "+Sessions.size());
	}
	

	//Add a session to the term
	public void addSessionTerm(Session session) {
		this.Sessions.add(session);
	}


	//Setters
	
	public void setTermEndDate(LocalDate termEndDate) {
		this.termEndDate = termEndDate;
	}

	public void setTermStartDate(LocalDate termStartDate) {
		this.termStartDate = termStartDate;
	}

	public void setTermID(int termID) {
		this.termID = termID;
	}

	public void setTermBalance(Balance termBalance) {
		this.termBalance = termBalance;
	}
	
	
	//Create term name
	public String createTermName(LocalDate termStartDate, LocalDate termEndDate) {
		String firstPartYear = String.valueOf(termStartDate.getYear());
		String firstPart = String.valueOf(termStartDate.getDayOfMonth())+"."+termStartDate.getMonth().toString().substring(0, 3)+"."+firstPartYear.substring(2, 4);
		
		String secondPartYear = String.valueOf(termEndDate.getYear());
		String secondPart = String.valueOf(termEndDate.getDayOfMonth())+"."+termEndDate.getMonth().toString().substring(0, 3)+"."+secondPartYear.substring(2, 4);
		String createdTermName = "Term-"+firstPart+"/"+secondPart;
		
		return createdTermName;
	}
	
	
	public void sessionSetup() {
		LocalDate start = termStartDate;
		int daysBetween = (int) ChronoUnit.DAYS.between(termStartDate,termEndDate);
		for(int a = 0; a< daysBetween; a++) {
			Session newSession = new Session(start.plusDays(a));
			Sessions.add(newSession);
			//System.out.println("Start plus one day"+ start.plusDays(a));
		}
	}
	
	
	//Getters	
	public LocalDate getTermEndDate() {
		return termEndDate;
	}

	public LocalDate getTermStartDate() {
		return termStartDate;
	}

	public ArrayList<Session> getSessions() {
		return Sessions;
	}

	public String getTermName() {
		return termName;
	}
	
	public int getTermID() {
		return termID;
	}


	public Balance getTermBalance() {
		return termBalance;
	}

}
