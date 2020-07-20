package classes;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class Invoice {
	
	private double preschLunchPrice = 1.75;
	private double preschNonGrantPrice = 12.00;
	
	private double breakfastPrice = 4.50;
	private double afterschoolPrice = 8;
	
	private int lateCharge = 10;
	
	DecimalFormat formatter = new DecimalFormat("0.00");
	//formatter.format(ounces)

	public Invoice() {
		
	}

	public int minutesCalc(ArrayList<Integer> collectedMinutes) {
		int total = 0;
		
		for(int i = 0; i < collectedMinutes.size(); i++) {
			total += collectedMinutes.get(i); 
		}
		
		return total*lateCharge;
	}
	
	/*
	 * Pre-School
	 */
	public double preschLunchSessionTotal(ArrayList<String> bookedSessionList) {
		double total = bookedSessionList.size() * this.preschLunchPrice;
		
		return total;
	}
	
	public double initialTotal(ArrayList<String> bookedSessionList) {
		double initialTotal = bookedSessionList.size() * this.preschNonGrantPrice;
		
		return initialTotal;
	}
	
	public double preschDiscountTotal(ArrayList<String> bookedSessionList, boolean siblingDiscount) {
		double sibling = 0.90;
		double discountTotal = 0;

		
		if(siblingDiscount == false) {
			sibling = 1;
		}
		
		discountTotal = ((bookedSessionList.size() * this.preschNonGrantPrice)*sibling);
		
		return discountTotal;
	}
	
	public double preschFinalTotal(double lunchSessionTotal, double discountTotal, int dropoffMinutes, int pickupMinutes, boolean grantStatus) {
		int grant = 1;
		
		if(grantStatus == true) {
			grant = 0;
		}
		double total = (lunchSessionTotal + discountTotal + dropoffMinutes + pickupMinutes) * grant ;
		
		return total;
	}
	
	public String preschSummary(ArrayList<String> bookedSessionList, ArrayList<Integer> dropoffMinutes, ArrayList<Integer> pickupMinutes, 
			boolean siblingDiscount, boolean grantStatus) 
	{
		
		double lunchSessionTotal = preschLunchSessionTotal(bookedSessionList);
		double initialTotal = initialTotal(bookedSessionList);
		double discountTotal = preschDiscountTotal(bookedSessionList, siblingDiscount);
		int totalDropoffMinutes = minutesCalc(dropoffMinutes);
		int totalPickupMinutes = minutesCalc(pickupMinutes);
		double finalTotal = preschFinalTotal(lunchSessionTotal, discountTotal, totalDropoffMinutes, totalPickupMinutes, grantStatus);
		
		String disp = "Preschool Non-Grant Price :£"+preschNonGrantPrice+" x "+bookedSessionList.size()+" = £"+formatter.format(initialTotal)+
				"\nPreschool Non-Grant Lunch Price: £"+preschLunchPrice+" x "+bookedSessionList.size()+" = £"+formatter.format(lunchSessionTotal)+
				"\nLate Charge £"+ lateCharge +" per 5 minutes "+"\n\nDropoff Minutes Late: £"+totalDropoffMinutes+"\nPickup Minutes Total: £"+totalPickupMinutes+
				"\n\nSibling Discount Status: "+siblingDiscount+ "\nGrant Status: "+grantStatus+
				"\n\nFinal Total: £"+formatter.format(finalTotal);
		
		return disp;
	}
	
	public double simplePreschBalance(ArrayList<String> bookedSessionList, ArrayList<Integer> dropoffMinutes, ArrayList<Integer> pickupMinutes, 
			boolean siblingDiscount, boolean grantStatus) {
		double lunchSessionTotal = preschLunchSessionTotal(bookedSessionList);
		double initialTotal = initialTotal(bookedSessionList);
		double discountTotal = preschDiscountTotal(bookedSessionList, siblingDiscount);
		int totalDropoffMinutes = minutesCalc(dropoffMinutes);
		int totalPickupMinutes = minutesCalc(pickupMinutes);
		double finalTotal = preschFinalTotal(lunchSessionTotal, discountTotal, totalDropoffMinutes, totalPickupMinutes, grantStatus);
		
		
		//String disp = "Amount Owed: £"+formatter.format(finalTotal);
		
		return finalTotal;
	}
	/*
	 * School
	 */
	public double breakfastSessionTotal(ArrayList<String> bookedSessionList) {
		double total = bookedSessionList.size() * this.breakfastPrice;
		
		return total;
	}
	
	
	public double breakfastFinalTotal(double breakfastSessionTotal, int dropoffMinutes, int pickupMinutes) {
		double total = breakfastSessionTotal + dropoffMinutes + pickupMinutes;
		
		return total;
	}
	
	public double afterschoolSessionTotal(ArrayList<String> bookedSessionList) {
		double total = bookedSessionList.size() * this.afterschoolPrice;
		
		return total;
	}
	
	public double afterschoolFinalTotal(double afterschoolSessionTotal, int dropoffMinutes, int pickupMinutes) {
		double total = afterschoolSessionTotal + dropoffMinutes + pickupMinutes;
		
		return total;
	}
	
	
	public String schBreakSummary(ArrayList<String> bookedSessionList, ArrayList<Integer> dropoffMinutes, ArrayList<Integer> pickupMinutes) {
		double breakfastSessionTotal = breakfastSessionTotal(bookedSessionList);
		int totalDropoffMinutes = minutesCalc(dropoffMinutes);
		int totalPickupMinutes = minutesCalc(pickupMinutes);
		double finalBrekTotal = breakfastFinalTotal(breakfastSessionTotal, totalDropoffMinutes, totalPickupMinutes);
		
		String disp = "Breakfast Price: £"+formatter.format(breakfastPrice)+" x "+bookedSessionList.size()+
				"\nSubTotal: £"+formatter.format(breakfastSessionTotal)+
				"\n\nLate Charge £"+ lateCharge +" per 5 minutes "+"\nDropoff Minutes Late: £"+formatter.format(totalDropoffMinutes)+
				"\nPickup Minutes Total: £"+formatter.format(totalPickupMinutes)+
				"\n\nFinal Total: £"+formatter.format(finalBrekTotal);
		
		return disp;
	}
	
	
	public double simpleBrekSchBalance(ArrayList<String> bookedSessionList, ArrayList<Integer> dropoffMinutes, ArrayList<Integer> pickupMinutes) {
		double sessionTotal = breakfastSessionTotal(bookedSessionList);
		int totalDropoffMinutes = minutesCalc(dropoffMinutes);
		int totalPickupMinutes = minutesCalc(pickupMinutes);
		double finalTotal = breakfastFinalTotal(sessionTotal, totalDropoffMinutes, totalPickupMinutes);
		
		//String disp = "Amount Owed: £"+formatter.format(finalTotal);
		
		return finalTotal;
	}
	
	public double simpleAfterSchBalance(ArrayList<String> bookedSessionList, ArrayList<Integer> dropoffMinutes, ArrayList<Integer> pickupMinutes) {
		double sessionTotal = afterschoolSessionTotal(bookedSessionList);
		int totalDropoffMinutes = minutesCalc(dropoffMinutes);
		int totalPickupMinutes = minutesCalc(pickupMinutes);
		double finalTotal = afterschoolFinalTotal(sessionTotal, totalDropoffMinutes, totalPickupMinutes);
		
		//String disp = "Amount Owed: £"+formatter.format(finalTotal);
		
		return finalTotal;
	}
	
	
	public String schAfterSchSummary(ArrayList<String> bookedSessionList, ArrayList<Integer> dropoffMinutes, ArrayList<Integer> pickupMinutes) {
		double afterschSessionTotal = afterschoolSessionTotal(bookedSessionList);
		int totalDropoffMinutes = minutesCalc(dropoffMinutes);
		int totalPickupMinutes = minutesCalc(pickupMinutes);
		double finalAfterSchTotal = afterschoolFinalTotal(afterschSessionTotal, totalDropoffMinutes, totalPickupMinutes);	
		
		String disp = "Afterschool Price: £"+formatter.format(afterschoolPrice)+" x "+bookedSessionList.size()+
				"\nSubTotal: £"+formatter.format(afterschSessionTotal)+
				"\n\nLate Charge £"+ formatter.format(lateCharge) +" per 5 minutes "+"\nDropoff Minutes Late: £"+formatter.format(totalDropoffMinutes)+
				"\nPickup Minutes Total: £"+formatter.format(totalPickupMinutes)+
				"\n\nFinal Total: £"+formatter.format(finalAfterSchTotal);
		
		return disp;
	}
}
