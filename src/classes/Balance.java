package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Balance implements Serializable{

	
	private ArrayList<Child> childList = new ArrayList<Child>();
	
	private ArrayList<Double> preschAmountOwed = new ArrayList<Double>(Collections.nCopies(60, 0.0));
	
	
	
	private ArrayList<Double> breakSchAmountOwed = new ArrayList<Double>(Collections.nCopies(60, 0.0));
	private ArrayList<Double> afterSchAmountOwed = new ArrayList<Double>(Collections.nCopies(60, 0.0));
	
	private ArrayList<Double> preschAmountPaid = new ArrayList<Double>(Collections.nCopies(60, 0.0));
	private ArrayList<Double> breakSchAmountPaid = new ArrayList<Double>(Collections.nCopies(60, 0.0));
	private ArrayList<Double> afterSchAmountPaid = new ArrayList<Double>(Collections.nCopies(60, 0.0));
	
	private double arrearsValue = 5;
	
	public Balance() {
		/*for(int i = 0; i < 60; i++) {
			preschAmountOwed.add(0.0);
		}*/
	}
	
	//Setters
	public void setChildList(ArrayList<Child> childList) {
		this.childList = childList;
	}
	
	
	public boolean setPreSchArreas(int childID) {
		//find childid and add arrearsValue to amount owed
		
		boolean success = false;
		int index;
		try {
		 index = getChildIndex(childID);
		}catch(IndexOutOfBoundsException ib) {
			index = -1;
		}
		
		if(index >= 0) {
			double value = preschAmountOwed.get(index);
			value+= arrearsValue;
			preschAmountOwed.add(index, value);
			
			success = true;
		}
		
		return success;
		
	}
	
	public boolean setBreakSchArreas(int childID) {
		//find childid and add arrearsValue to amount owed
		
		boolean success = false;
		int index;
		try {
			 index = getChildIndex(childID);
			}catch(IndexOutOfBoundsException ib) {
				index = -1;
			}
		
		if(index >= 0) {
			double value = breakSchAmountOwed.get(index);
			value+= arrearsValue;
			breakSchAmountOwed.add(index, value);
			
			success = true;
		}
		
		return success;
		
	}
	
	public boolean setAfterSchArreas(int childID) {
		//find childid and add arrearsValue to amount owed
		
		boolean success = false;
		
		int index;
		try {
			 index = getChildIndex(childID);
			}catch(IndexOutOfBoundsException ib) {
				index = -1;
			}
		
		if(index >= 0) {
			double value = afterSchAmountOwed.get(index);
			value+= arrearsValue;
			afterSchAmountOwed.add(index, value);
			
			success = true;
		}
		
		return success;
		
	}
	
	/*
	 * SET AMOUNT OWED
	 */
	public boolean setPreschAmountOwed(int childID, double inAmountOwed) {
		//find childid and put amount owed in corresponding index
		// equals current value plus incomming value
		boolean success = false;
		
		int index;
		try {
			 index = getChildIndex(childID);
			}catch(IndexOutOfBoundsException ib) {
				index = -1;
			}
		
		if(index >= 0) {
			double value = preschAmountOwed.get(index);
			value+= inAmountOwed;
			preschAmountOwed.set(index, value);
			
			success = true;
		}
		
		return success;
	}
	
	public boolean setBreakSchAmountOwed(int childID, double inAmountOwed) {
		//find childid and put amount owed in corresponding index
		// equals current value plus incomming value
		boolean success = false;
		
		int index;
		try {
			 index = getChildIndex(childID);
			}catch(IndexOutOfBoundsException ib) {
				index = -1;
			}
		
		if(index >= 0) {
			double value = breakSchAmountOwed.get(index);
			value+= inAmountOwed;
			breakSchAmountOwed.set(index, value);
			
			success = true;
		}
		
		return success;
	}
	
	
	public boolean setAfterSchAmountOwed(int childID, double inAmountOwed) {
		//find childid and put amount owed in corresponding index
		// equals current value plus incomming value
		boolean success = false;
		
		int index;
		try {
			 index = getChildIndex(childID);
			}catch(IndexOutOfBoundsException ib) {
				index = -1;
			}
		
		if(index >= 0) {
			double value = afterSchAmountOwed.get(index);
			value+= inAmountOwed;
			afterSchAmountOwed.set(index, value);
			
			success = true;
		}
		
		return success;
	}
	
	/*
	 * SET AMOUNT PAID
	 */
	
	public boolean setPreSchAmountPaid(int childID, double inAmountPaid) {
		//find childID and put amount owed in corresponding index
		//equals current value plus incoming value
		
		boolean success = false;
		
		int index;
		try {
			 index = getChildIndex(childID);
			}catch(IndexOutOfBoundsException ib) {
				index = -1;
			}
		
		if(index >= 0) {
			double value = preschAmountPaid.get(index);
			value+= inAmountPaid;
			preschAmountPaid.set(index, value);
			
			success = true;
		}
		
		return success;
	}
	
	public boolean setBreakSchAmountPaid(int childID, double inAmountPaid) {
		//find childID and put amount owed in corresponding index
		//equals current value plus incoming value
		
		boolean success = false;
		
		int index;
		try {
			 index = getChildIndex(childID);
			}catch(IndexOutOfBoundsException ib) {
				index = -1;
			}
		
		if(index >= 0) {
			double value = breakSchAmountPaid.get(index);
			value+= inAmountPaid;
			breakSchAmountPaid.set(index, value);
			
			success = true;
		}
		
		return success;
	}
	
	public boolean setAfterSchAmountPaid(int childID, double inAmountPaid) {
		//find childID and put amount owed in corresponding index
		//equals current value plus incoming value
		
		boolean success = false;
		
		int index;
		try {
			 index = getChildIndex(childID);
			}catch(IndexOutOfBoundsException ib) {
				index = -1;
			}
		
		if(index >= 0) {
			double value = afterSchAmountPaid.get(index);
			value+= inAmountPaid;
			afterSchAmountPaid.set(index, value);
			
			success = true;
		}
		
		return success;
	}
	
	//Clear values
	public void clearAll() {
		preschAmountOwed = new ArrayList<Double>(Collections.nCopies(60, 0.0));
		breakSchAmountOwed = new ArrayList<Double>(Collections.nCopies(60, 0.0));
		afterSchAmountOwed = new ArrayList<Double>(Collections.nCopies(60, 0.0));
		
		/*
		preschAmountPaid
		breakSchAmountPaid
		afterSchAmountPaid
		*/
	}
	
	//Getters
	
	/*
	 * GET AMOUNT OWED
	 */
	public double getPreSchAmountOwed(int childID) {
		int index;
		try {
			 index = getChildIndex(childID);
			}catch(IndexOutOfBoundsException ib) {
				index = -1;
			}
		
		double outAmountOwed = preschAmountOwed.get(index);
		
		return outAmountOwed;
	}
	
	public double getBreakSchAmountOwed(int childID) {
		int index;
		try {
			 index = getChildIndex(childID);
			}catch(IndexOutOfBoundsException ib) {
				index = -1;
			}
		
		double outAmountOwed = breakSchAmountOwed.get(index);
		
		return outAmountOwed;
	}
	
	public double getAfterSchAmountOwed(int childID) {
		int index;
		try {
			 index = getChildIndex(childID);
			}catch(IndexOutOfBoundsException ib) {
				index = -1;
			}
		
		double outAmountOwed = afterSchAmountOwed.get(index);
		
		return outAmountOwed;
	}
	
	
	/*
	 * GET AMOUNT PAID
	 */
	public double getPreSchAmountPaid(int childID) {
		
		int index;
		try {
			 index = getChildIndex(childID);
			}catch(IndexOutOfBoundsException ib) {
				index = -1;
			}
		
		double outAmountPaid = preschAmountPaid.get(index);

		
		return outAmountPaid;
	}
	
	public double getBreakSchAmountPaid(int childID) {
		
		int index;
		try {
			 index = getChildIndex(childID);
			}catch(IndexOutOfBoundsException ib) {
				index = -1;
			}
		
		double outAmountPaid = breakSchAmountPaid.get(index);

		
		return outAmountPaid;
	}
	
	
	public double getAfterSchAmountPaid(int childID) {
		
		int index;
		try {
			 index = getChildIndex(childID);
			}catch(IndexOutOfBoundsException ib) {
				index = -1;
			}
		
		double outAmountPaid = afterSchAmountPaid.get(index);

		
		return outAmountPaid;
	}
	
	
	
	public double getCurrBalance(double amountOwed, double amountPaid) {
		
		double balance = amountOwed - amountPaid;
		
		return balance;
	}
	
	//Find child in child list
	public int getChildIndex(int childID) {
		int foundIndex = -1;
		
		int count = 0;
		boolean search = false;
		
		
		do {
			if(childList.get(count).getID() == childID) {
				foundIndex = count;
				search = true;
			}
				
			count++;
		}while(count < childList.size() && search == false);
		
		return foundIndex;
	}

	
	
	public ArrayList<Child> getChildList(){
		return this.childList;
	}


}
