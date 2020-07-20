package controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

import classes.Child;
import classes.Global;
import classes.Session;
import classes.Term;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SessionPageController {

    @FXML
    private DatePicker setupTermStart;

    @FXML
    private DatePicker setupTermEnd;

    @FXML
    private Button setupAddTermBtn;

    @FXML
    private ListView<String> setupCurrTermsListView;

    @FXML
    private ListView<String> bookingChildListView;

    @FXML
    private Button bookSessionBtn;

    @FXML
    private Label TermLabel;

    @FXML
    private TextField bookingChildFilterTxtField;

    @FXML
    private ChoiceBox<String> bookingTermDropdown;

    @FXML
    private Label SessionLabel;

    @FXML
    private DatePicker bookSessionDatePicker;

    @FXML
    private CheckBox preSchoolChkBox;

    @FXML
    private CheckBox breakfastChkBox;

    @FXML
    private CheckBox afterschoolChkBox;

    @FXML
    private Label brekSchCount;

    @FXML
    private Label preSchCount;

    @FXML
    private Label afterSchCount;
    
    @FXML
    private ListView<String> dataDispPreSchListView;

    @FXML
    private ListView<String> dataDispBrekListView;

    @FXML
    private ListView<String> dataDispAfterSchListView;
    
    @FXML
    private Button preschRemoveSelectedBtn;

    @FXML
    private Button preschClearAllBtn;

    @FXML
    private Button breakClearAllBtn;

    @FXML
    private Button breakRemoveSelectedBtn;

    @FXML
    private Button afterschClearAllBtn;

    @FXML
    private Button afterRemoveSelectedBtn;
    
    @FXML
    private ChoiceBox<String> bulkTermDropdown;

    @FXML
    private DatePicker bulkTermStartDatePicker;

    @FXML
    private DatePicker bulkTermEndDatePicker;

    @FXML
    private ListView<String> bulkTermChildList;

    @FXML
    private TextField bulkChildFilterTxtField;

    @FXML
    private TextArea bulkConfirmTxtView;

    @FXML
    private Button bulkBookSessionBtn;
    
    @FXML
    private Label bulkTermLabel;
    
    @FXML
    private CheckBox bulkPreSchoolChkBox;

    @FXML
    private CheckBox bulkBreakfastChkBox;

    @FXML
    private CheckBox bulkAfterschoolChkBox;
    
    @FXML
    private Button logoutBtn;

    @FXML
    private Button adminBtn;
    
    private Global getGlobal = new Global();
    
    
    
        
    //Collecting Data
    public void createTerm() {
    	//Check that form is not null
    	
    	if(setupTermStart.getValue() == null || setupTermEnd.getValue() == null) {
    		getGlobal.formIncomplete();
    	}
    	else 
    	{

    		//Make sure the data taken from form is valid LocalDate
	    	LocalDate termStartForm = setupTermStart.getValue();
	    	LocalDate termEndForm = setupTermEnd.getValue();

	    	
	    	//If adding to existing terms list
	    	if(getGlobal.getStaticTerm().size() > 0) {
	    		System.out.println("Adding to existing Terms list from global");
	    		
	    		
	    	    //Check that date does not exist between two dates
	    	    //Useful to ensure that a new term does not overlap on the previous existing terms  
	    		LocalDate checkStartDate, checkEndDate;
	    		int i = 0;
	    		boolean dateErrorCheck = false;
	    		
	    		do {
	    			checkStartDate = getGlobal.getStaticTerm().get(i).getTermStartDate();
	    			checkEndDate = getGlobal.getStaticTerm().get(i).getTermEndDate();
	    			
	    			//If dates exist already in the system or start date is after end date
	    			if(termStartForm.isBefore(checkEndDate) || termStartForm.isAfter(termEndForm) || termEndForm.isBefore(checkEndDate)) {
	    				System.out.println("Term start: "+termStartForm);
	    				System.out.println("Already in system term end date: "+checkEndDate);
	    				System.out.println("Term end: "+termEndForm);
	    				dateErrorCheck = true; 	    	
	    			}
	    			
	    			i++;
	    			//System.out.println("i at the end is: "+i);
	    			
	    		}while(i < getGlobal.getStaticTerm().size() );
	    		
	    		//If an error is found in the date input
	    		if(dateErrorCheck != false) {
	    			getGlobal.dateError();
	    		}
	    		//Else allow input into global and file system
	    		else 
	    		{
	    		
	    			//create new id +1 to old id
	    			int termSize = getGlobal.getStaticTerm().size();
	    			int genNewID = getGlobal.getStaticTerm().get(termSize-1).getTermID()+1;
	    			
	    			System.out.println("Got to add term (Existing)");
		    		//Add to global
		    		Term newTerm = new Term(genNewID,termStartForm,termEndForm);
		    		getGlobal.getStaticTerm().add(newTerm);
		    		
		    		try {
	            		FileOutputStream outStream = new FileOutputStream("Terms.dat");
	            		ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
	            		
	            		System.out.println("File overwritten");
	            		
	            		//Cycles through the existing terms in the arraylist and writes them to file
	            		for (int n = 0; n < getGlobal.getStaticTerm().size(); n++)
	          	      	{
	            			objectOutputFile.writeObject(getGlobal.getStaticTerm().get(n));
	          					
	          	      	}
	            		
	            		//System.out.println("Test: "+getGlobal.getStaticTerm().get(0).getTermName());
	            		
	            		objectOutputFile.close();
	            		outStream.close();
	            		
	            		//Set to listview
	            		displayListTerms();
	            		populateTermDropdowns();
	            		
	            		//Clear fields
	            		setupTermStart.setValue(null);
	            		setupTermEnd.setValue(null);
	        		}catch(Exception e) {
	            		e.printStackTrace();
	            		System.out.println("Failed to write to file");
	            	}
		    		
		    		
	    		}
	    	}
	    	
	    	else {
	    		
	    		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	    		//New file. Should only run once. the first time when first adding a term
	    		System.out.println("Adding first new term to system. Should only run in the first ever system run");
	    		
	    		//If start-date is after end-date error in input so popup
	    		if(termStartForm.isAfter(termEndForm)) {
	    			//If start date is after end date, that will be blocked
	    			getGlobal.dateError();
	    		}
	    		else {
	    			int newTermID = 00000001;
	    			Term newTerm = new Term(newTermID,termStartForm,termEndForm);
		    		getGlobal.setStaticTerm(newTerm);
		    		
		    		
		    		try {
	            		FileOutputStream outStream = new FileOutputStream("Terms.dat");
	            		ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
	            		
	            		System.out.println("File overwritten- Session Update");
	            		
	            		//Cycles through the existing terms in the arraylist and writes them to file
	            		for (int n = 0; n < getGlobal.getStaticTerm().size(); n++)
	          	      	{
	            			objectOutputFile.writeObject(getGlobal.getStaticTerm().get(n));
	          					
	          	      	}
	            		
	            		//System.out.println("Test: "+getGlobal.getStaticTerm().get(0).getTermName());
	            		
	            		objectOutputFile.close();
	            		outStream.close();
	            		
	            		//Set to listview
	            		displayListTerms();
	            		populateTermDropdowns();
	            		
	            		//Clear fields
	            		setupTermStart.setValue(null);
	            		setupTermEnd.setValue(null);
	        		}catch(Exception e) {
	            		e.printStackTrace();
	            		System.out.println("Failed to write to file");
	            	}
	    		}
	    		
	    		
	    	}
    	}

    }
    
    //Displays the existing terms in the system
    public void displayListTerms() {
    	
    	try {
    		ArrayList<Term> listViewTerms = new ArrayList<Term>();
    		
    		listViewTerms = getGlobal.getStaticTerm();
    		
    		
    		ObservableList<String> termList = FXCollections.observableArrayList();
    	
    		for(int i = 0; i< listViewTerms.size(); i++) {
    			termList.add(listViewTerms.get(i).getTermName());
    		}
    		
    		setupCurrTermsListView.setItems(termList);
    		
    	}catch(NullPointerException ne) {
    		ne.printStackTrace();
    	}
    	

    }

    
    //Creates session and add to selected term
    public void createSession() {
    	
		
    	populateTermDropdowns();
    	
    }
    
    
    
    //Populate dropdown terms list
    public void populateTermDropdowns() {
    	
    	ArrayList<Term> dropdownListTerms = getGlobal.getStaticTerm();

		
		ObservableList<String> termDropList = FXCollections.observableArrayList();
	
		for(int i = 0; i< dropdownListTerms.size(); i++) {
			termDropList.add(dropdownListTerms.get(i).getTermName());
		}
    	
		bookingTermDropdown.getItems().clear();
		bookingTermDropdown.getItems().addAll(termDropList);
		
		bulkTermDropdown.getItems().clear();
		bulkTermDropdown.getItems().addAll(termDropList);
    }
    
    
    //Loads the session for the selected term as well as set the initial date to the first day of the term
    //Runs when a term is selected from dropdown
    public void setupSessionDatepicker() {
    	//bookingPreSchTermDropdown.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    	bookingTermDropdown.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

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
		    			String selectedTermName = bookingTermDropdown.getSelectionModel().getSelectedItem();
						String globalTerm = getGlobal.getStaticTerm().get(i).getTermName();
						
						TermLabel.setText(selectedTermName);
		    			if(selectedTermName.equals(globalTerm)) {
		    				bookSessionDatePicker.setValue(getGlobal.getStaticTerm().get(i).getTermStartDate());
		    				checkfound = true;
		    			}
		    			
		    			i++;
		    			
		    		}while(i < getGlobal.getStaticTerm().size() && checkfound == false );
					
				}
				
				
			}
    	});
    }
    
    
    public void setupBulkSessionDatepicker() {
    	// bulkTermStartDatePicker  bulkTermEndDatePicker
    	bulkTermDropdown.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

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
		    			String selectedTermName = bulkTermDropdown.getSelectionModel().getSelectedItem();
						String globalTerm = getGlobal.getStaticTerm().get(i).getTermName();
						
						bulkTermLabel.setText(selectedTermName);
		    			if(selectedTermName.equals(globalTerm)) {
		    				bulkTermStartDatePicker.setValue(getGlobal.getStaticTerm().get(i).getTermStartDate());
		    				bulkTermEndDatePicker.setValue(getGlobal.getStaticTerm().get(i).getTermEndDate());
		    				checkfound = true;
		    			}
		    			
		    			i++;
		    			
		    		}while(i < getGlobal.getStaticTerm().size() && checkfound == false );
					
				}
				
				
			}
    	});
    }
    
    //Load previously booked children in selected session
    //Runs when a date is picked in the session datepicker
    public void loadCurrentBookedChildren() {    	
    	try {
    	if(bookSessionDatePicker.getValue() != null) {
    		//System.out.println("Selected Date: "+bookSessionDatePicker.getValue());
    		
    		String bookingTermName = bookingTermDropdown.getSelectionModel().getSelectedItem();
    		LocalDate bookingSessionDate = bookSessionDatePicker.getValue();
    		
    		int i = 0;
			boolean findTerm = false;
			do {
				
				//Find term based on name in the system
				if(bookingTermName.equals(getGlobal.getStaticTerm().get(i).getTermName())) {
					
					Term foundterm = getGlobal.getStaticTerm().get(i);
					findTerm = true;
					
					int x = 0;
					boolean searchSession = false;
					do {
						if(bookingSessionDate.equals(foundterm.getSessions().get(x).getSessionDate())) {
							SessionLabel.setText(foundterm.getSessions().get(x).getSessionName());
							
							int preSchoolBookedChildCount = foundterm.getSessions().get(x).getPreSchSession().size();
							int breakfastBookedChildCount = foundterm.getSessions().get(x).getBreakfastSchSession().size();
							int afterSchoolBookedChildCount = foundterm.getSessions().get(x).getAfterSchSession().size();
							
							preSchCount.setText(String.valueOf(preSchoolBookedChildCount));
							brekSchCount.setText(String.valueOf(breakfastBookedChildCount));
							afterSchCount.setText(String.valueOf(afterSchoolBookedChildCount));
							
							displayTableview(foundterm.getSessions().get(x));
						}
						x++;
					}while(x < foundterm.getSessions().size() && searchSession == false);
				}
				
				i++;
			}while(i < getGlobal.getStaticTerm().size() && findTerm == false);
    	}
    	}catch(NullPointerException np) {
    		
    	}
    	
    }
    
    public void clearPreschSession() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Clear All");
    	alert.setContentText("Are you sure you want to clear session of all children?");


    	ButtonType buttonTypeYes = new ButtonType("Yes");
    	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    	alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
    	
    	Optional<ButtonType> result = alert.showAndWait();

    	if (result.get() == buttonTypeYes){
    		try 
        	{
            	if(bookSessionDatePicker.getValue() != null) {
            		//System.out.println("Selected Date: "+bookSessionDatePicker.getValue());
            		
            		String bookingTermName = bookingTermDropdown.getSelectionModel().getSelectedItem();
            		LocalDate bookingSessionDate = bookSessionDatePicker.getValue();
            		
            		int i = 0;
        			boolean findTerm = false;
        			do {
        				
        				//Find term based on name in the system
        				if(bookingTermName.equals(getGlobal.getStaticTerm().get(i).getTermName())) {
        					
        					Term foundterm = getGlobal.getStaticTerm().get(i);
        					findTerm = true;
        					
        					int x = 0;
        					boolean searchSession = false;
        					do {
        						if(bookingSessionDate.equals(foundterm.getSessions().get(x).getSessionDate())) {
        							getGlobal.getStaticTerm().get(i).getSessions().get(x).getPreSchSession().clear();
        							displayTableview(foundterm.getSessions().get(x));
        							
        							int preSchoolBookedChildCount = foundterm.getSessions().get(x).getPreSchSession().size();
        							preSchCount.setText(String.valueOf(preSchoolBookedChildCount));
        							
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
        			            		
        			            		//System.out.println("Test: "+getGlobal.getStaticTerm().get(0).getTermName());
        			            		
        			            		objectOutputFile.close();
        			            		outStream.close();
        			            		
        			            		//Set to listview
        			            		displayListTerms();
        			            		//populateTermDropdowns();
        							}catch(Exception e) {
        								//e.printStackTrace();
        							}
        							
        							
        							searchSession = true;
        							
        						}
        						x++;
        					}while(x < foundterm.getSessions().size() && searchSession == false);
        				}
        				
        				i++;
        			}while(i < getGlobal.getStaticTerm().size() && findTerm == false);
            	}
        	}catch(NullPointerException np) {
        		np.printStackTrace();
        	}
    	}
    	
    }
    
    public void clearPreschSelectedSession() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Remove Child Session");
    	alert.setContentText("Are you sure you want to remove selected child?");


    	ButtonType buttonTypeYes = new ButtonType("Yes");
    	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    	alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
    	
    	Optional<ButtonType> result = alert.showAndWait();

    	if (result.get() == buttonTypeYes){
    		try 
        	{
            	if(bookSessionDatePicker.getValue() != null) {
            		//System.out.println("Selected Date: "+bookSessionDatePicker.getValue());
            		
            		String bookingTermName = bookingTermDropdown.getSelectionModel().getSelectedItem();
            		LocalDate bookingSessionDate = bookSessionDatePicker.getValue();
            		int selectedListIndex = dataDispPreSchListView.getSelectionModel().getSelectedIndex();
            		
            		int i = 0;
        			boolean findTerm = false;
        			do {
        				
        				//Find term based on name in the system
        				if(bookingTermName.equals(getGlobal.getStaticTerm().get(i).getTermName())) {
        					
        					Term foundterm = getGlobal.getStaticTerm().get(i);
        					findTerm = true;
        					
        					int x = 0;
        					boolean searchSession = false;
        					do {
        						if(bookingSessionDate.equals(foundterm.getSessions().get(x).getSessionDate())) {
        							getGlobal.getStaticTerm().get(i).getSessions().get(x).getPreSchSession().remove(selectedListIndex);
        							displayTableview(foundterm.getSessions().get(x));
        							
        							int preSchoolBookedChildCount = foundterm.getSessions().get(x).getPreSchSession().size();
        							preSchCount.setText(String.valueOf(preSchoolBookedChildCount));
        							
        							
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
        			            		
        			            		//System.out.println("Test: "+getGlobal.getStaticTerm().get(0).getTermName());
        			            		
        			            		objectOutputFile.close();
        			            		outStream.close();
        			            		
        			            		//Set to listview
        			            		displayListTerms();
        			            		//populateTermDropdowns();
        							}catch(Exception e) {
        								//e.printStackTrace();
        							}
        							
        							
        							searchSession = true;
        							
        						}
        						x++;
        					}while(x < foundterm.getSessions().size() && searchSession == false);
        				}
        				
        				i++;
        			}while(i < getGlobal.getStaticTerm().size() && findTerm == false);
            	}
        	}catch(NullPointerException np) {
        		np.printStackTrace();
        	}
    	}
    	
    }
    
    
    
    public void clearBreakschSession() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Clear All");
    	alert.setContentText("Are you sure you want to clear session of all children?");


    	ButtonType buttonTypeYes = new ButtonType("Yes");
    	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    	alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
    	
    	Optional<ButtonType> result = alert.showAndWait();

    	if (result.get() == buttonTypeYes){
    		try 
        	{
            	if(bookSessionDatePicker.getValue() != null) {
            		//System.out.println("Selected Date: "+bookSessionDatePicker.getValue());
            		
            		String bookingTermName = bookingTermDropdown.getSelectionModel().getSelectedItem();
            		LocalDate bookingSessionDate = bookSessionDatePicker.getValue();
            		
            		int i = 0;
        			boolean findTerm = false;
        			do {
        				
        				//Find term based on name in the system
        				if(bookingTermName.equals(getGlobal.getStaticTerm().get(i).getTermName())) {
        					
        					Term foundterm = getGlobal.getStaticTerm().get(i);
        					findTerm = true;
        					
        					int x = 0;
        					boolean searchSession = false;
        					do {
        						if(bookingSessionDate.equals(foundterm.getSessions().get(x).getSessionDate())) {
        							getGlobal.getStaticTerm().get(i).getSessions().get(x).getBreakfastSchSession().clear();
        							displayTableview(foundterm.getSessions().get(x));
        							
        							
        							int breakfastBookedChildCount = foundterm.getSessions().get(x).getBreakfastSchSession().size();
        							brekSchCount.setText(String.valueOf(breakfastBookedChildCount));
        							
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
        			            		
        			            		//System.out.println("Test: "+getGlobal.getStaticTerm().get(0).getTermName());
        			            		
        			            		objectOutputFile.close();
        			            		outStream.close();
        			            		
        			            		//Set to listview
        			            		displayListTerms();
        			            		//populateTermDropdowns();
        							}catch(Exception e) {
        								//e.printStackTrace();
        							}
        							
        							searchSession = true;
        						}
        						x++;
        					}while(x < foundterm.getSessions().size() && searchSession == false);
        				}
        				
        				i++;
        			}while(i < getGlobal.getStaticTerm().size() && findTerm == false);
            	}
        	}catch(NullPointerException np) {
        		np.printStackTrace();
        	}
    	}
    	
    }
    
    
    public void clearBreakSelectedSession() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Remove Child Session");
    	alert.setContentText("Are you sure you want to remove selected child?");


    	ButtonType buttonTypeYes = new ButtonType("Yes");
    	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    	alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
    	
    	Optional<ButtonType> result = alert.showAndWait();

    	if (result.get() == buttonTypeYes){
    		try 
        	{
            	if(bookSessionDatePicker.getValue() != null) {
            		//System.out.println("Selected Date: "+bookSessionDatePicker.getValue());
            		
            		String bookingTermName = bookingTermDropdown.getSelectionModel().getSelectedItem();
            		LocalDate bookingSessionDate = bookSessionDatePicker.getValue();
            		int selectedListIndex = dataDispBrekListView.getSelectionModel().getSelectedIndex();
            		
            		int i = 0;
        			boolean findTerm = false;
        			do {
        				
        				//Find term based on name in the system
        				if(bookingTermName.equals(getGlobal.getStaticTerm().get(i).getTermName())) {
        					
        					Term foundterm = getGlobal.getStaticTerm().get(i);
        					findTerm = true;
        					
        					int x = 0;
        					boolean searchSession = false;
        					do {
        						if(bookingSessionDate.equals(foundterm.getSessions().get(x).getSessionDate())) {
        							getGlobal.getStaticTerm().get(i).getSessions().get(x).getBreakfastSchSession().remove(selectedListIndex);
        							displayTableview(foundterm.getSessions().get(x));
        							
        							int breakfastBookedChildCount = foundterm.getSessions().get(x).getBreakfastSchSession().size();
        							brekSchCount.setText(String.valueOf(breakfastBookedChildCount));
        							
        							
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
        			            		
        			            		//Set to listview
        			            		displayListTerms();
        			            		//populateTermDropdowns();
        							}catch(Exception e) {
        								//e.printStackTrace();
        							}
        							
        							
        							searchSession = true;
        							
        						}
        						x++;
        					}while(x < foundterm.getSessions().size() && searchSession == false);
        				}
        				
        				i++;
        			}while(i < getGlobal.getStaticTerm().size() && findTerm == false);
            	}
        	}catch(NullPointerException np) {
        		np.printStackTrace();
        	}
    	}
    	
    }
    
    public void clearAfterschSession() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Clear All");
    	alert.setContentText("Are you sure you want to clear session of all children?");


    	ButtonType buttonTypeYes = new ButtonType("Yes");
    	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    	alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
    	
    	Optional<ButtonType> result = alert.showAndWait();

    	if (result.get() == buttonTypeYes){
    		try 
        	{
            	if(bookSessionDatePicker.getValue() != null) {
            		//System.out.println("Selected Date: "+bookSessionDatePicker.getValue());
            		
            		String bookingTermName = bookingTermDropdown.getSelectionModel().getSelectedItem();
            		LocalDate bookingSessionDate = bookSessionDatePicker.getValue();
            		
            		int i = 0;
        			boolean findTerm = false;
        			do {
        				
        				//Find term based on name in the system
        				if(bookingTermName.equals(getGlobal.getStaticTerm().get(i).getTermName())) {
        					
        					Term foundterm = getGlobal.getStaticTerm().get(i);
        					findTerm = true;
        					
        					int x = 0;
        					boolean searchSession = false;
        					do {
        						if(bookingSessionDate.equals(foundterm.getSessions().get(x).getSessionDate())) {
        							getGlobal.getStaticTerm().get(i).getSessions().get(x).getAfterSchSession().clear();
        							displayTableview(foundterm.getSessions().get(x));
        							
        							int afterSchoolBookedChildCount = foundterm.getSessions().get(x).getAfterSchSession().size();
        							afterSchCount.setText(String.valueOf(afterSchoolBookedChildCount));
        							
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
        			            		
        			            		//System.out.println("Test: "+getGlobal.getStaticTerm().get(0).getTermName());
        			            		
        			            		objectOutputFile.close();
        			            		outStream.close();
        			            		
        			            		//Set to listview
        			            		displayListTerms();
        			            		//populateTermDropdowns();
        							}catch(Exception e) {
        								//e.printStackTrace();
        							}
        							
        							searchSession = true;
        						}
        						x++;
        					}while(x < foundterm.getSessions().size() && searchSession == false);
        				}
        				
        				i++;
        			}while(i < getGlobal.getStaticTerm().size() && findTerm == false);
            	}
        	}catch(NullPointerException np) {
        		np.printStackTrace();
        	}
    	}
    	
    }
    
    
    public void clearAfterschSelectedSession() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Remove Child Session");
    	alert.setContentText("Are you sure you want to remove selected child?");


    	ButtonType buttonTypeYes = new ButtonType("Yes");
    	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    	alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
    	
    	Optional<ButtonType> result = alert.showAndWait();

    	if (result.get() == buttonTypeYes){
    		try 
        	{
            	if(bookSessionDatePicker.getValue() != null) {
            		//System.out.println("Selected Date: "+bookSessionDatePicker.getValue());
            		
            		String bookingTermName = bookingTermDropdown.getSelectionModel().getSelectedItem();
            		LocalDate bookingSessionDate = bookSessionDatePicker.getValue();
            		int selectedListIndex = dataDispAfterSchListView.getSelectionModel().getSelectedIndex();
            		
            		int i = 0;
        			boolean findTerm = false;
        			do {
        				
        				//Find term based on name in the system
        				if(bookingTermName.equals(getGlobal.getStaticTerm().get(i).getTermName())) {
        					
        					Term foundterm = getGlobal.getStaticTerm().get(i);
        					findTerm = true;
        					
        					int x = 0;
        					boolean searchSession = false;
        					do {
        						if(bookingSessionDate.equals(foundterm.getSessions().get(x).getSessionDate())) {
        							getGlobal.getStaticTerm().get(i).getSessions().get(x).getAfterSchSession().remove(selectedListIndex);
        							displayTableview(foundterm.getSessions().get(x));
        							
        							int afterSchoolBookedChildCount = foundterm.getSessions().get(x).getAfterSchSession().size();
        							afterSchCount.setText(String.valueOf(afterSchoolBookedChildCount));
        							
        							
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
        			            		
        			            		//Set to listview
        			            		displayListTerms();
        			            		//populateTermDropdowns();
        							}catch(Exception e) {
        								//e.printStackTrace();
        							}
        							
        							
        							searchSession = true;
        							
        						}
        						x++;
        					}while(x < foundterm.getSessions().size() && searchSession == false);
        				}
        				
        				i++;
        			}while(i < getGlobal.getStaticTerm().size() && findTerm == false);
            	}
        	}catch(NullPointerException np) {
        		np.printStackTrace();
        	}
    	}
    	
    }
    
    //Disable checkboxes depending on the child's status e.g. preschool cannot check breakfast and afterschool boxes
    public void checkChildStatus() {
    	try {
    		String selectedListOption = bookingChildListView.getSelectionModel().getSelectedItem();
    		String[] splitSelected = selectedListOption.split(",");
    	
	    	//ID is at 0
	    	//Search global with that
	    	boolean found = false;
	    	int i = 0;
	    	Child foundChild = null;
	    	
	    	
	    	do {
	    		if(getGlobal.getStaticChild().get(i).getID() == Integer.parseInt(splitSelected[0])) {
	    			foundChild = getGlobal.getStaticChild().get(i);
	    			found = true;
	    			
	    			
	    			if(foundChild.getPreschool() == true) {
	    				//For preschool kids
	    				breakfastChkBox.setDisable(true);
	    				breakfastChkBox.setSelected(false);
	    				afterschoolChkBox.setDisable(true);
	    				afterschoolChkBox.setSelected(false);
	    				
	    				preSchoolChkBox.setDisable(false);
	    				preSchoolChkBox.setSelected(true);
	    			}
	    			else {
	    				//for school kids
	    				preSchoolChkBox.setDisable(true);
	    				preSchoolChkBox.setSelected(false);
	    				
	    				breakfastChkBox.setDisable(false);
	    				afterschoolChkBox.setDisable(false);
	    			}
	    		}
	
	    		i++;
	
	    		
	    	}while(i < getGlobal.getStaticChild().size() && found != true);
    	
    	}catch(NullPointerException np) {
    		
    	}
    }
    
    //Bulk disable checkboxes button
    public void checkBulkChildStatus() {
    	try {
    		String selectedListOption = bulkTermChildList.getSelectionModel().getSelectedItem();
    		String[] splitSelected = selectedListOption.split(",");
    	
	    	//ID is at 0
	    	//Search global with that
	    	boolean found = false;
	    	int i = 0;
	    	Child foundChild = null;
	    	
	    	
	    	do {
	    		if(getGlobal.getStaticChild().get(i).getID() == Integer.parseInt(splitSelected[0])) {
	    			foundChild = getGlobal.getStaticChild().get(i);
	    			found = true;
	    			
	    			
	    			if(foundChild.getPreschool() == true) {
	    				//For preschool kids
	    				bulkBreakfastChkBox.setDisable(true);
	    				bulkBreakfastChkBox.setSelected(false);
	    				bulkAfterschoolChkBox.setDisable(true);
	    				bulkAfterschoolChkBox.setSelected(false);
	    				
	    				bulkPreSchoolChkBox.setDisable(false);
	    				bulkPreSchoolChkBox.setSelected(true);
	    			}
	    			else {
	    				//for school kids
	    				bulkPreSchoolChkBox.setDisable(true);
	    				bulkPreSchoolChkBox.setSelected(false);
	    				
	    				bulkBreakfastChkBox.setDisable(false);
	    				bulkAfterschoolChkBox.setDisable(false);
	    			}
	    		}
	
	    		i++;
	
	    		
	    	}while(i < getGlobal.getStaticChild().size() && found != true);
    	
    	}catch(NullPointerException np) {
    		
    	}
    }
    
    public void bookingChildFilter(TextField searchfield, ListView listview) {
    	
    	ArrayList<Child> childList = new ArrayList<Child>();
    	
    	try {
        	//Observable list to hold all the current children in the system
        	ObservableList<String> foundChildren = FXCollections.observableArrayList();

        	childList = getGlobal.getStaticChild();
        	for(int i = 0; i< childList.size(); i++) {
        		foundChildren.add(childList.get(i).getID()+", "+childList.get(i).getFname()+" "+childList.get(i).getSname());
    		}
        	
        	//Filterlist added to the observable list
        	FilteredList<String> filteredData = new FilteredList<>(foundChildren, s -> true);
        	
        	//Listener added to the textfield to search up text input
        	searchfield.textProperty().addListener(obs->{
                String filter = searchfield.getText();
                
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
        	listview.setItems(filteredData);
        	}catch(Exception e){
        		System.out.println("No one found. Error in search. Probably non existent global set");
        	}
    }
    
    
    
   //Book Session with selected choices
    public void bookChildSession() {
    	//Get Session ID from dropdown, Get child Id from listview, Get Selected radio buttons, Add to Selected session
    	
    	
    	/*
    	 * Have date check, if selected session date is not within term limits, popup error
    	 * Check checkboxes are also selected
    	 */
		if(bookSessionDatePicker.getValue() == null) {
			getGlobal.formIncomplete();
		}
		else {
			try {
	        	String bookingTermName = bookingTermDropdown.getSelectionModel().getSelectedItem();
	        	LocalDate bookingSessionDate = bookSessionDatePicker.getValue();
	        	String selectedListChild = bookingChildListView.getSelectionModel().getSelectedItem();
	        	String[] splitSelected = selectedListChild.split(",");
	        	int bookingChildID = Integer.parseInt(splitSelected[0]);
	        	String childName = splitSelected[1];
	        	boolean preSchoolSelected = preSchoolChkBox.isSelected();
	        	boolean breakfastSelected = breakfastChkBox.isSelected();
	        	boolean afterSchoolSelected = afterschoolChkBox.isSelected();
	        	
	        	Alert alert = new Alert(AlertType.INFORMATION);
	        	alert.setTitle("Book Session");
	        	
	        	String popupConfirm = "Selected Session: "+bookingSessionDate+
	        			"\nChild Name: "+childName;
	        	
	        	alert.setContentText("Are you sure you want to book the following?\n"+popupConfirm);


	        	ButtonType buttonTypeYes = new ButtonType("Yes");
	        	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	        	alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
	        	
	        	Optional<ButtonType> result = alert.showAndWait();
	        	if (result.get() == buttonTypeYes){

	        			System.out.println("Didn't catch null");
	            		//Clears previous data set
	            		//tablePreSchSessionDisplay.getItems().clear();
	            		
	        			
	        			
	        			//Find child object based on selected child
	        			int y = 0;
	        			boolean foundchild = false;
	        			Child foundChildObject = null;
	        			
	        			do {
	        				
	        				if(bookingChildID == getGlobal.getStaticChild().get(y).getID()) {
	        					foundChildObject = getGlobal.getStaticChild().get(y);
	        					foundchild = true;
	        				}
	        				
	        				y++;
	        			}while(y < getGlobal.getStaticChild().size() && foundchild == false);
	        			
	        			//System.out.println("Got here- found child");
	            		//Add to global and write to file
	        			int i = 0;
	        			boolean addedSession = false;
	        			boolean duplicateChild = false;
	        			do {
	        				
	        				//Find term based on name in the system
	        				if(bookingTermName.equals(getGlobal.getStaticTerm().get(i).getTermName())) {
	        					
	        					Term foundterm = getGlobal.getStaticTerm().get(i);

	        					//System.out.println("Got here- found term");
	        					//Within found term, find session
	        					int x = 0;
	        					boolean searchSession = false;
	        					do {
	        						
	        						if(bookingSessionDate.equals(foundterm.getSessions().get(x).getSessionDate())) {
	        							
	        							if(preSchoolSelected) {
	        								//Checks if child is not already booked
	        								duplicateChild = getGlobal.childDupliCheck(foundterm.getSessions().get(x).getPreSchSession(), foundChildObject.getID());
	        								if(duplicateChild == false) {
	        									addedSession = foundterm.getSessions().get(x).addPreSchoolSessionChild(foundChildObject);
	        									
	        								}
	        								//System.out.println("Duplicate: "+duplicateChild);
	        							}
	        							
	        							if(breakfastSelected) {
	        								//System.out.println("Breakfast Selected");
	        								duplicateChild = getGlobal.childDupliCheck(foundterm.getSessions().get(x).getBreakfastSchSession(), foundChildObject.getID());
	        								
	        								
	        								if(duplicateChild == false) {
	        									addedSession = foundterm.getSessions().get(x).addbreakfastSchoolSessionChild(foundChildObject);
	        								}
	        								//System.out.println("Duplicate: "+duplicateChild);
	        							}
	        							
	        							if(afterSchoolSelected) {
	        								//System.out.println("AfterSchool selected");
	        								duplicateChild = getGlobal.childDupliCheck(foundterm.getSessions().get(x).getAfterSchSession(), foundChildObject.getID());
	        								if(duplicateChild == false) {
	        									addedSession = foundterm.getSessions().get(x).addAfterSchoolSessionChild(foundChildObject);
	        								}
	        								//System.out.println("Duplicate: "+duplicateChild);
	        								
	        							}
	        							
	        							
	        							if(preSchoolChkBox.isSelected() ==  false && breakfastChkBox.isSelected()  ==  false && afterschoolChkBox.isSelected()  ==  false) {
	        					        	Alert alert2 = new Alert(AlertType.WARNING);
	        					        	alert2.setTitle("Complete From");
	        					        	
	        					        	alert2.setContentText("Please complete the form correctly");
	        					        	ButtonType buttonTypeOK = new ButtonType("OK", ButtonData.CANCEL_CLOSE);
	        					        	alert2.getButtonTypes().setAll(buttonTypeOK);
	        					        	Optional<ButtonType> result2 = alert2.showAndWait();
	        						    	if (result2.get() == buttonTypeOK){
	        						    		//do nothing
	        						    	}
	        							}
	        							else if(addedSession == false && duplicateChild == false) {
	        					        	Alert alert1 = new Alert(AlertType.WARNING);
	        					        	alert1.setTitle("SESSION FULL");
	        					        	
	        					        	alert1.setContentText("Session is full");
	        					        	ButtonType buttonTypeOK = new ButtonType("OK", ButtonData.CANCEL_CLOSE);
	        					        	alert1.getButtonTypes().setAll(buttonTypeOK);
	        					        	Optional<ButtonType> result1 = alert1.showAndWait();
	        						    	if (result1.get() == buttonTypeOK){
	        						    		//do nothing
	        						    	}
	        							}
	        							else if(addedSession == false && duplicateChild == true) {
	        								getGlobal.alreadyBookedChildPopUp();
	        							}
	        							
	        							
	        							
	        							
	        							try {
	        								//Displaying to tabular view
	        								//System.out.println("Got to display");
	        								displayTableview(foundterm.getSessions().get(x));
	        								
	        								int preSchoolBookedChildCount = foundterm.getSessions().get(x).getPreSchSession().size();
	        								int breakfastBookedChildCount = foundterm.getSessions().get(x).getBreakfastSchSession().size();
	        								int afterSchoolBookedChildCount = foundterm.getSessions().get(x).getAfterSchSession().size();
	        								
	        								preSchCount.setText(String.valueOf(preSchoolBookedChildCount));
	        								brekSchCount.setText(String.valueOf(breakfastBookedChildCount));
	        								afterSchCount.setText(String.valueOf(afterSchoolBookedChildCount));
	        								
	        								
	        								
	        								System.out.println();
	        							}catch(Exception e) {
	        								e.printStackTrace();
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
	        			            		
	        			            		//System.out.println("Test: "+getGlobal.getStaticTerm().get(0).getTermName());
	        			            		
	        			            		objectOutputFile.close();
	        			            		outStream.close();
	        			            		
	        			            		//Set to listview
	        			            		displayListTerms();
	        			            		//populateTermDropdowns();
	        							}catch(Exception e) {
	        								//e.printStackTrace();
	        							}
	        							
	        							
	        							searchSession = true;
	        							
	        						}
	        						x++;
	        					}while(x < foundterm.getSessions().size() && searchSession == false);
	        					
	        				}
	        				
	        				i++;
	        			}while(i < getGlobal.getStaticTerm().size());
	            		

	        		
	        	}
	    	}
	    	catch(Exception e) {
	    		

	    	}

		}
    	
    }
    
    
    public void displayTableview(Session session) {

		ObservableList<String> foundPreSchoolChildren = FXCollections.observableArrayList();
		ObservableList<String> foundBreakfastChildren = FXCollections.observableArrayList();
		ObservableList<String> foundAfterSchoolChildren = FXCollections.observableArrayList();
    	
    	String dispPreSchText = "";
    	String dispBrekText = "";
    	String dispAfterSchText = "";
    	
    	for(int i = 0; i< session.getPreSchSession().size(); i++) {
    		//dispPreSchText = dispPreSchText + session.getPreSchSession().get(i).getFname()+" "+session.getPreSchSession().get(i).getSname()+"\n";
    		//foundPreSchoolChildren.clear();
    		foundPreSchoolChildren.add(session.getPreSchSession().get(i).getFname()+" "+session.getPreSchSession().get(i).getSname());

		}
    	
    	for(int i = 0; i< session.getBreakfastSchSession().size(); i++) {
    		//dispBrekText = dispBrekText + session.getBreakfastSchSession().get(i).getFname()+" "+session.getBreakfastSchSession().get(i).getSname()+"\n";
    		//foundBreakfastChildren.clear();
    		foundBreakfastChildren.add(session.getBreakfastSchSession().get(i).getFname()+" "+session.getBreakfastSchSession().get(i).getSname());
		}
    	
    	for(int i = 0; i< session.getAfterSchSession().size(); i++) {
    		//dispAfterSchText = dispAfterSchText + session.getAfterSchSession().get(i).getFname()+" "+session.getAfterSchSession().get(i).getSname()+"\n";
    		//foundBreakfastChildren.clear();
    		foundAfterSchoolChildren.add(session.getAfterSchSession().get(i).getFname()+" "+session.getAfterSchSession().get(i).getSname());
		}
    	

		try {
			
			dataDispPreSchListView.setItems(foundPreSchoolChildren);
			dataDispPreSchListView.getSelectionModel().clearSelection();
			
			dataDispBrekListView.setItems(foundBreakfastChildren);
			dataDispBrekListView.getSelectionModel().clearSelection();
			
			dataDispAfterSchListView.setItems(foundAfterSchoolChildren);
			dataDispAfterSchListView.getSelectionModel().clearSelection();
		}catch(NullPointerException np) {
			//np.printStackTrace();
		}
		
    	
    }
    
    
    public void bookSessionBulk() {
    	
		if(bulkTermStartDatePicker.getValue() == null || bulkTermEndDatePicker.getValue() == null) {
			getGlobal.formIncomplete();
		}else {
			try {
		    	String bookingTermName = bulkTermDropdown.getSelectionModel().getSelectedItem();
		    	LocalDate bookingStartSessionDate = bulkTermStartDatePicker.getValue();
		    	LocalDate bookingEndSessionDate = bulkTermEndDatePicker.getValue();
		    	int daysBetween = (int) ChronoUnit.DAYS.between(bookingStartSessionDate,bookingEndSessionDate);
		    	
		    	String selectedListChild = bulkTermChildList.getSelectionModel().getSelectedItem();
		    	String[] splitSelected = selectedListChild.split(",");
		    	int bookingChildID = Integer.parseInt(splitSelected[0]);
		    	String childName = splitSelected[1];
		    	boolean preSchoolSelected = bulkPreSchoolChkBox.isSelected();
		    	boolean breakfastSelected = bulkBreakfastChkBox.isSelected();
		    	boolean afterSchoolSelected = bulkAfterschoolChkBox.isSelected();
		    	
		    	
		    	Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Book Bulk Session");
		    	
		    	String popupConfirm = "Book sessions from \nStart Date: "+bookingStartSessionDate+
		    			"\nEnd Date: "+bookingEndSessionDate+
		    			"\nChild Name: "+childName;
		    	
		    	alert.setContentText("Are you sure you want to book the following?\n"+popupConfirm);
		
		
		    	ButtonType buttonTypeYes = new ButtonType("Yes");
		    	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		    	alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
		    	
		    	Optional<ButtonType> result = alert.showAndWait();
		    	if (result.get() == buttonTypeYes){

		    		//Find child object based on selected child
						int y = 0;
						boolean foundchild = false;
						Child foundChildObject = null;
						
						do {
							
							if(bookingChildID == getGlobal.getStaticChild().get(y).getID()) {
								foundChildObject = getGlobal.getStaticChild().get(y);
								foundchild = true;
							}
							
							y++;
						}while(y < getGlobal.getStaticChild().size() && foundchild == false);
						
						//System.out.println("Got here- found child");
			    		//Add to global and write to file
						int i = 0;
						boolean addedSession = false;
						boolean duplicateChild = false;
						do {
							
							//Find term based on name in the system
							if(bookingTermName.equals(getGlobal.getStaticTerm().get(i).getTermName())) {
								
								Term foundterm = getGlobal.getStaticTerm().get(i);
			
								//System.out.println("Got here- found term");
								//Within found term, find session
								
									int sessionsBookedCounter = 0;
									// Add session for each day between dates
									for(int n = 0; n <= daysBetween; n++) {
										System.out.println("N at start"+n);
										LocalDate setup = bookingStartSessionDate.plusDays(n);
										System.out.println(setup+ " -Day: "+setup.getDayOfWeek());
										
										int x = 0;
										boolean searchSession = false;
										do {
											if(setup.equals(foundterm.getSessions().get(x).getSessionDate()) && !(String.valueOf(setup.getDayOfWeek()).equals("SATURDAY")) 
													&& !(String.valueOf(setup.getDayOfWeek()).equals("SUNDAY"))) {
												sessionsBookedCounter++;
												if(preSchoolSelected) {
													//Checks if child is not already booked
													duplicateChild = getGlobal.childDupliCheck(foundterm.getSessions().get(x).getPreSchSession(), foundChildObject.getID());
													if(duplicateChild == false) {
														addedSession = foundterm.getSessions().get(x).addPreSchoolSessionChild(foundChildObject);
														
													}
													//System.out.println("Duplicate: "+duplicateChild);
												}
												
												if(breakfastSelected) {
													//System.out.println("Breakfast Selected");
													duplicateChild = getGlobal.childDupliCheck(foundterm.getSessions().get(x).getBreakfastSchSession(), foundChildObject.getID());
													
													
													if(duplicateChild == false) {
														addedSession = foundterm.getSessions().get(x).addbreakfastSchoolSessionChild(foundChildObject);
													}
													//System.out.println("Duplicate: "+duplicateChild);
												}
												
												if(afterSchoolSelected) {
													//System.out.println("AfterSchool selected");
													duplicateChild = getGlobal.childDupliCheck(foundterm.getSessions().get(x).getAfterSchSession(), foundChildObject.getID());
													if(duplicateChild == false) {
														addedSession = foundterm.getSessions().get(x).addAfterSchoolSessionChild(foundChildObject);
													}
													//System.out.println("Duplicate: "+duplicateChild);
													
												}
												
												
												if(bulkPreSchoolChkBox.isSelected() ==  false && bulkBreakfastChkBox.isSelected()  ==  false && bulkAfterschoolChkBox.isSelected()  ==  false) {
										        	Alert alert2 = new Alert(AlertType.WARNING);
										        	alert2.setTitle("Complete From");
										        	
										        	alert2.setContentText("Please complete the form correctly");
										        	ButtonType buttonTypeOK = new ButtonType("OK", ButtonData.CANCEL_CLOSE);
										        	alert2.getButtonTypes().setAll(buttonTypeOK);
										        	Optional<ButtonType> result2 = alert2.showAndWait();
											    	if (result2.get() == buttonTypeOK){
											    		//do nothing
											    	}
												}
												else if(addedSession == false && duplicateChild == false) {
										        	Alert alert1 = new Alert(AlertType.WARNING);
										        	alert1.setTitle("SESSION FULL");
										        	
										        	alert1.setContentText("Session is full");
										        	ButtonType buttonTypeOK = new ButtonType("OK", ButtonData.CANCEL_CLOSE);
										        	alert1.getButtonTypes().setAll(buttonTypeOK);
										        	Optional<ButtonType> result1 = alert1.showAndWait();
											    	if (result1.get() == buttonTypeOK){
											    		//do nothing
											    	}
												}
												else if(addedSession == false && duplicateChild == true) {
													getGlobal.alreadyBookedChildPopUp();
												}
				
												String confirmed = "Start Date: "+bookingStartSessionDate+"\nEnd Date: "+bookingEndSessionDate+
														"\nSessions Booked: "+sessionsBookedCounter;
												bulkConfirmTxtView.setText(confirmed);
												
												//Write to file
												try {
								            		FileOutputStream outStream = new FileOutputStream("Terms.dat");
								            		ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
								            		
								            		System.out.println("File overwritten");
								            		
								            		//Cycles through the existing terms in the arraylist and writes them to file
								            		for (int fileCount = 0; fileCount < getGlobal.getStaticTerm().size(); fileCount++)
								          	      	{
								            			objectOutputFile.writeObject(getGlobal.getStaticTerm().get(fileCount));
								          					
								          	      	}
								            		
								            		//System.out.println("Test: "+getGlobal.getStaticTerm().get(0).getTermName());
								            		
								            		objectOutputFile.close();
								            		outStream.close();
								            		
								            		//Set to listview
								            		displayListTerms();
								            		//populateTermDropdowns();
												}catch(Exception e) {
													//e.printStackTrace();
												}
												
												
												searchSession = true;
												
											}
											
											x++;
										}while(x < foundterm.getSessions().size() && searchSession == false);
									}
									
								
							}
							
							i++;
						}while(i < getGlobal.getStaticTerm().size());
		    		
		    		
		    	}
		    	
	    	}catch(Exception e) {
	    		
	    	}
		}
    	
    }
    
    public void initialize() {
    	
    	//Displays the available terms on open
    	displayListTerms();
    	
    	//Populates the term dropdowns in booking
    	populateTermDropdowns();
    	
    	//Sets up the child search fields in booking
    	bookingChildFilter(bookingChildFilterTxtField, bookingChildListView);
    	bookingChildFilter(bulkChildFilterTxtField, bulkTermChildList);
    	
    	//setup the initial month of the session selection datepicker
    	setupSessionDatepicker();
    	setupBulkSessionDatepicker();
    	
    	//Load previously booked children for selected session
    	loadCurrentBookedChildren();
    	
    	
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
