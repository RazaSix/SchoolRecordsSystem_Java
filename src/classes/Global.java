package classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;

public class Global {
	
	//Static lists of Term/Session
	private static ArrayList<Term> staticTerm = new ArrayList<Term>();
	private static ArrayList<Session> staticSession = new ArrayList<Session>();
	
	//Static List of Children stays the same throughout the program
	private static ArrayList<classes.Child> staticChild = new ArrayList<classes.Child>();
	
	//Static list of Balance Info
	private static ArrayList<classes.Balance> staticBalance = new ArrayList<classes.Balance>();
	
	//Setters
	
	//Add child to global
	public void setStaticChild(classes.Child child) {
		Global.staticChild.add(child);
	}
	
	//Clear global child list
	public void clearStaticChild() {
		Global.staticChild.clear();
	}
	
	//Add term to global term
	public void setStaticTerm(Term term) {
		Global.staticTerm.add(term);
	}
	
	public void clearStaticTerm() {
		Global.staticTerm.clear();
	}
	
	public void setStaticSession(Session session) {
		Global.staticSession.add(session);
	}
	
	public void setStaticBalance(Balance staticBalance) {
		Global.staticBalance.add(staticBalance);
	}
	
	//Getter
	public ArrayList<classes.Child> getStaticChild(){
		return staticChild;
	}	

	public ArrayList<Term> getStaticTerm() {
		return staticTerm;
	}

	public ArrayList<Session> getStaticSession() {
		return staticSession;
	}

	public ArrayList<Balance> getStaticBalance() {
		return staticBalance;
	}


	
	
	//Validate Incomplete Form Warning Popup
	public void formIncomplete() {
    	Alert alert = new Alert(AlertType.WARNING);
    	alert.setTitle("Form Incomplete");
    	alert.setContentText("Please complete the form before submitting");


    	ButtonType buttonTypeOk = new ButtonType("Ok");
    	alert.getButtonTypes().setAll(buttonTypeOk);
    	
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == buttonTypeOk){
    		//do nothing
    	}
	}
	
	//Date error and order warning popup
	public void dateError() {
		Alert alert = new Alert(AlertType.WARNING);
    	alert.setTitle("Date Error");
    	alert.setContentText("Your input date already exists in the system or \nYour start date is after the end date");


    	ButtonType buttonTypeOk = new ButtonType("Ok");
    	alert.getButtonTypes().setAll(buttonTypeOk);
    	
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == buttonTypeOk){
    		//do nothing
    	}
	}
	
	//Session duplicate child check for already booked child
	
	//Check child does not already exist in the session
	public boolean childDupliCheck(ArrayList<Child> childSessionList, int childID) {
		boolean duplicate = false;
		
		int count = 0;
		
		try {
			do {
				//System.out.println("Form childID: "+childID+" -- Session childID: "+childSessionList.get(count).getID());
				if(childID == childSessionList.get(count).getID()) {
					duplicate = true;
				}
				count++;
			}while(count < childSessionList.size() && duplicate == false);
		}catch(Exception e) {
			duplicate = false;
		}
		return duplicate;
	}
	
	public void alreadyBookedChildPopUp() {
		Alert alert1 = new Alert(AlertType.WARNING);
    	alert1.setTitle("Already Booked");
    	
    	alert1.setContentText("Child already booked in this session");
    	ButtonType buttonTypeOK = new ButtonType("OK", ButtonData.CANCEL_CLOSE);
    	alert1.getButtonTypes().setAll(buttonTypeOK);
    	Optional<ButtonType> result1 = alert1.showAndWait();
    	if (result1.get() == buttonTypeOK){
    		//do nothing
    	}
	}
	
    //Return to Admin Control Page
    public void backtoAdmin(ActionEvent e) throws  IOException{
    	Parent parent = FXMLLoader.load(
	               getClass().getResource("/controllers/adminPage.fxml")); 
	      
	 	      // Build the scene graph.
	 	      Scene scene = new Scene(parent); 
	 	
	 	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	 	      // Display our window, using the scene graph.
	 	      stage.setTitle("Administrator"); 
	 	      stage.setScene(scene);
		      stage.setResizable(false);
		      stage.show();
		      stage.centerOnScreen();
 	
    }
	
	public void logout(ActionEvent e) throws IOException{
	    	
	    	Alert alert = new Alert(AlertType.WARNING);
	    	alert.setTitle("Logout?");
	    	alert.setContentText("Are you sure you want to logout?");


	    	ButtonType buttonTypeYes = new ButtonType("Yes");
	    	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	    	alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
	    	
	    	Optional<ButtonType> result = alert.showAndWait();
	    	if (result.get() == buttonTypeYes){
	    		Parent parent = FXMLLoader.load(
	   	               getClass().getResource("../login/loginpage.fxml")); 
	   	      
	  	 	      // Build the scene graph.
	  	 	      Scene scene = new Scene(parent); 
	  	 	
	  	 	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	  	 	      // Display our window, using the scene graph.
	  	 	      stage.setTitle("Administrator"); 
	  	 	      stage.setScene(scene);
			      stage.setResizable(false);
			      stage.show();
			      stage.centerOnScreen();
	    	} else {
	    	    // Do nothing
	    	}
	    	
	    }




}
