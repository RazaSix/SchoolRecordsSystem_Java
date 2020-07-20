package login;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import classes.Admin;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class StartViewController {

    @FXML
    private TextField usernameTxtField;

    @FXML
    private PasswordField passwordTxtField;

    @FXML
    private ImageView loginLogoImgView;
    
    @FXML
    private Button loginBtn;
    
    
    private ArrayList<Admin> adminsPassCheck = new ArrayList<Admin>();
    private String adminUsername, adminPassword;
    
    public void login(ActionEvent e) throws IOException{
    	boolean found = false;
    	
    	try {
	    	FileInputStream inStream = new FileInputStream("Admins.dat");
			ObjectInputStream objectInputFile = new ObjectInputStream(inStream);
			
			while(inStream.available() > 0) {
				Admin existAdmin = ((Admin)objectInputFile.readObject());
				adminsPassCheck.add(existAdmin);
				
			}
			
			objectInputFile.close();
			inStream.close();
			
			
			
			int i = 0;
			while (found == false && i < adminsPassCheck.size()) {
				if(adminsPassCheck.get(i).getUsername().equals(usernameTxtField.getText()) && 
						adminsPassCheck.get(i).getPassword().equals(passwordTxtField.getText()))
				{
					found = true;
				}
				//System.out.println("Username: "+adminsPassCheck.get(i).getUsername());
				//System.out.println("Pass    : "+adminsPassCheck.get(i).getPassword());
				i++;
	
			}
		
    	}catch(Exception io) {
    		//io.printStackTrace();
    	}
    	
    	if((usernameTxtField.getText().equals("Admin") && passwordTxtField.getText().equals("Password")) || found == true) {
    		
    		Parent parent = FXMLLoader.load(
  	               getClass().getResource("/controllers/adminPage.fxml")); 
  	      
 	 	      // Build the scene graph.
 	 	      Scene scene = new Scene(parent); 
 	 	
 	 	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
 	 	      // Display our window, using the scene graph.
 	 	      stage.setTitle("Administrator"); 
 	 	      stage.setScene(scene);
 	 	      stage.show(); 
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Wrong Password");
            //alert.initOwner(owner);
            alert.show();
    	}
    }
    
    
    public void enterKeyboard(ActionEvent e) throws IOException {
	    passwordTxtField.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(javafx.scene.input.KeyCode.ENTER))
	            {
	            	try {
						login(e);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        }
	    });
    }
    
    public void initialize() {

    }
	
}
