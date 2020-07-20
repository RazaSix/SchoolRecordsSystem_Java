package controllers;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import javafx.scene.control.ToggleGroup;

import classes.Admin;
import classes.Child;
import classes.Global;
import classes.Parent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientRegistrationController {

	
    @FXML
    private TextField childFnameTxtField;

    @FXML
    private TextField childLnameTxtField;

    @FXML
    private DatePicker childDOBField;

    @FXML
    private TextArea childAllergyTxtField;

    @FXML
    private CheckBox childPreschoolCheckbox;

    @FXML
    private CheckBox childGrantCheckbox;
    
    @FXML
    private CheckBox olderSiblingCheckbox;
    
    @FXML
    private TextField parentFnameTxtField;

    @FXML
    private TextField parentLNameTxtField;

    @FXML
    private DatePicker parentDOBField;

    @FXML
    private TextArea parentAddressTxtField;

    @FXML
    private TextField parentEmailTxtField;

    @FXML
    private Button AddChildBtn;

    @FXML
    private TextField modID;
    
    @FXML
    private TextField modChildFnameTxtField;
    
    @FXML
    private TextField modChildLnameTxtField;

    @FXML
    private TextField modParentFnameTxtField;

    @FXML
    private TextField modParentLnameTxtField;
    
    @FXML
    private TextField modParentEmailTxtField;

    @FXML
    private TextArea modChildAllergyTxtField;

    @FXML
    private Button modModifyBtn;
    
    @FXML
    private Button modDeleteBtn;

    @FXML
    private ListView<String> modChildListView;

    @FXML
    private TextField modFindParentsTxtView;

    @FXML
    private ToggleGroup modSearchTermRdioBtn;

    @FXML
    private TextArea modAddressTxtArea;
    
    @FXML
    private CheckBox modChildPreschoolCheckbox;

    @FXML
    private CheckBox modChildGrantCheckbox;   

    @FXML
    private CheckBox modChildSiblingCheckbox;

    @FXML
    private Button modRefreshParentsListBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button backToAdminBtn;
    
    
    //private ArrayList<Parent> sortParentList = new ArrayList<Parent>();
    private ArrayList<Child> childList = new ArrayList<Child>();
    
    private Global getGlobal = new Global();
    
    
    public void addClient() {
    	//Get info from form
    	if(
    			childFnameTxtField.getText() == null ||
    			childLnameTxtField.getText() == null ||
    			childDOBField.getValue() == null || 
    			childAllergyTxtField.getText() == null ||
    			parentFnameTxtField.getText() == null || 
    			parentLNameTxtField.getText() == null ||
    			parentAddressTxtField.getText() == null ||
				parentDOBField.getValue() == null ||
				parentEmailTxtField.getText() == null
    			) 
    	{
    		getGlobal.formIncomplete();
    	}else {

        	
        	//Get Child info.
        	//Get Parent info
        	//Add parent to child
        	//Add to file
        	
        	//Read back in modify tab
        	//For display in listview
        	//Allow searching
        	//Allow clicking to fill form - editable being only allergy info or parent address (Add back address field)

        	Alert alert = new Alert(AlertType.CONFIRMATION);
        	alert.setTitle("Confirm Registration");
        	alert.setContentText("Are you sure you want to add this client?");


        	ButtonType buttonTypeYes = new ButtonType("Yes");
        	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        	alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
        	
        	Optional<ButtonType> result = alert.showAndWait();

        	if (result.get() == buttonTypeYes){
        		
            	//If there are children already in the system
        		if(getGlobal.getStaticChild().size() > 0) {
        			System.out.println("Trying to add child to exisiting list");
            		//Gets latest child ID and adds 1
        			int globalCchildArrayListSize = getGlobal.getStaticChild().size();
            		int newID = getGlobal.getStaticChild().get(globalCchildArrayListSize-1).getID()+1;
            		
            		
        	    	//Add parent info to parent object
        	    	Parent newParent = new Parent(newID, parentFnameTxtField.getText(), parentLNameTxtField.getText(),parentAddressTxtField.getText(),
            				parentDOBField.getValue(),parentEmailTxtField.getText());
        			
        	    	//Add child info to child object
        	    	Child newChild = new Child(newID, childFnameTxtField.getText(), childLnameTxtField.getText(), 
        	    			childDOBField.getValue(), childAllergyTxtField.getText(), 
        	    			childPreschoolCheckbox.isSelected(), childGrantCheckbox.isSelected(), olderSiblingCheckbox.isSelected());
        	    	
        	    	//Add parent object to child object (array of one to hold parent)
        	    	newChild.addParent(newParent);
        	    	
        	    	//Add child to global variable
        	    	getGlobal.setStaticChild(newChild);
        	    	
            		System.out.println("Added to global child");
            		
            		try {
                		FileOutputStream outStream = new FileOutputStream("Clients.dat");
                		ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
                		
                		//Cycles through the existing admins in the arraylist and writes them to file
                		for (int i = 0; i < getGlobal.getStaticChild().size(); i++)
              	      	{
                			objectOutputFile.writeObject(getGlobal.getStaticChild().get(i));
              					
              	      	}
                		
                		
                		objectOutputFile.close();
                		outStream.close();
            		}catch(Exception e) {
                		e.printStackTrace();
                	}
        		}
        		else
        		{
        			//If there are no children already in the system (First Run)
            		//If file does not exist
            		System.out.println("Global not available- Probably first run");
            		
            		//Create the file
            		try {
            			
            			//Generates new ID for the parent
            			Random rand = new Random();
            	    	int firstID = 10000001;
            			
            	    	//Gets data from form textfields. Needs VALIDATION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            	    	//Add parent info to parent object
            	    	Parent newParent = new Parent(firstID, parentFnameTxtField.getText(), parentLNameTxtField.getText(),parentAddressTxtField.getText(),
                				parentDOBField.getValue(),parentEmailTxtField.getText());
            			
            	    	//Add child info to child object
            	    	Child newChild = new Child(firstID, childFnameTxtField.getText(), childLnameTxtField.getText(), 
            	    			childDOBField.getValue(), childAllergyTxtField.getText(), childPreschoolCheckbox.isSelected(),
            	    			childGrantCheckbox.isSelected(), olderSiblingCheckbox.isSelected());
            	    	
            	    	//Add parent object to child object (array of one to hold parent)
            	    	newChild.addParent(newParent);
            	    	
            	    	//Add child to global variable
            	    	getGlobal.setStaticChild(newChild);
            			
            			FileOutputStream outStream = new FileOutputStream("Clients.dat");
                		ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
                		
                		//Writes to Parent file
                		for (int i = 0; i < getGlobal.getStaticChild().size(); i++)
              	      	{
                			objectOutputFile.writeObject(getGlobal.getStaticChild().get(i));
              					
              	      	}
                		
                		//objectOutputFile.writeObject(existingAdmins.get().);
                		objectOutputFile.close();
                		outStream.close();
                		System.out.println("Created new file");
                		
                		setChildList();
                		
                		
            		}catch(IOException ioe) {
            			System.out.println("Error creating file");
            			ioe.printStackTrace();
            		}
        		}
                	
            	
            	
            		//Clear Parent form method called after submit
            		clearAddForm();
            		//Set info to listview method
            		setChildList();
        	}
    	}
    	
    }
    
    
    // Clears parent form
    public void clearAddForm() {
    	childFnameTxtField.clear();
    	childLnameTxtField.clear();
    	childDOBField.setValue(null);
    	childAllergyTxtField.clear();
    	childPreschoolCheckbox.setSelected(false);
    	childGrantCheckbox.setSelected(false);
    	
    	parentFnameTxtField.clear();
    	parentLNameTxtField.clear();
    	parentAddressTxtField.clear();
		parentDOBField.setValue(null);
		parentEmailTxtField.clear();
    }
    
    
    
    // Clear Modify Form
    public void clearModForm() {
    	modID.clear();
		modChildFnameTxtField.clear();
		modChildLnameTxtField.clear();
    	modParentFnameTxtField.clear();
    	modParentLnameTxtField.clear();
    	modParentEmailTxtField.clear();
    	modChildAllergyTxtField.clear();
    	modAddressTxtArea.clear();
    	modChildPreschoolCheckbox.setSelected(false);
    	modChildGrantCheckbox.setSelected(false);
    }
    
    
    public void disableGrant() {
    	if(childPreschoolCheckbox.isSelected()) {
    		childGrantCheckbox.setDisable(false);
    	}
    	else {
    		childGrantCheckbox.setDisable(true);
    	}
    }
    
    public void modDisableGrant() {
    	if(modChildPreschoolCheckbox.isSelected()) {
    		modChildGrantCheckbox.setDisable(false);
    	}
    	else {
    		modChildGrantCheckbox.setDisable(true);
    	}
    }
    
    public void filterSearchTest() {
    	
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
    	modFindParentsTxtView.textProperty().addListener(obs->{
            String filter = modFindParentsTxtView.getText();
            
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
    	modChildListView.setItems(filteredData);
    	}catch(Exception e){
    		System.out.println("No one found. Error in search. Probably non existent global set");
    	}
    }

    
    public void addFoundtoForm() {
    	
    	String selectedListOption = modChildListView.getSelectionModel().getSelectedItem();
    	String[] splitSelected = selectedListOption.split(",");
    	
    	//ID is at 0
    	//Search global with that
    	boolean found = false;
    	int i = 0;
    	Child foundChild = null;
    	
    	//Test prints
    	//System.out.println("Split string from listView should be ID only: "+splitSelected[0]);
    	//System.out.println("ID test from Global Child: "+getGlobal.getStaticChild().get(i).getID());
    	
    	do {
    		if(getGlobal.getStaticChild().get(i).getID() == Integer.parseInt(splitSelected[0])) {
    			foundChild = getGlobal.getStaticChild().get(i);
    			found = true;
    		}

    		i++;

    		
    	}while(i < getGlobal.getStaticChild().size() && found == false);
    	

    	
    	try {
    		modID.setText(String.valueOf(foundChild.getID()));
    		modChildFnameTxtField.setText(foundChild.getFname());
    		modChildLnameTxtField.setText(foundChild.getSname());
        	modParentFnameTxtField.setText(foundChild.getParent().getFname());
        	modParentLnameTxtField.setText(foundChild.getParent().getSname());
        	modParentEmailTxtField.setText(foundChild.getParent().getEmail());
        	modChildAllergyTxtField.setText(foundChild.getAllergyInfo());
        	modAddressTxtArea.setText(foundChild.getParent().getAddress());
        	modChildPreschoolCheckbox.setSelected(foundChild.getPreschool());
        	modChildGrantCheckbox.setSelected(foundChild.getGrant());
        	modChildSiblingCheckbox.setSelected(foundChild.getSiblingCheck());
    	}catch(NullPointerException ne) {
    		System.out.println("Error in search from listview");
    		ne.printStackTrace();
    	}

    	
    	
    }
    
    
    //Sets existing parents info to listview
    public void setChildList() { 
    	
    	//Clears local arraylist before calling from global
    	//childList.clear();

    	//Gets children list from global if not empty
    	try {
    	childList = getGlobal.getStaticChild();
    	//System.out.println("Got child list from global");
    	
    	}catch(NullPointerException ne) {
    		childList.isEmpty();    		
    		ne.printStackTrace();
    	}
    	
    	//List populated with names from file for display  
		ObservableList<String> childNames = FXCollections.observableArrayList();
    	
		
		//Append IDs and Names to observable list for Listview 
		for(int i = 0; i< childList.size(); i++) {
			childNames.add(childList.get(i).getID()+", "+childList.get(i).getFname()+" "+childList.get(i).getSname());
		}
		
		//Sort
		childNames.sort(Comparator.<String>naturalOrder());
    	
    	//Set to listview
    	modChildListView.setItems(childNames);
    	
    	modFindParentsTxtView.clear();
    	filterSearchTest();
    }
    
    
    
    
    
    //Modify Details
    public void modifyPerson() {
    	/*
    	1. Get text from form
    	2. Find user based on ID to int
    	3. Overwrite info on global
    	4. Overwrite to file
    	*/
    	
    	if(
    			modChildFnameTxtField.getText() == null ||
    			modChildLnameTxtField.getText() == null ||
    	    	modParentFnameTxtField.getText() == null ||
    	    	modParentLnameTxtField.getText() == null ||
    	    	modParentEmailTxtField.getText() == null ||
    	    	modChildAllergyTxtField.getText() == null ||
    	    	modAddressTxtArea.getText() == null
    		) 
    	{
    		getGlobal.formIncomplete();
    	}
    	else {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
        	alert.setTitle("Confirm Change");
        	alert.setContentText("Are you sure you want to save these changes?");


        	ButtonType buttonTypeYes = new ButtonType("Yes");
        	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        	alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
        	
        	Optional<ButtonType> result = alert.showAndWait();

        	if (result.get() == buttonTypeYes){
        		
        		//Confirm modification
        		boolean found = false;
            	int i = 0;
            	Child foundChild = null;
            	int foundIndex = 0; 
            	
            	//Find child based on ID
            	do {
            		if(getGlobal.getStaticChild().get(i).getID() == Integer.parseInt(modID.getText())) {
            			foundChild = getGlobal.getStaticChild().get(i);
            			found = true;
            			foundIndex = i;
            			//System.out.println("Found "+foundChild.getID());
            		}

            		i++;

            		
            	}while(i < getGlobal.getStaticChild().size() || found == false);
            	
            	
            	//If found
            	if(found==true) {
            		//Get "new info" from Form
            		foundChild.setFname(modChildFnameTxtField.getText());
            		foundChild.setSname(modChildLnameTxtField.getText());
            		foundChild.getParent().setFname(modParentFnameTxtField.getText());
            		foundChild.getParent().setSname(modParentLnameTxtField.getText());
            		foundChild.getParent().setEmail(modParentEmailTxtField.getText());
            		foundChild.setAllergyInfo(modChildAllergyTxtField.getText());
            		foundChild.getParent().setAddress(modAddressTxtArea.getText());
            		foundChild.setPreschool(modChildPreschoolCheckbox.isSelected());
            		foundChild.setGrant(modChildGrantCheckbox.isSelected());
            		foundChild.setSibling(modChildSiblingCheckbox.isSelected());
            		
            		//Replace existing element with new info
            		//System.out.println("Before "+getGlobal.getStaticChild().get(foundIndex).getSname());
            		getGlobal.getStaticChild().set(foundIndex, foundChild);
            		
            		//System.out.println("After "+getGlobal.getStaticChild().get(foundIndex).getSname());
            		
            		//Write to file
            		try {
            		FileOutputStream outStream = new FileOutputStream("Clients.dat");
            		ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
            		
            		//Writes to Parent file
            		for (int count = 0; count < getGlobal.getStaticChild().size(); count++)
          	      	{
            			objectOutputFile.writeObject(getGlobal.getStaticChild().get(count));
          					
          	      	}
            		
            		//objectOutputFile.writeObject(existingAdmins.get().);
            		objectOutputFile.close();
            		outStream.close();
            		}catch(Exception e) {
            			System.out.println("Trying to overwrite file in Modify function");
            		}
            	}
        	
            	setChildList();
            	
        	} else {
        	    // Do nothing
        	}
    	}
    	
    	

    }
    
    
    //Delete Person
    public void deletePerson() {
    	
    	/*
    	 modID (ID textfield)
    	1. Find person based on ID
    	2. Remove from global
    	3. Overwrite file
    	*/
    	
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirm Change");
    	alert.setContentText("Are you sure you want to delete this client?");


    	ButtonType buttonTypeYes = new ButtonType("Yes");
    	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    	alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
    	
    	Optional<ButtonType> result = alert.showAndWait();

    	if (result.get() == buttonTypeYes){
    		
    		//Confirm modification
    		boolean found = false;
        	int i = 0;
        	int foundIndex = 0; 
        	
        	//Find child based on ID
        	do {
        		if(getGlobal.getStaticChild().get(i).getID() == Integer.parseInt(modID.getText())) {
        			found = true;
        			foundIndex = i;
        			//System.out.println("Found "+foundChild.getID());
        		}

        		i++;

        		
        	}while(i < getGlobal.getStaticChild().size() || found == false);
        	
        	
        	//If found
        	if(found==true) {
        		
        		
        		//Write to file
        		try {
        		getGlobal.getStaticChild().remove(foundIndex);
        			
        		FileOutputStream outStream = new FileOutputStream("Clients.dat");
        		ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
        		
        		//Writes to Parent file
        		for (int count = 0; count < getGlobal.getStaticChild().size(); count++)
      	      	{
        			objectOutputFile.writeObject(getGlobal.getStaticChild().get(count));
      					
      	      	}
        		
        		//Clear form after deletion and refresh list
        		clearModForm();
        		setChildList();
        		
        		//objectOutputFile.writeObject(existingAdmins.get().);
        		objectOutputFile.close();
        		outStream.close();
        		}catch(Exception e) {
        			System.out.println("Trying to overwrite file in Delete function");
        		}
        	}
        	
        	
    	}
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //Initialize runs on scene start
    public void initialize() {
    	//setChildList();
    	
    	filterSearchTest();
    	disableGrant();
    	modDisableGrant();
    	
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
