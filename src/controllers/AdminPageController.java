package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Optional;

import classes.Global;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminPageController {

	
    @FXML
    private Button loutoutBtn;

    @FXML
    private Button clientsPgBtn;

    @FXML
    private Button adminPgBtn;

    @FXML
    private Button sessionsPgBtn;
    
    @FXML
    private Button invoiceBtn;
    
    
    Global setGlobalVals = new Global();
   // ArrayList<classes.Parent> setGlobalParent = new ArrayList<classes.Parent>();
    
    
    public void loadChildrenGlobal() {
    	
    	try 
    	{
    		//Clear global (Should be unnecessary because blank on program start but maybe multiple logins)
    		setGlobalVals.clearStaticChild();
    		
    		//Check if file exists
    		FileInputStream inStream = new FileInputStream("Clients.dat");
    		ObjectInputStream objectInputFile = new ObjectInputStream(inStream);
    		
    		//Read existing children into global static variable
    		while(inStream.available() > 0) {
    			classes.Child currChildren = ((classes.Child)objectInputFile.readObject());
    			
    			setGlobalVals.setStaticChild(currChildren);
    		}
    		
    		objectInputFile.close();
    		inStream.close();
    		
    	}catch(Exception e) 
    	{
    		//e.printStackTrace();
    		
    	}
    	
    	
    }
    
    
    public void loadTermGlobal() {
    	//Load Term into global

    		setGlobalVals.clearStaticTerm();
    		
    		try {
	    		//Check if file exists
	    		FileInputStream inStream = new FileInputStream("Terms.dat");
	    		ObjectInputStream objectInputFile = new ObjectInputStream(inStream);
	    		
	    		//Read existing Terms into global static variable
	    		// Goes through object by object
	    		while(inStream.available() > 0) {
	    			classes.Term currTerms = ((classes.Term)objectInputFile.readObject());
	    			
	    			setGlobalVals.setStaticTerm(currTerms);
	    		}
	    		
	    		objectInputFile.close();
	    		inStream.close();
    		
    		}catch(FileNotFoundException f) {
    			//f.printStackTrace();
    		}
    		catch(IOException io) {
    			//io.printStackTrace();
    		}
    		catch (ClassNotFoundException e) {
				//e.printStackTrace();
			}
    		

    }
    
    public void loadBalanceGlobal() {
    	setGlobalVals.getStaticBalance().clear();
    	
    	for(int i = 0; i< setGlobalVals.getStaticTerm().size(); i++) {
    		setGlobalVals.getStaticTerm().get(i).getTermBalance().setChildList(setGlobalVals.getStaticChild());
    		setGlobalVals.setStaticBalance(setGlobalVals.getStaticTerm().get(i).getTermBalance());
    	}
    	
    }
    
    
    
    //Opens Add Clients Registration Page
    public void clientsPg(ActionEvent e) throws  IOException{
    	Parent parent = FXMLLoader.load(
	               getClass().getResource("ClientRegistration.fxml")); 
	      
	 	      // Build the scene graph.
	 	      Scene scene = new Scene(parent); 
	 	
	 	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	 	      // Display our window, using the scene graph.
	 	      stage.setTitle("Registration"); 
	 	      stage.setScene(scene);
	 	      stage.setResizable(false);
	 	      stage.show();
	 	      stage.centerOnScreen();
 	
    }
    
    //Opens Add Admins Page
    public void adminPg(ActionEvent e) throws  IOException{
    	Parent parent = FXMLLoader.load(
      	               getClass().getResource("addAdmin.fxml")); 
      	      
     	 	      // Build the scene graph.
     	 	      Scene scene = new Scene(parent); 
     	 	
     	 	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
     	 	      // Display our window, using the scene graph.
     	 	      stage.setTitle("Add Administrators"); 
     	 	      stage.setScene(scene);
     	 	      stage.setResizable(false);
     	 	      stage.show();
     	 	      stage.centerOnScreen();
        	
    }
    
    
    //Opens Sessions Page
    public void sessionsPg(ActionEvent e) throws  IOException{
    	Parent parent = FXMLLoader.load(
	               getClass().getResource("sessionPage.fxml")); 
	      
	 	      // Build the scene graph.
	 	      Scene scene = new Scene(parent); 
	 	
	 	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	 	      // Display our window, using the scene graph.
	 	      stage.setTitle("Sessions"); 
	 	      stage.setScene(scene);
	 	      stage.setResizable(false);
	 	      stage.show();
	 	      stage.centerOnScreen();
	
 }
    
    
    //Opens Invoice Page
    public void invoicesPg(ActionEvent e) throws  IOException{
    	Parent parent = FXMLLoader.load(
	               getClass().getResource("invoicePage.fxml")); 
	      
	 	      // Build the scene graph.
	 	      Scene scene = new Scene(parent); 
	 	
	 	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	 	      // Display our window, using the scene graph.
	 	      stage.setTitle("View Invoices"); 
	 	      stage.setScene(scene);
	 	      stage.setResizable(false);
	 	      stage.show();
	 	      stage.centerOnScreen();
	
 }
    
    //Initialise- Runs on opening the scene
    public void initialize() {
    	loadChildrenGlobal();
    	loadTermGlobal();
    	loadBalanceGlobal();
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
