package controllers;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import classes.Balance;
import classes.Child;
import classes.Global;
import classes.Invoice;
import classes.Session;
import classes.Term;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class InvoiceController {

 @FXML
 private Button invoiceAdminBtn;

 @FXML
 private Button invoiceLogoutBtn;

 @FXML
 private ChoiceBox<String> attendanceTermDropdown;

 @FXML
 private DatePicker attendanceSessionDatePicker;

 @FXML
 private ChoiceBox<String> attendancePickupMinsDropdown;

 @FXML
 private ChoiceBox<String> attendanceDropoffMinsDropdown;

 @FXML
 private Button attendanceSubmitBtn;

 @FXML
 private ChoiceBox<String> attendanceSessionTypeDropdown;

 @FXML
 private ListView<String> attendanceChildList;

 @FXML
 private TextField attendanceChildFilterTxtField;

 @FXML
 private ListView<String> invoiceChildList;

 @FXML
 private TextField invoiceChildFilterTxtField;

 @FXML
 private TextArea invoiceSummaryTextView;

 @FXML
 private ChoiceBox<String> invoiceTermDropdown;

 @FXML
 private Button invoiceSendEmailBtn;
 
 @FXML
 private ListView<String> invoiceListViewA;

 @FXML
 private ListView<String> invoiceListViewB;
 
 @FXML
 private Label dateLabelA;

 @FXML
 private Label dateLabelB;
 
 @FXML
 private ChoiceBox<String> balanceTermDropdown;

 @FXML
 private ListView<String> balanceChildList;

 @FXML
 private TextField balanceChildFilterTxtField;

 @FXML
 private TextArea balanceSummaryTextView;

 @FXML
 private TextField balancePaymentTxtField;

 @FXML
 private Button balanceMakePaymentBtn;

 @FXML
 private ListView<String> paymentRecordsChildList;

 @FXML
 private TextField paymentRecordsChildFilterTxtField;

 @FXML
 private ListView<String> paymentRecordsPaymentHistoryListView;
 
 @FXML
 private RadioButton balanceBreakRdioBtn;

 @FXML
 private RadioButton balanceAfterRdioBtn;
 
 @FXML
 private ToggleGroup sessionRdioBtn;
 
 @FXML
 private Button saveCSVBtn;
 
 private Global getGlobal = new Global();
 
 private int childTermIndexPass, childSessionIndexPass;
 
 
 private Invoice schInvoice = new Invoice();
 
 
 
 //Sets up dropdowns in attendance
 public void setupAttendanceTerm(ChoiceBox<String> dropdownName) {
	 //Load term dropdown
 	ArrayList<Term> dropdownListTerms = getGlobal.getStaticTerm();


	ObservableList<String> termDropList = FXCollections.observableArrayList();

	for(int i = 0; i< dropdownListTerms.size(); i++) {
		termDropList.add(dropdownListTerms.get(i).getTermName());
	}
	
	dropdownName.getItems().clear();
	dropdownName.getItems().addAll(termDropList);
	 
	 //add change listener to term dropdown to setup session picker
	 
	 //setup session dropdown as PreSchool, Breakfast, AfterSchool
 }
 
 //Sets start date for date picker to term start
 public void setupSessionDatePicker() {
	 attendanceTermDropdown.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				

				if(arg2 == null) {
					System.out.println("Null?");
				}
				else {
					/*
					 * Find term startdate and set to date picker
					 */
					int i = 0;
					boolean checkfound = false;
					
					
		    		do {
		    			String selectedTermName = attendanceTermDropdown.getSelectionModel().getSelectedItem();
						String globalTerm = getGlobal.getStaticTerm().get(i).getTermName();
						
						//TermLabel.setText(selectedTermName);
		    			if(selectedTermName.equals(globalTerm)) {
		    				attendanceSessionDatePicker.setValue(getGlobal.getStaticTerm().get(i).getTermStartDate());
		    				checkfound = true;
		    			}
		    			
		    			i++;
		    			
		    		}while(i < getGlobal.getStaticTerm().size() && checkfound == false );
					
				}
				
				
			}
 	});
 }
 
 public void dropdownOptionList() {
	 
	 //Session Option Dropdown
	 ObservableList<String> sessionOptionList = FXCollections.observableArrayList();
	 
	 sessionOptionList.add("Pre-School");
	 sessionOptionList.add("Breakfast");
	 sessionOptionList.add("AfterSchool");
	 
	 attendanceSessionTypeDropdown.getItems().clear();
	 attendanceSessionTypeDropdown.getItems().addAll(sessionOptionList);
	 
	 
	 //Minutes Option Dropdown
	 ObservableList<String> minutesOptionList = FXCollections.observableArrayList();
	 for(int i = 0; i < 60; i+=5) {
		 minutesOptionList.add(String.valueOf(i));
	 }
	 
	 //Dropoff
	 attendanceDropoffMinsDropdown.getItems().clear();
	 attendanceDropoffMinsDropdown.getItems().addAll(minutesOptionList);
	 
	 //Pickup
	 attendancePickupMinsDropdown.getItems().clear();
	 attendancePickupMinsDropdown.getItems().addAll(minutesOptionList);
	 
	 //Set Minutes dropdowns to select zero as default
	 attendanceDropoffMinsDropdown.getSelectionModel().selectFirst();
	 attendancePickupMinsDropdown.getSelectionModel().selectFirst();
 }
 
 public void loadAttendanceChildList() {
	 attendanceSessionTypeDropdown.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				

				if(arg2 == null) {
					System.out.println("Null Session Option Selection?");
				}
				else {
					/*
					 * Find term startdate and set to date picker
					 */
					int i = 0;
					boolean checkfound = false;
					
					
		    		do {
		    			String selectedTermName = attendanceTermDropdown.getSelectionModel().getSelectedItem();
						String globalTerm = getGlobal.getStaticTerm().get(i).getTermName();
						
						
						//TermLabel.setText(selectedTermName);
		    			if(selectedTermName.equals(globalTerm)) {
		    				childTermIndexPass = i;
		    				Term foundterm = getGlobal.getStaticTerm().get(i);
		    				checkfound = true;
							
							
							int x = 0;
							boolean searchSession = false;
							do {
								if(attendanceSessionDatePicker.getValue().equals(foundterm.getSessions().get(x).getSessionDate())) {
									childSessionIndexPass = x;
									Session session = foundterm.getSessions().get(x);
									ArrayList<Child> preSchoolChildList = session.getPreSchSession();
									ArrayList<Child> breakfastChildList = session.getBreakfastSchSession();
									ArrayList<Child> afterSchoolChildList = session.getAfterSchSession();
									
									
									ObservableList<String> sessionBookedChildren = FXCollections.observableArrayList();
									
									
									
									if(attendanceSessionTypeDropdown.getSelectionModel().getSelectedItem().equals("Pre-School")){
										sessionBookedChildren.clear();
										
										for(int a = 0; a< preSchoolChildList.size(); a++) {
											sessionBookedChildren.add(preSchoolChildList.get(a).getID()+", "+preSchoolChildList.get(a).getFname()+" "+preSchoolChildList.get(a).getSname());
							    		}
										
									}
									else if(attendanceSessionTypeDropdown.getSelectionModel().getSelectedItem().equals("Breakfast")){
										sessionBookedChildren.clear();
										
										for(int a = 0; a< breakfastChildList.size(); a++) {
											sessionBookedChildren.add(breakfastChildList.get(a).getID()+", "+breakfastChildList.get(a).getFname()+" "+breakfastChildList.get(a).getSname());
							    		}
										
									}
									else if(attendanceSessionTypeDropdown.getSelectionModel().getSelectedItem().equals("AfterSchool")){
										sessionBookedChildren.clear();
										
										for(int a = 0; a< afterSchoolChildList.size(); a++) {
											sessionBookedChildren.add(afterSchoolChildList.get(a).getID()+", "+afterSchoolChildList.get(a).getFname()+" "+afterSchoolChildList.get(a).getSname());
							    		}
										
									}
									
									
									
									//Filterlist added to the observable list
						        	FilteredList<String> filteredData = new FilteredList<>(sessionBookedChildren, s -> true);
						        	
						        	//Listener added to the textfield to search up text input
						        	attendanceChildFilterTxtField.textProperty().addListener(obs->{
						                String filter = attendanceChildFilterTxtField.getText();
						                
						                //If textfield is blank show all (s stands for string using lamba?)
						                if(filter == null || filter.length() == 0) {
						                    filteredData.setPredicate(s -> true);
						                }
						                //Else check in input(lowercase) matches any text in the list(lowerxcase)
						                else {
						                    filteredData.setPredicate(s -> s.toLowerCase().contains(filter.toLowerCase()));
						                }
						            });
						        	
						        	
						        	//Sort the filtered info
						        	SortedList<String> sortedData = new SortedList<>(filteredData);

						        	//Set to listview for display
						        	attendanceChildList.setItems(filteredData);
									
								}
								x++;
							}while(x < foundterm.getSessions().size() && searchSession == false);
		    				
		    				
		    				
		    				
		    				
		    			}
		    			
		    			i++;
		    			
		    		}while(i < getGlobal.getStaticTerm().size() && checkfound == false );
					
				}
				
				
			}
	});
 }
 
 public void clearListViewSessionDateChange() {
	 attendanceChildList.getItems().clear();
 }
 
 public void attendanceSubmit() {
	 /*
	 	Get child index from listview
	 		Info already in this file with the writing to listview in the first place
	 	use index to put the corresponding dropoff and pickup times in the right arrays
	 	Popup before submit for confirmation
	 	Write to file to overwrite term
	 
	 */
	 //System.out.println("Term Index: "+childTermIndexPass);
	 Term foundterm = getGlobal.getStaticTerm().get(childTermIndexPass);
	 
	 //System.out.println("Session Index: "+childSessionIndexPass);
	 Session session = foundterm.getSessions().get(childSessionIndexPass);
	 
	 ArrayList<Child> preSchoolChildList = session.getPreSchSession();
	 ArrayList<Child> breakfastChildList = session.getBreakfastSchSession();
	 ArrayList<Child> afterSchoolChildList = session.getAfterSchSession();
	 
	 
	try {
		
		//Get info from listview, split ID from rest of string and use to search global to find child
	 	String selectedListChild = attendanceChildList.getSelectionModel().getSelectedItem();
	 	String[] splitSelected = selectedListChild.split(",");
	 	int bookingChildID = Integer.parseInt(splitSelected[0]);
	
	 	
		int dropOffMins = Integer.parseInt(attendanceDropoffMinsDropdown.getSelectionModel().getSelectedItem());
		int pickUpMins = Integer.parseInt(attendancePickupMinsDropdown.getSelectionModel().getSelectedItem());
		
	 	//Selected list from dropdowns
		
		String confirmText = "Confirm Attendance for "+splitSelected[1]+"\nDropoff: "+dropOffMins+"mins \nPickup: "+pickUpMins+"mins";
		
    	Alert alertAttendance = new Alert(AlertType.WARNING);
    	alertAttendance.setTitle("Attendance");
    	alertAttendance.setContentText(confirmText);


    	ButtonType buttonTypeYes = new ButtonType("Yes");
    	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    	alertAttendance.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
    	
    	Optional<ButtonType> resultAttendance = alertAttendance.showAndWait();
    	if (resultAttendance.get() == buttonTypeYes){
    		if(attendanceSessionTypeDropdown.getSelectionModel().getSelectedItem().equals("Pre-School")){
				int count = 0;
				boolean searching = false;
				
				do {
					if(bookingChildID == preSchoolChildList.get(count).getID()) {
						//System.out.println("Found: "+preSchoolChildList.get(count).getFname()+" Index is :"+count);

						//getGlobal.getStaticTerm().get(childTermIndexPass).getSessions().get(childSessionIndexPass).getPreSchDropoffMinutesLate()[count] = dropOffMins;
						getGlobal.getStaticTerm().get(childTermIndexPass).getSessions().get(childSessionIndexPass).setPreSchDropoffMinutesLate(count, dropOffMins);
						
						//getGlobal.getStaticTerm().get(childTermIndexPass).getSessions().get(childSessionIndexPass).getPreSchPickupMinutesLate()[count] = pickUpMins;
						getGlobal.getStaticTerm().get(childTermIndexPass).getSessions().get(childSessionIndexPass).setPreSchPickupMinutesLate(count, pickUpMins);
						
						
						searching = true;
					}
					count++;
				}while(count <preSchoolChildList.size() && searching == false);
	 		
				
			}
			 else if(attendanceSessionTypeDropdown.getSelectionModel().getSelectedItem().equals("Breakfast")){
				 int count = 0;
					boolean searching = false;
					
					do {
						if(bookingChildID == breakfastChildList.get(count).getID()) {
							//System.out.println("Found: "+breakfastChildList.get(count).getFname()+" Index is :"+count);
							
							getGlobal.getStaticTerm().get(childTermIndexPass).getSessions().get(childSessionIndexPass).getBreakfastDropoffMinutesLate()[count] = dropOffMins;
							getGlobal.getStaticTerm().get(childTermIndexPass).getSessions().get(childSessionIndexPass).getBreakfastPickupMinutesLate()[count] = pickUpMins;
							
							searching = true;
						}
						count++;
					}while(count <breakfastChildList.size() && searching == false);
			
				
			 }
			 else if(attendanceSessionTypeDropdown.getSelectionModel().getSelectedItem().equals("AfterSchool")){
				 int count = 0;
					boolean searching = false;
					
					do {
						if(bookingChildID == afterSchoolChildList.get(count).getID()) {
							//System.out.println("Found: "+afterSchoolChildList.get(count).getFname()+" Index is :"+count);
							
							getGlobal.getStaticTerm().get(childTermIndexPass).getSessions().get(childSessionIndexPass).getAfterSchoolDropoffMinutesLate()[count] = dropOffMins;
							getGlobal.getStaticTerm().get(childTermIndexPass).getSessions().get(childSessionIndexPass).getAfterSchoolPickupMinutesLate()[count] = pickUpMins;
							
							searching = true;
						}
						count++;
					}while(count <afterSchoolChildList.size() && searching == false);
				 
			 }
    		
    		
    		
			//Write to file
			try {
				//written to file
        		FileOutputStream outStream = new FileOutputStream("Terms.dat");
        		ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
        		
        		//System.out.println("File overwritten");
        		
        		//Cycles through the existing terms in the arraylist and writes them to file
        		for (int n = 0; n < getGlobal.getStaticTerm().size(); n++)
      	      	{
        			objectOutputFile.writeObject(getGlobal.getStaticTerm().get(n));
      					
      	      	}
        		
        		//System.out.println("Test: "+getGlobal.getStaticTerm().get(0).getTermName());
        		
        		objectOutputFile.close();
        		outStream.close();
        		
        		//populateTermDropdowns();
			}catch(Exception e) {
				//e.printStackTrace();
			}
    	}
		 
		 
		 
		 
		 //Write to Term file
	}catch(NullPointerException np) {
		np.printStackTrace();
		}	

 }
 
 
 public void loadChildList(TextField searchFilter, ListView<String> listview) {
 	try {
 	//Observable list to hold all the current children in the system
 	ObservableList<String> foundChildren = FXCollections.observableArrayList();
 	ArrayList<Child> childList = new ArrayList<Child>();
 	
 	
 	childList = getGlobal.getStaticChild();
 	for(int i = 0; i< childList.size(); i++) {
 		foundChildren.add(childList.get(i).getID()+", "+childList.get(i).getFname()+" "+childList.get(i).getSname());
		}
 	
 	//Filterlist added to the observable list
 	FilteredList<String> filteredData = new FilteredList<>(foundChildren, s -> true);
 	
 	//Listener added to the textfield to search up text input
 	searchFilter.textProperty().addListener(obs->{
         String filter = searchFilter.getText();
         
         //If textfield is blank show all (s stands for string using lamba?)
         if(filter == null || filter.length() == 0) {
             filteredData.setPredicate(s -> true);
         }
         //Else check in input(lowercase) matches any text in the list(lowerxcase)
         else {
             filteredData.setPredicate(s -> s.toLowerCase().contains(filter.toLowerCase()));
         }
     });
 	
 	
 	//Sort the filtered info
 	SortedList<String> sortedData = new SortedList<>(filteredData);

 	//Set to listview for display
 	listview.setItems(sortedData);
 	}catch(Exception e){
 		System.out.println("No one found. Error in search. Probably non existent global set");
 	}
 }
 
 
 public void displayInvoice() {
	try {
		String selectedListOption = invoiceChildList.getSelectionModel().getSelectedItem();
		String[] splitSelected = selectedListOption.split(",");
		String termNameDropdown = invoiceTermDropdown.getSelectionModel().getSelectedItem();
		
		//ArrayList<Child> globalChildList = new ArrayList<Child>();
		
		ArrayList<String> childPreSchBookedSession = new ArrayList<String>();
		ArrayList<Integer> childPreSchSessionAttendancePickup = new ArrayList<Integer>();
		ArrayList<Integer> childPreSchSessionAttendanceDropoff = new ArrayList<Integer>();
		
		ArrayList<String> childBreakfastBookedSession = new ArrayList<String>();
		ArrayList<Integer> childBreakfastSessionAttendancePickup = new ArrayList<Integer>();
		ArrayList<Integer> childBreakfastSessionAttendanceDropoff = new ArrayList<Integer>();
		
		ArrayList<String> childAfterSchoolBookedSession = new ArrayList<String>();
		ArrayList<Integer> childAfterSchoolSessionAttendancePickup = new ArrayList<Integer>();
		ArrayList<Integer> childAfterSchoolSessionAttendanceDropoff = new ArrayList<Integer>();
		
		LocalDate termStartDate = null;
	 	//ID is at 0
	 	//Search global with that to find child
	 	boolean found = false;
	 	int i = 0;
	 	Child foundChild = null;
	 	
	 	do {
	 		if(getGlobal.getStaticChild().get(i).getID() == Integer.parseInt(splitSelected[0])) {
	 			foundChild = getGlobal.getStaticChild().get(i);
	 			found = true;
	 		}
	
	 		i++;
	
	 		
	 	}while(i < getGlobal.getStaticChild().size() && found == false);
		
	 	
	 	//Get child's status (Preschool or School, Grant && Older Sibling)
	 	boolean preschoolStatus = foundChild.getPreschool();
	 	boolean grantStatus = foundChild.getGrant();
	 	boolean olderSiblingStatus = foundChild.getSiblingCheck();
	 	
	 	
	 	
	 	
	 	//Search through term
	 	int termCount = 0;
	 	boolean termSearch = false;
	 	do{
	 		if(getGlobal.getStaticTerm().get(termCount).getTermName().equals(termNameDropdown)) {
	 			termStartDate = getGlobal.getStaticTerm().get(termCount).getTermStartDate();
	 			ArrayList<Session> allTermSessions = getGlobal.getStaticTerm().get(termCount).getSessions();
	 			//Search through sessions
	 			int sessionCount = 0;
	 			boolean sessSearch = false;
	 			do {
	 				//search preschool sessions
	 				if(preschoolStatus == true) {
	 					int preschCount = 0;
	 					boolean preschSearch = false;
	 					try {
	 					do {
	 						
	 						if(allTermSessions.get(sessionCount).getPreSchSession().get(preschCount).getID() == Integer.parseInt(splitSelected[0])) {
	 							//Add to local variable for easy access
	 							childPreSchBookedSession.add(String.valueOf(allTermSessions.get(sessionCount).getSessionDate()));
	 							childPreSchSessionAttendancePickup.add(allTermSessions.get(sessionCount).getPreSchPickupMinutesLate()[preschCount]);
	 							childPreSchSessionAttendanceDropoff.add(allTermSessions.get(sessionCount).getPreSchDropoffMinutesLate()[preschCount]);
	 							preschSearch = true;
	 						}
	 						preschCount++;
	 					}while(preschCount < allTermSessions.get(sessionCount).getPreSchSession().size() && preschSearch == false);
	 					}catch(IndexOutOfBoundsException ie) {
	 						
	 					}
	 					
	 				}
	 				//search school kids
	 				else {
	 					int schCount = 0;
	 					boolean schSearch = false;
	 					
	 					try {
		 					
		 					//Breakfast Session
		 					do {
		 						if(allTermSessions.get(sessionCount).getBreakfastSchSession().get(schCount).getID() == Integer.parseInt(splitSelected[0])) {
		 							//Add to local variable for easy access
		 							childBreakfastBookedSession.add(String.valueOf(allTermSessions.get(sessionCount).getSessionDate()));
		 							childBreakfastSessionAttendancePickup.add(allTermSessions.get(sessionCount).getBreakfastPickupMinutesLate()[schCount]);
		 							childBreakfastSessionAttendanceDropoff.add(allTermSessions.get(sessionCount).getBreakfastDropoffMinutesLate()[schCount]);
		 							schSearch = true;
		 						}
		 						schCount++;
		 					}while(schCount < allTermSessions.get(sessionCount).getBreakfastSchSession().size() && schSearch == false);
		 					
		 					
		 					//Afterschool Session
		 					schCount = 0;
		 					schSearch = false;
		 					
		 					do {
		 						if(allTermSessions.get(sessionCount).getAfterSchSession().get(schCount).getID() == Integer.parseInt(splitSelected[0])) {
		 							//Add to local variable for easy access
		 							childAfterSchoolBookedSession.add(String.valueOf(allTermSessions.get(sessionCount).getSessionDate()));
		 							childAfterSchoolSessionAttendancePickup.add(allTermSessions.get(sessionCount).getAfterSchoolPickupMinutesLate()[schCount]);
		 							childAfterSchoolSessionAttendanceDropoff.add(allTermSessions.get(sessionCount).getAfterSchoolDropoffMinutesLate()[schCount]);
		 							schSearch = true;
		 						}
		 						schCount++;
		 					}while(schCount < allTermSessions.get(sessionCount).getAfterSchSession().size() && schSearch == false);
	 					
	 					}catch(IndexOutOfBoundsException ie) {
	 						
	 					}
	 				}
	 				
	 				sessionCount++;
	 			}while(sessionCount < allTermSessions.size() && sessSearch == false);
	 			
	 			
	 			termSearch = true;
	 		}
	 		
	 		termCount++;
	 	}while(termCount < getGlobal.getStaticTerm().size() && termSearch == false);
	 	
	 	
	 	/*
	 	 * Display
	 	 */
	 	//PRESCHOOL DISPLAY
	 	if(foundChild.getPreschool() == true) {
	 		/*
	 		System.out.println("PRESCHOOL");
	 		System.out.println("Session Dates");
	 		System.out.println(childPreSchBookedSession.toString());
	 		System.out.println("Preschool Price: £1.75");
	 		System.out.println("Number of Sessions : £1.75x"+childPreSchBookedSession.size());
	 		System.out.println("SubTotal: "+(childPreSchBookedSession.size()*1.75));
	 		
	 		System.out.println("Minutes Pickup");
	 		System.out.println(childPreSchSessionAttendancePickup.toString());
	 		
	 		
	 		System.out.println("Minutes Dropoff");
	 		System.out.println(childPreSchSessionAttendanceDropoff.toString());
	 		*/

	 		ObservableList<String> preSchDates = FXCollections.observableArrayList();
	 		
	 		for(int preschDates = 0; preschDates < childPreSchBookedSession.size(); preschDates++) {
	 			preSchDates.add(childPreSchBookedSession.get(preschDates));
	 		}
	 		
	 		
	 		dateLabelA.setText("");
	 		dateLabelB.setText("");
	 		invoiceListViewA.getItems().clear();
	 		invoiceListViewB.getItems().clear();
	 		
	 		dateLabelA.setText("PreSchool Booked Dates");
	 		invoiceListViewA.setItems(preSchDates);
	 		
	 		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
	 		
	 		try {
	 			Invoice preschInvoice = new Invoice();
		 		String disp = preschInvoice.preschSummary(childPreSchBookedSession, childPreSchSessionAttendanceDropoff, childPreSchSessionAttendancePickup, 
		 				foundChild.getSiblingCheck(), foundChild.getGrant())+ "\nDue : "+dateFormatter.format(termStartDate.plusWeeks(2));
 				invoiceSummaryTextView.setText(disp);
	 		}catch(NullPointerException npDate) {
	 			System.out.println("Issue with the invoice due date probably- reading null");
	 		}
	 	}
	 	else {
	 		//SCHOOL DISPLAY
	 		/*
	 		System.out.println("SCHOOL");
	 		System.out.println("Session Dates");
	 		System.out.println("Breakfast Session");
	 		System.out.println(childBreakfastBookedSession.toString());
	 		
	 		System.out.println("Minutes Dropoff");
	 		System.out.println(childBreakfastSessionAttendanceDropoff.toString());
	 		
	 		System.out.println("Minutes Pickup");
	 		System.out.println(childBreakfastSessionAttendancePickup.toString());
	 		System.out.println();
	 		
	 		
	 		System.out.println("Session Dates");
	 		System.out.println("After School Session");
	 		System.out.println(childAfterSchoolBookedSession.toString());
	 		
	 		System.out.println("Minutes Dropoff");
	 		System.out.println(childAfterSchoolSessionAttendanceDropoff.toString());
	 		
	 		System.out.println("Minutes Pickup");
	 		System.out.println(childAfterSchoolSessionAttendancePickup.toString());
			*/
	 		ObservableList<String> breakSchDates = FXCollections.observableArrayList();
	 		ObservableList<String> afterSchDates = FXCollections.observableArrayList();
	 		
	 		for(int breakSessDates = 0; breakSessDates < childBreakfastBookedSession.size(); breakSessDates++) {
	 			breakSchDates.add(childBreakfastBookedSession.get(breakSessDates));
	 		}
	 		
	 		for(int afterSessDates = 0; afterSessDates < childAfterSchoolBookedSession.size(); afterSessDates++) {
	 			afterSchDates.add(childAfterSchoolBookedSession.get(afterSessDates));
	 		}
	 		
	 		
	 		String brekDisp = schInvoice.schBreakSummary(childBreakfastBookedSession, childBreakfastSessionAttendanceDropoff, childBreakfastSessionAttendancePickup);
	 		String afterschDisp = schInvoice.schAfterSchSummary(childAfterSchoolBookedSession, childAfterSchoolSessionAttendanceDropoff, childAfterSchoolSessionAttendancePickup);
	 		
	 		dateLabelA.setText("");
	 		dateLabelB.setText("");
	 		invoiceListViewA.getItems().clear();
	 		invoiceListViewB.getItems().clear();
	 		
	 		dateLabelA.setText("Breakfast Booked Dates");
	 		invoiceListViewA.setItems(breakSchDates);
	 		
	 		dateLabelB.setText("AfterSchool Booked Dates");
	 		invoiceListViewB.setItems(afterSchDates);
	 		
	 		
	 		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
	 		
			invoiceSummaryTextView.setText(brekDisp+ "\nDue : "+dateFormatter.format(termStartDate.plusWeeks(2))+
					"\n-----------------------------------------------\n"+afterschDisp+ "\nDue : "+dateFormatter.format(termStartDate.plusWeeks(2)));
	 	}
	}catch(NullPointerException np) {
		//np.printStackTrace();
	}
	
	

 }
 
 //For childlist balance tab
 public void loadChildBalance() {
	 try {
			String selectedListOption = balanceChildList.getSelectionModel().getSelectedItem();
			String[] splitSelected = selectedListOption.split(",");
			String termNameDropdown = balanceTermDropdown.getSelectionModel().getSelectedItem();
			
			//ArrayList<Child> globalChildList = new ArrayList<Child>();
			
			ArrayList<String> childPreSchBookedSession = new ArrayList<String>();
			ArrayList<Integer> childPreSchSessionAttendanceDropoff = new ArrayList<Integer>();
			ArrayList<Integer> childPreSchSessionAttendancePickup = new ArrayList<Integer>();

			
			ArrayList<String> childBreakfastBookedSession = new ArrayList<String>();
			ArrayList<Integer> childBreakfastSessionAttendanceDropoff = new ArrayList<Integer>();
			ArrayList<Integer> childBreakfastSessionAttendancePickup = new ArrayList<Integer>();

			
			ArrayList<String> childAfterSchoolBookedSession = new ArrayList<String>();
			ArrayList<Integer> childAfterSchoolSessionAttendanceDropoff = new ArrayList<Integer>();
			ArrayList<Integer> childAfterSchoolSessionAttendancePickup = new ArrayList<Integer>();
			
			
			
		 	//ID is at 0
		 	//Search global with that to find child
		 	boolean found = false;
		 	int i = 0;
		 	Child foundChild = null;
		 	
		 	do {
		 		if(getGlobal.getStaticChild().get(i).getID() == Integer.parseInt(splitSelected[0])) {
		 			foundChild = getGlobal.getStaticChild().get(i);
		 			found = true;
		 		}
		
		 		i++;
		
		 		
		 	}while(i < getGlobal.getStaticChild().size() && found == false);
			
		 	
		 	//Get child's status (Preschool or School, Grant && Older Sibling)
		 	boolean preschoolStatus = foundChild.getPreschool();
		 	boolean grantStatus = foundChild.getGrant();
		 	boolean olderSiblingStatus = foundChild.getSiblingCheck();
		 	
		 	
		 	
		 	
		 	//Search through term
		 	int termCount = 0;
		 	boolean termSearch = false;
		 	do{
		 		if(getGlobal.getStaticTerm().get(termCount).getTermName().equals(termNameDropdown)) {
		 			LocalDate termStartDate = getGlobal.getStaticTerm().get(termCount).getTermStartDate();
		 			ArrayList<Session> allTermSessions = getGlobal.getStaticTerm().get(termCount).getSessions();
		 			//Search through sessions
		 			int sessionCount = 0;
		 			boolean sessSearch = false;
		 			do {
		 				//search preschool sessions
		 				if(preschoolStatus == true) {
		 					int preschCount = 0;
		 					boolean preschSearch = false;
		 					try {
		 					do {
		 						
		 						if(allTermSessions.get(sessionCount).getPreSchSession().get(preschCount).getID() == Integer.parseInt(splitSelected[0])) {
		 							//Add to local variable for easy access
		 							childPreSchBookedSession.add(String.valueOf(allTermSessions.get(sessionCount).getSessionDate()));
		 							childPreSchSessionAttendancePickup.add(allTermSessions.get(sessionCount).getPreSchPickupMinutesLate()[preschCount]);
		 							childPreSchSessionAttendanceDropoff.add(allTermSessions.get(sessionCount).getPreSchDropoffMinutesLate()[preschCount]);
		 							preschSearch = true;
		 						}
		 						preschCount++;
		 					}while(preschCount < allTermSessions.get(sessionCount).getPreSchSession().size() && preschSearch == false);
		 					}catch(IndexOutOfBoundsException ie) {
		 						
		 					}
		 					
		 				}
		 				//search school kids
		 				else {
		 					int schCount = 0;
		 					boolean schSearch = false;
		 					
		 					try {
			 					
			 					//Breakfast Session
			 					do {
			 						if(allTermSessions.get(sessionCount).getBreakfastSchSession().get(schCount).getID() == Integer.parseInt(splitSelected[0])) {
			 							//Add to local variable for easy access
			 							childBreakfastBookedSession.add(String.valueOf(allTermSessions.get(sessionCount).getSessionDate()));
			 							childBreakfastSessionAttendancePickup.add(allTermSessions.get(sessionCount).getBreakfastPickupMinutesLate()[schCount]);
			 							childBreakfastSessionAttendanceDropoff.add(allTermSessions.get(sessionCount).getBreakfastDropoffMinutesLate()[schCount]);
			 							schSearch = true;
			 						}
			 						schCount++;
			 					}while(schCount < allTermSessions.get(sessionCount).getBreakfastSchSession().size() && schSearch == false);
			 					
			 					
			 					//Afterschool Session
			 					schCount = 0;
			 					schSearch = false;
			 					
			 					do {
			 						if(allTermSessions.get(sessionCount).getAfterSchSession().get(schCount).getID() == Integer.parseInt(splitSelected[0])) {
			 							//Add to local variable for easy access
			 							childAfterSchoolBookedSession.add(String.valueOf(allTermSessions.get(sessionCount).getSessionDate()));
			 							childAfterSchoolSessionAttendancePickup.add(allTermSessions.get(sessionCount).getAfterSchoolPickupMinutesLate()[schCount]);
			 							childAfterSchoolSessionAttendanceDropoff.add(allTermSessions.get(sessionCount).getAfterSchoolDropoffMinutesLate()[schCount]);
			 							schSearch = true;
			 						}
			 						schCount++;
			 					}while(schCount < allTermSessions.get(sessionCount).getAfterSchSession().size() && schSearch == false);
		 					
		 					}catch(IndexOutOfBoundsException ie) {
		 						
		 					}
		 				}
		 				
		 				sessionCount++;
		 			}while(sessionCount < allTermSessions.size() && sessSearch == false);
		 			
		 			
		 			try {
					 	//Balance Calc for Amount Owed
					 	if(foundChild.getPreschool() == true) {
					 		
					 		balanceBreakRdioBtn.setDisable(true);
					 		balanceAfterRdioBtn.setDisable(true);
					 		double preSchFinalTotalOwed = schInvoice.simplePreschBalance(childPreSchBookedSession, childPreSchSessionAttendanceDropoff, childPreSchSessionAttendancePickup, 
					 				foundChild.getSiblingCheck(), foundChild.getGrant());
					 		
					 		/*
					 		System.out.println("Child booked Session Size: "+childPreSchBookedSession.size());
					 		System.out.println("Child Attendance Dropoff: "+childPreSchSessionAttendanceDropoff.size());
					 		System.out.println("Child Attendance Pickup: "+childPreSchSessionAttendancePickup.size());
					 		System.out.println("Calculated Preschool: Owed: "+preSchFinalTotalOwed);
					 		
					 		System.out.println("Term Count: "+termCount);
					 		System.out.println("Balance Child List Size: "+(getGlobal.getStaticBalance().get(termCount).getChildList().size()));
					 		System.out.println("Found child ID: "+foundChild.getID());
					 		//balanceSummaryTextView.setText("Pre School Display");
					 		*/
					 		System.out.println("Calculated Preschool: Owed: "+preSchFinalTotalOwed);
					 		getGlobal.getStaticBalance().get(termCount).clearAll();
					 		boolean setPreSchOwed = getGlobal.getStaticBalance().get(termCount).setPreschAmountOwed(foundChild.getID(), preSchFinalTotalOwed);
					 		
					 		System.out.println("Preschool amount set: "+setPreSchOwed);
					 		
					 		
					 		String preschDisp = displayBalance("PRE-SCHOOL SESSION",foundChild, getGlobal.getStaticBalance().get(termCount).getPreSchAmountOwed(foundChild.getID()), 
					 				getGlobal.getStaticBalance().get(termCount).getPreSchAmountPaid(foundChild.getID()), termStartDate);
					 	
					 		balanceSummaryTextView.setText(preschDisp);
					 		
					 		
					 	}
					 	else {
					 		balanceBreakRdioBtn.setDisable(false);
					 		balanceAfterRdioBtn.setDisable(false);
					 		
					 		double breakSchFinalTotalOwed = schInvoice.simpleBrekSchBalance(childBreakfastBookedSession, childBreakfastSessionAttendanceDropoff, childBreakfastSessionAttendancePickup);
				 			
				 			double afterSchFinalTotalOwed = schInvoice.simpleAfterSchBalance(childAfterSchoolBookedSession, childAfterSchoolSessionAttendanceDropoff, childAfterSchoolSessionAttendancePickup);
				 			
				 			/*
				 			System.out.println("Breakfast Session Size: "+childBreakfastBookedSession.size());
				 			System.out.println("Breakfast dropoff Size: "+childBreakfastSessionAttendanceDropoff.size());
				 			System.out.println("Breakfast pickup Size: "+childBreakfastSessionAttendancePickup.size());
				 			System.out.println("Calculated Breakfast Owed: "+breakSchFinalTotalOwed);
				 			
				 			System.out.println();
				 			System.out.println("Afterschool Session Size: "+childAfterSchoolBookedSession.size());
				 			System.out.println("Afterschool dropoff Size: "+childAfterSchoolSessionAttendanceDropoff.size());
				 			System.out.println("Afterschool pickup Size: "+childAfterSchoolSessionAttendancePickup.size());
				 			System.out.println("Calculated AfterSchool Owed: "+afterSchFinalTotalOwed);
				 			System.out.println("Term Count: "+termCount);
				 			*/
				 			
				 			System.out.println("Calculated Breakfast Owed: "+breakSchFinalTotalOwed);
				 			System.out.println("Calculated AfterSchool Owed: "+afterSchFinalTotalOwed);
				 			getGlobal.getStaticBalance().get(termCount).clearAll();
				 			getGlobal.getStaticBalance().get(termCount).setBreakSchAmountOwed(foundChild.getID(), breakSchFinalTotalOwed);
				 			getGlobal.getStaticBalance().get(termCount).setAfterSchAmountOwed(foundChild.getID(), afterSchFinalTotalOwed);
				 			
				 			
				 			String breakDisp = displayBalance("BREAKFAST SESSION", foundChild, getGlobal.getStaticBalance().get(termCount).getBreakSchAmountOwed(foundChild.getID()),
				 					getGlobal.getStaticBalance().get(termCount).getBreakSchAmountPaid(foundChild.getID()),termStartDate);
				 			
				 			String afterSchDisp = displayBalance("AFTERSCHOOL SESSION", foundChild, getGlobal.getStaticBalance().get(termCount).getAfterSchAmountOwed(foundChild.getID()),
					 				getGlobal.getStaticBalance().get(termCount).getAfterSchAmountPaid(foundChild.getID()),termStartDate);
				 			
				 			
				 			balanceSummaryTextView.setText(breakDisp+"\n-----------------------------\n"+afterSchDisp);
					 	}
		 			}catch(IndexOutOfBoundsException ibe) {
		 				ibe.printStackTrace();
		 			}
		 			
		 			
		 			
				 	//Write to file
					try {
	            		FileOutputStream outStream = new FileOutputStream("Terms.dat");
	            		ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
	            		
	            		System.out.println("File overwritten");
	            		
	            		//Cycles through the existing terms in the arraylist and writes them to file
	            		for (int n = 0; n < getGlobal.getStaticTerm().size(); n++)
	          	      	{
	            			objectOutputFile.writeObject(getGlobal.getStaticTerm().get(n));
	          					
	          	      	}
	            		objectOutputFile.close();
	            		outStream.close();
	            		
	            		//populateTermDropdowns();
					}catch(Exception e) {
						//e.printStackTrace();
					}
				 	
				 	
		 			termSearch = true;
		 			
		 		}
		 		
		 		termCount++;
		 	}while(termCount < getGlobal.getStaticTerm().size() && termSearch == false);


	 }catch(NullPointerException np) {
		 
	 }
 }
 
 //For payment button
 public void balancePayment() {
	 try {
			String selectedListOption = balanceChildList.getSelectionModel().getSelectedItem();
			String[] splitSelected = selectedListOption.split(",");
			String termNameDropdown = balanceTermDropdown.getSelectionModel().getSelectedItem();
			double inPayment = 0;
			try {
				inPayment = Double.parseDouble(balancePaymentTxtField.getText());
			}catch(NumberFormatException nf) {
				
			}
			
			//ID is at 0
		 	//Search global with that to find child
		 	boolean found = false;
		 	int i = 0;
		 	Child foundChild = null;
		 	
		 	do {
		 		if(getGlobal.getStaticChild().get(i).getID() == Integer.parseInt(splitSelected[0])) {
		 			foundChild = getGlobal.getStaticChild().get(i);
		 			found = true;
		 		}
		
		 		i++;
		
		 		
		 	}while(i < getGlobal.getStaticChild().size() && found == false);
			
			
			
			//Search through term
		 	int termCount = 0;
		 	boolean termSearch = false;
		 	do{
		 		if(getGlobal.getStaticTerm().get(termCount).getTermName().equals(termNameDropdown)) {
		 			LocalDate termStartDate = getGlobal.getStaticTerm().get(termCount).getTermStartDate();
		 			
		 			if(foundChild.getPreschool() == true) {
				 		
				 		getGlobal.getStaticBalance().get(termCount).setPreSchAmountPaid(foundChild.getID(), inPayment);
				 		
				 		String preschDisp = displayBalance("PRE-SCHOOL SESSION",foundChild, getGlobal.getStaticBalance().get(termCount).getPreSchAmountOwed(foundChild.getID()), 
				 				getGlobal.getStaticBalance().get(termCount).getPreSchAmountPaid(foundChild.getID()),termStartDate);
				 		
				 		balanceSummaryTextView.setText(preschDisp);
				 		
				 	}
		 			else {
		 				
		 				if(balanceBreakRdioBtn.isSelected()) {
				 		
			 			getGlobal.getStaticBalance().get(termCount).setBreakSchAmountPaid(foundChild.getID(), inPayment);
			 			}
		 				
		 				else if(balanceAfterRdioBtn.isSelected()) {
				 		
				 		getGlobal.getStaticBalance().get(termCount).setAfterSchAmountPaid(foundChild.getID(), inPayment);
					 	}
		 				
			 			String breakDisp = displayBalance("BREAKFAST SESSION", foundChild, getGlobal.getStaticBalance().get(termCount).getBreakSchAmountOwed(foundChild.getID()),
			 					getGlobal.getStaticBalance().get(termCount).getBreakSchAmountPaid(foundChild.getID()),termStartDate);
			 			
				 		String afterSchDisp = displayBalance("AFTERSCHOOL SESSION", foundChild, getGlobal.getStaticBalance().get(termCount).getAfterSchAmountOwed(foundChild.getID()),
				 				getGlobal.getStaticBalance().get(termCount).getAfterSchAmountPaid(foundChild.getID()),termStartDate);
				 		
				 		balanceSummaryTextView.setText(breakDisp+"\n-----------------------------\n"+afterSchDisp);
		 				
		 			}
				 	
		 			
				 	//Write to file
					try {
	            		FileOutputStream outStream = new FileOutputStream("Terms.dat");
	            		ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
	            		
	            		System.out.println("File overwritten");
	            		
	            		//Cycles through the existing terms in the arraylist and writes them to file
	            		for (int n = 0; n < getGlobal.getStaticTerm().size(); n++)
	          	      	{
	            			objectOutputFile.writeObject(getGlobal.getStaticTerm().get(n));
	          					
	          	      	}
	            		objectOutputFile.close();
	            		outStream.close();
	            		
	            		//populateTermDropdowns();
					}catch(Exception e) {
						//e.printStackTrace();
					}
		 			
		 			termSearch = true;
		 			
		 		}
		 		
		 		termCount++;
		 	}while(termCount < getGlobal.getStaticTerm().size() && termSearch == false);


		 	
	 }catch(NullPointerException np) {
		 
	 }
 }
 
 
 public String displayBalance(String label, Child foundChild, double amountOwed, double amountPaid, LocalDate termStart) {
	 double balance = amountOwed - amountPaid;
	 DecimalFormat formatter = new DecimalFormat("0.00");
	 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
	 
	 String disp = label+"\n"+foundChild.getID()+", "+foundChild.getSname()+", "+foundChild.getFname()+
			 "\nDate: "+dateFormatter.format(termStart)+
			 "\nAmount Owed: £"+formatter.format(amountOwed)+"\nAmount Paid: £"+formatter.format(amountPaid)+
			 "\nBalance: £"+formatter.format(balance)+"\nDue: "+dateFormatter.format(termStart.plusWeeks(2));
	 
	 
	 return disp;
 }
 
 
 
 public void paymentHistory() {
	 //paymentRecordsChildList
	 //paymentRecordsPaymentHistoryListView
 }
 
 //For csv dave button
 public void saveCSV() {
	 //Get Term Name from dropdown
	 //Get Child ID and Name form childlist
	 //Get Presch/school from dropdown
	 //Get session dates from listview
	 //If presch get listviewA else get both

	 //Get summary from textarea
	 
	 //Summary first
	 //Session Dates, Session Dates
	 //Date,Date
	 
	 
	 String selectedListOption = invoiceChildList.getSelectionModel().getSelectedItem();
	 String[] splitSelected = selectedListOption.split(",");
	 String childID = splitSelected[0];
	 String name = splitSelected[1];
	 String termNameDropdown = invoiceTermDropdown.getSelectionModel().getSelectedItem();
	 String listViewA = invoiceListViewA.getItems().stream().map(Object::toString).collect(Collectors.joining(" , "));
	 String[] listDatesA = listViewA.split(",");
	 
	 
	 String listViewB = invoiceListViewB.getItems().stream().map(Object::toString).collect(Collectors.joining(" , "));
	 String summary = invoiceSummaryTextView.getText();
	 String toCsv = "";
	 String columnDates = "";
	 
 	boolean found = false;
 	int i = 0;
 	Child foundChild = null;
 	
 	do {
 		if(getGlobal.getStaticChild().get(i).getID() == Integer.parseInt(childID)) {
 			foundChild = getGlobal.getStaticChild().get(i);
 			found = true;
 		}

 		i++;

 		
 	}while(i < getGlobal.getStaticChild().size() && found == false);
	 	
	 	
	 /*
	 System.out.println("ID: "+childID);
	 System.out.println("Name: "+name);
	 System.out.println("Term Name: "+termNameDropdown);
	 System.out.println("ListViewA: "+listViewA);
	 System.out.println("ListViewB: "+listViewB);
	 System.out.println("Summary: "+summary);
	 */
	 
	 
	 if(foundChild.getPreschool() == true) {
		 toCsv = "Summary\n"+summary+"\nSession Dates\n"+listViewA;
	 }
	 else {
		 //columnDates = "";
		 String[] listDatesB = listViewA.split(",");
		 for(int dateCount = 0; dateCount< listDatesA.length || dateCount < listDatesA.length; dateCount++ ) {
			 columnDates += listDatesA[dateCount]+", "+listDatesB[dateCount]+"\n";
		 }
		 System.out.println(columnDates);
		 toCsv = "Summary\n"+summary+"\nBreakfast Session Dates, Afterschool Session Dates\n"+columnDates;
	 }
	 
	 
	 //Create filename from term start date and child info
	 String filename = listDatesA[0]+"_"+foundChild.getID()+"-"+foundChild.getFname()+foundChild.getSname()+".csv";
	 System.out.println("FileName: "+filename);
	//Write to file
		try {
		
	 		FileOutputStream outStream = new FileOutputStream(filename);
	 		ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
	 		
	 		objectOutputFile.writeObject(toCsv);
	 		
	 		objectOutputFile.close();
	 		outStream.close();


		}catch(Exception e) {
			//e.printStackTrace();
			System.out.println("File did not write");
		}
	 
 }
 
 public void initialize() {
	 setupAttendanceTerm(attendanceTermDropdown);
	 setupAttendanceTerm(invoiceTermDropdown);
	 setupAttendanceTerm(balanceTermDropdown);
	 setupSessionDatePicker();
	 dropdownOptionList();
	 loadAttendanceChildList();
	 
	 // Child Listviews
	 loadChildList(invoiceChildFilterTxtField,invoiceChildList);
	 loadChildList(balanceChildFilterTxtField,balanceChildList);
	 loadChildList(paymentRecordsChildFilterTxtField,paymentRecordsChildList);
	 
 }
 
 
 //Return to Admin Control Page
 public void backtoAdmin(ActionEvent e) throws  IOException{
 	getGlobal.backtoAdmin(e);
	
 }
     
     
 public void logout(ActionEvent e) {
	Global logout = new Global();
	try {
		logout.logout(e);
	} catch (IOException io) {
		// TODO Auto-generated catch block
		io.printStackTrace();
	}
 }

}
