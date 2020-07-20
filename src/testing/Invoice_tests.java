package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import classes.Invoice;

class Invoice_tests {

	private Invoice invoice = new Invoice();
	
    @BeforeEach
    public void beforeEachTestMethod() {
    	
    }
    
    @AfterEach
	public void after() {
		//System.out.println("Should be after");
	}
	
	@Test
	void collectedMinutes() {
		ArrayList<Integer> collectedMinutes = new ArrayList<Integer>();
    	for(int i = 0; i <= 5; i++) {
    		collectedMinutes.add(i);
    	}
    	
    	int result = invoice.minutesCalc(collectedMinutes);
    	
    	assertEquals(150, result);
	}
	
	
	@Test
	void sessionDateTotal() {
		//preschLunchSessionTotal(ArrayList<String> bookedSessionList)
		
		ArrayList<String> sessionList = new ArrayList<String>();
		sessionList.add("12/11/2018");
		sessionList.add("22/11/2018");
		sessionList.add("24/11/2018");
		
		double result = invoice.preschLunchSessionTotal(sessionList);
		
		assertEquals(5.25, result);
	}
	
	@Test
	void simpleBalanceNoDiscount() {
		//double simplePreschBalance(ArrayList<String> bookedSessionList, ArrayList<Integer> dropoffMinutes, ArrayList<Integer> pickupMinutes, 
		//		boolean siblingDiscount, boolean grantStatus)
		
		ArrayList<String> sessionList = new ArrayList<String>();
		sessionList.add("12/11/2018");
		sessionList.add("22/11/2018");
		sessionList.add("24/11/2018");
		
		ArrayList<Integer> dropoffMinutes = new ArrayList<Integer>();
    	for(int i = 0; i <= 5; i++) {
    		dropoffMinutes.add(i);
    	}
    	
    	ArrayList<Integer> pickupMinutes = new ArrayList<Integer>();
    	for(int i = 0; i <= 5; i++) {
    		pickupMinutes.add(i);
    	}
    	
    	
    	double simpleBalance = invoice.simplePreschBalance(sessionList, dropoffMinutes, pickupMinutes, false, false);
    	assertEquals(341.25, simpleBalance);
	}

	@Test
	void simpleBalanceSiblingDiscount() {
		//double simplePreschBalance(ArrayList<String> bookedSessionList, ArrayList<Integer> dropoffMinutes, ArrayList<Integer> pickupMinutes, 
		//		boolean siblingDiscount, boolean grantStatus)
		
		ArrayList<String> sessionList = new ArrayList<String>();
		sessionList.add("12/11/2018");
		sessionList.add("22/11/2018");
		sessionList.add("24/11/2018");
		
		ArrayList<Integer> dropoffMinutes = new ArrayList<Integer>();
    	for(int i = 0; i <= 5; i++) {
    		dropoffMinutes.add(i);
    	}
    	
    	ArrayList<Integer> pickupMinutes = new ArrayList<Integer>();
    	for(int i = 0; i <= 5; i++) {
    		pickupMinutes.add(i);
    	}
    	
    	
    	double simpleBalance = invoice.simplePreschBalance(sessionList, dropoffMinutes, pickupMinutes, true, false);
    	assertEquals(337.65, simpleBalance);
	}
	
	
	@Test
	void simpleBalanceGrantDiscount() {
		//double simplePreschBalance(ArrayList<String> bookedSessionList, ArrayList<Integer> dropoffMinutes, ArrayList<Integer> pickupMinutes, 
		//		boolean siblingDiscount, boolean grantStatus)
		
		ArrayList<String> sessionList = new ArrayList<String>();
		sessionList.add("12/11/2018");
		sessionList.add("22/11/2018");
		sessionList.add("24/11/2018");
		
		ArrayList<Integer> dropoffMinutes = new ArrayList<Integer>();
    	for(int i = 0; i <= 5; i++) {
    		dropoffMinutes.add(i);
    	}
    	
    	ArrayList<Integer> pickupMinutes = new ArrayList<Integer>();
    	for(int i = 0; i <= 5; i++) {
    		pickupMinutes.add(i);
    	}
    	
    	
    	double simpleBalance = invoice.simplePreschBalance(sessionList, dropoffMinutes, pickupMinutes, false, true);
    	assertEquals(0, simpleBalance);
	}
	
}
