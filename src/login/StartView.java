package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartView extends Application{

	
	
	
public void start(Stage stage) throws Exception {
		
		// Load the FXML file.
	      Parent parent = FXMLLoader.load(
	               getClass().getResource("loginpage.fxml")); 
	      
	      // Build the scene graph.
	      Scene scene = new Scene(parent); 
	      
	      // Display our window, using the scene graph.
	      stage.setTitle("Eldean Childcare- Login"); 
	      stage.setScene(scene);
	      stage.setResizable(false);
	      stage.show();
	      stage.centerOnScreen();
		
	}

	public static void main(String[] args) {
		launch(args);

	}

}
