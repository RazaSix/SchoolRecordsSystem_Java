package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import classes.Admin;
import classes.Global;





public class AddAdminController {

	
    @FXML
    private Button logoutBtn;

    @FXML
    private Button backToAdminBtn;

    @FXML
    private ListView<String> existingAdminsList;

    @FXML
    private Button adminDetailsBtn;

    @FXML
    private TextField fNameTxtField;

    @FXML
    private TextField emailTxtField;

    @FXML
    private PasswordField passwordTxtField;

    @FXML
    private PasswordField confirmPassTxtField;

    @FXML
    private TextField sNameTxtField;

    @FXML
    private TextArea addressTxtArea;

    @FXML
    private DatePicker dobField;

    @FXML
    private Button addAdminBtn;
    
    @FXML
    private Button clearTxtFieldsBtn;
    
    private ArrayList<Admin> existingAdmins = new ArrayList<Admin>();
    
    Global globalmethods = new Global();
    
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *Add Admin 
     !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    
    //confirmPassword()
    
    //checkEmail()??
    
    //IDGenerator	(Gets IDs from file and adds one)
    
    public void clearTxtFields() {
		fNameTxtField.clear();
		sNameTxtField.clear();
		addressTxtArea.clear();
		dobField.setValue(null);
		emailTxtField.clear();
		passwordTxtField.clear();
		confirmPassTxtField.clear();
    }
    
    public void addAdmin() {
    	//Example data for inserts
    	//Admin adminAdd = new Admin(111,"Jack","Smith","123 Round Road, Northampton, NN11 8RH","12/03/1988","smith@email.com","test");
    	
    	//Test collection of data from form
    	//Admin adminAdd = new Admin(111, fNameTxtField.getText(), sNameTxtField.getText(),addressTxtArea.getText(),
    			//dobTxtField.getText(),emailTxtField.getText(),passwordTxtField.getText());
    	
    	if(
    			fNameTxtField.getText() == null ||
    			sNameTxtField.getText() == null ||
    			addressTxtArea.getText() == null ||
    			dobField.getValue() == null ||
    			emailTxtField.getText() == null ||
    			passwordTxtField.getText() == null ||
    			confirmPassTxtField.getText() == null
    			) 
    	{
    		globalmethods.formIncomplete();
    	}
    	else if(!(passwordTxtField.getText().equals(confirmPassTxtField.getText()))) {
        	Alert alert = new Alert(AlertType.WARNING);
        	alert.setTitle("Password Error");
        	alert.setContentText("Make sure passwords match");


        	ButtonType buttonTypeOk = new ButtonType("Ok");
        	alert.getButtonTypes().setAll(buttonTypeOk);
        	
        	Optional<ButtonType> result = alert.showAndWait();
        	if (result.get() == buttonTypeOk){
        		//do nothing
        	}
    	}
    	else {
    		//Confirmation pop up
        	Alert alert = new Alert(AlertType.CONFIRMATION);
        	alert.setTitle("Add Admin?");
        	alert.setContentText("Are you sure you want add an Administrator?");

        	//Two options on the pop up
        	ButtonType buttonTypeYes = new ButtonType("Yes");
        	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        	alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
        	
        	Optional<ButtonType> result = alert.showAndWait();

        	//If user clicks yes
        	if (result.get() == buttonTypeYes){
        		
        		try 
            	{
        			//existingAdmins.clear();
            		//Check if file exists
            		FileInputStream inStream = new FileInputStream("Admins.dat");
            		ObjectInputStream objectInputFile = new ObjectInputStream(inStream);
            		
            		//Read all objects one by one and add them to the local Adminslist
            		while(inStream.available() > 0) {
            			Admin existAdmin = ((Admin)objectInputFile.readObject());
            			existingAdmins.add(existAdmin);
            			
            		}
            		
            		//Gets latest ID from adminslist and adds 1
            		int newID = existingAdmins.get(existingAdmins.size()-1).getID()+1;
            		
            		//Gets info from form
            		Admin adminAdd = new Admin(newID, fNameTxtField.getText(), sNameTxtField.getText(),addressTxtArea.getText(),
        	    			dobField.getValue(),emailTxtField.getText(),passwordTxtField.getText());
            		
            		//Add Admin info to Arraylist of all Admins (In this case empty as this sections runs only the first time)
            		existingAdmins.add(adminAdd);
            		
            		
            		FileOutputStream outStream = new FileOutputStream("Admins.dat");
            		ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
            		
            		//Cycles through the existing admins in the arraylist and writes them to file
            		for (int i = 0; i < existingAdmins.size(); i++)
          	      	{
            			objectOutputFile.writeObject(existingAdmins.get(i));
          					
          	      	}
            		
            		
            		existingAdmins.clear();
            		
            		objectInputFile.close();
            		inStream.close();
            		
            		objectOutputFile.close();
            		outStream.close();
            		
            	}catch(Exception e) 
            	{	//If file does not exist
            		System.out.println("File doesn't exist");
            		
            		//Create the file
            		try {
            			
            			//Generates new ID for the admin
            			Random rand = new Random();
            	    	int firstID = 10000001;
            			
            	    	Admin adminAdd = new Admin(firstID, fNameTxtField.getText(), sNameTxtField.getText(),addressTxtArea.getText(),
            	    			dobField.getValue(),emailTxtField.getText(),passwordTxtField.getText());
            			
            			existingAdmins.add(adminAdd);
            			
            			FileOutputStream outStream = new FileOutputStream("Admins.dat");
                		ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
                		
                		//System.out.println(existingAdmins.size());
                		for (int i = 0; i < existingAdmins.size(); i++)
                	      {
                			objectOutputFile.writeObject(existingAdmins.get(i));
                	      }
                		
                		existingAdmins.clear();
                		//objectOutputFile.writeObject(existingAdmins.get().);
                		objectOutputFile.close();
                		outStream.close();
                		System.out.println("Created new file");
                		
                		
            		}catch(IOException ioe) {
            			System.out.println("Error creating file");
            			ioe.printStackTrace();
            		}
            		
            	}
        		
        		
        		//Clears form after insert
        		clearTxtFields();
        		viewAdmins();
        		
        		
        	} else {
        	    // Do nothing
        		//"Else clause" of pop up confirmation window of adding new admin which does nothing because user clicked cancel
        	}
    	}
    	
    	
    	
    }
    
