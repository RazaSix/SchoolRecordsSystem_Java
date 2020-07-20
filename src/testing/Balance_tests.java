package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.input.DataFormat;

class Balance_tests {

	private LocalDate dob = LocalDate.of(2015,04,22);
	private classes.Balance balance = new classes.Balance();
	
	private classes.Child child1 = new classes.Child(001, "Jack", "Smith", dob, "No Allergies", 
			true, false, false);
	
	private classes.Child child2 = new classes.Child(002, "James", "Green", dob, "No Allergies", 
			true, false, false);
	
	private classes.Child child3 = new classes.Child(003, "Luke", "Waterson", dob, "No Allergies", 
			true, false, false);
	
	private ArrayList<classes.Child> childList = new ArrayList<classes.Child>();
	

 
    @BeforeEach
    public void beforeEachTestMethod() {
    	//System.out.println("Before running");
    	this.balance.getChildList().clear();
		this.childList.add(child1);
		this.childList.add(child2);
		this.childList.add(child3);
    	
		this.balance.setChildList(this.childList);
    }
	
    @AfterEach
	public void after() {
		//System.out.println("Should be after");
	}
    
    
	@Test
	void setPreschAmountOwedNonExistentChild() {
    	
		boolean result = this.balance.setPreschAmountOwed(004,100);
		assertFalse(result);
	}
	
	@Test
	void setPreschAmountPaidNonExistentChild() {	
		boolean result = this.balance.setPreschAmountOwed(004,100);
		assertFalse(result);
	}
	
	@Test
	void setPreSchArrearsNonExistentChild() {
		
		boolean result = this.balance.setPreSchArreas(004);
		assertFalse(result);
	}
	
	
	@Test
	void setPreschAmountOwedExistingChild() {		
		
		this.balance.setPreschAmountOwed(001,100);
		double value = this.balance.getPreSchAmountOwed(001);
		assertEquals(100, value);
	}
	
	
	@Test
	void setPreschAmountPaidExistingChild() {
		this.balance.setPreSchAmountPaid(001, 100);
		double value = this.balance.getPreSchAmountPaid(001);
		assertEquals(100, value);
	}
	
	
	@Test
	void getBalance() {
		double result = this.balance.getCurrBalance(100, 50);	
		assertEquals(50, result);
	}

}