    public void deleteSelectedAdmin() {
    	//Get index from listview
    	//Arraylist read from admin file
    	//Remove admin at index
    	//Write back to file
    	
    	if(existingAdminsList.getSelectionModel().getSelectedIndex() < 0) {
    		
    	}
    	else {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
        	alert.setTitle("Remove Child Session");
        	alert.setContentText("Are you sure you want to remove selected child?");


        	ButtonType buttonTypeYes = new ButtonType("Yes");
        	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        	alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
        	
        	Optional<ButtonType> result = alert.showAndWait();

        	if (result.get() == buttonTypeYes){
        		int selectedListIndex = existingAdminsList.getSelectionModel().getSelectedIndex();
        		
        		try 
            	{
        			existingAdmins.clear();
    	    		FileInputStream inStream = new FileInputStream("Admins.dat");
    	    		ObjectInputStream objectInputFile = new ObjectInputStream(inStream);
    	    		
    	    		//Read all objects one by one and add them to the local Adminslist
    	    		while(inStream.available() > 0) {
    	    			Admin existAdmin = ((Admin)objectInputFile.readObject());
    	    			existingAdmins.add(existAdmin);
    	    			
    	    		}
    	    		
    	    		existingAdmins.remove(selectedListIndex);
    	    		
    	    		FileOutputStream outStream = new FileOutputStream("Admins.dat");
    	    		ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
    	    		
    	    		//System.out.println(existingAdmins.size());
    	    		for (int i = 0; i < existingAdmins.size(); i++)
    	    	      {
    	    			objectOutputFile.writeObject(existingAdmins.get(i));
    	    	      }
    	    		
    	    		existingAdmins.clear();
    	    		
    	    		viewAdmins();
    	    		//objectOutputFile.writeObject(existingAdmins.get().);
    	    		objectOutputFile.close();
    	    		outStream.close();
    	    		System.out.println("Overwritten File");
            	}
        		catch(IOException io) {}
        		catch(ClassNotFoundException cn) {}

        	}
    	}
    	
    }
    
    
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *View Admins
     !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    public void initialize() {
    	//System.out.println("Initialize");
    	viewAdmins();
    }
    
    //After highlighting an Admin click to view pop up with Admin's info
    public void viewAdmins() {
    	try 
    	{
    		
    		//Check if file exists
    		FileInputStream inStream = new FileInputStream("Admins.dat");
    		ObjectInputStream objectInputFile = new ObjectInputStream(inStream);
    		
    		//List populated with names from file for display
    		ObservableList<String> names = FXCollections.observableArrayList();
    		
    		
    		while(inStream.available() > 0) {
    			Admin existAdmin = ((Admin)objectInputFile.readObject());
    			names.add(existAdmin.getID()+","+existAdmin.getFname()+" "+existAdmin.getSname());
    			
    		}
    		
    		
    		existingAdminsList.setItems(names);
    		
    		objectInputFile.close();
    		inStream.close();
    		
    	}catch(Exception e) 
    	{
    		System.out.println("Cannot populate list-Should only occur on first run with non existent file. Expected");
    		try {
				FileOutputStream outStream = new FileOutputStream("Admins.dat");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		//e.printStackTrace();
    		
    	}
    }
    
    
    //Return to Admin Control Page
    public void backtoAdmin(ActionEvent e) throws  IOException{
    	globalmethods.backtoAdmin(e);
 	
    }
    
    
    //Logout
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
