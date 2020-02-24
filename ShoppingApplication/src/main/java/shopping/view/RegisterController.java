package shopping.view;

import java.awt.Desktop;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import shopping.app.App;
import shopping.model.Address;
import shopping.model.Name;
import shopping.model.User;

public class RegisterController {
	private App app;
	private boolean readTerms;
	@FXML
	private TextField firstName;
	@FXML
	private TextField middleName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField email;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private TextField confirmPassword;
	@FXML
	private TextField streetName;
	@FXML
	private TextField houseNumber;
	@FXML
	private TextField city;
	@FXML
	private TextField state;
	@FXML
	private TextField country;

	@FXML
	private RadioButton readTermBtn;

	public RegisterController() {

	}

	public void setApp(App app) { // gives controller access to databases
		this.app = app;

		readTermBtn.setOnAction(e -> {
			readTerms = true;
		});
	}

	/**
	 * Performs action on Register Button click. If no user input exception is
	 * called, the program creates a new User object and adds it to the UserBag.
	 * 
	 * @see LoginController
	 */
	@FXML
	private void handleRegister() { // will take info and make user
		if (fieldCheck()) {
			Name name = new Name(firstName.getText().trim(), middleName.getText().trim(), lastName.getText().trim());
			Address address = new Address(houseNumber.getText(), streetName.getText(), city.getText(), state.getText(),
					country.getText());
			User newUser = new User(name, address, username.getText(), password.getText(), email.getText());
			app.getLiveUserBag().getUsers().put(newUser.getUsername(), newUser);
			firstName.clear();
			middleName.clear();
			lastName.clear();
			email.clear();
			username.clear();
			password.clear();
			confirmPassword.clear();
			streetName.clear();
			houseNumber.clear();
			city.clear();
			state.clear();
			country.clear();

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

			alert.setTitle("Account Created");
			alert.setHeaderText("Success!");
			alert.setContentText("");

			alert.showAndWait();

			if(app.getCurrentUser() ==null) {
				app.showLoginPage();

			}
			else {
				
				app.showAdminPage();

			}
		}

	}

	/**
	 * Performs action on Login Hyperlink click. Calls app.showLoginPage().
	 * 
	 * @see LoginController
	 */
	public void handleLogInHyperlink() {
		app.showLoginPage();
	}

	/**
	 * Checks all available RegisterPage fields and checks if improper user input
	 * exists. Only returns true if user information is correct.
	 * 
	 * @return isProperInformation boolean value returning true if fields are
	 *         acceptable, false if user input exception is caught
	 * @see RegisterController
	 */
	public boolean fieldCheck() {// checks inefficient field data

		if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || email.getText().isEmpty()
				|| username.getText().isEmpty() || password.getText().isEmpty() || confirmPassword.getText().isEmpty()
				|| streetName.getText().isEmpty() || houseNumber.getText().isEmpty() || city.getText().isEmpty()
				|| state.getText().isEmpty() || country.getText().isEmpty()) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

			alert.setTitle("Error");
			alert.setHeaderText("Issues with certain field(s)");
			alert.setContentText("Please fill each field");

			alert.showAndWait();
			return false;
		}
		if (firstName.getText().matches(".*\\d.*") || middleName.getText().matches(".*\\d.*")
				|| lastName.getText().matches(".*\\d.*") || city.getText().matches(".*\\d.*")
				|| state.getText().matches(".*\\d.*") || country.getText().matches(".*\\d.*")) { // handles numbers in
																									// inappropriate
																									// fields
			Alert alert = new Alert(AlertType.WARNING);
			alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

			alert.setTitle("Error");
			alert.setHeaderText("Issues with certain field(s)");
			alert.setContentText("Numbers in an inappropriate field!");

			alert.showAndWait();
			return false;
		}

		if (!readTerms) { // user must agree to terms
			Alert alert = new Alert(AlertType.WARNING);
			alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

			alert.setTitle("Error");
			alert.setHeaderText("Issues with certain field(s)");
			alert.setContentText("Please agree to terms and conditions!");

			alert.showAndWait();
			return false;
		}
		if (houseNumber.getText().matches(".*\\D.*")) {// house number must be a number
			Alert alert = new Alert(AlertType.WARNING);
			alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

			alert.setTitle("Error");
			alert.setHeaderText("Issues with certain field(s)");
			alert.setContentText("Please input a number for house number!");

			alert.showAndWait();
			return false;
		}
		if (!(password.getText().contentEquals(confirmPassword.getText()))) {// password fields must match
			Alert alert = new Alert(AlertType.WARNING);
			alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

			alert.setTitle("Error");
			alert.setHeaderText("Issues with certain field(s)");
			alert.setContentText("Passwords must match!");

			alert.showAndWait();
			return false;
		}

		if (app.getLiveUserBag().getUsers().containsKey(username.getText().trim())) {// if username exists already,
																						// can't register
			Alert alert = new Alert(AlertType.WARNING);
			alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

			alert.setTitle("Error");
			alert.setHeaderText("Issues with certain field(s)");
			alert.setContentText("Username is taken!");

			alert.showAndWait();
			return false;
		}

		if (!(email.getText().matches("([\\w]+@[\\w]+[.][\\w]+)+"))) {// if username exists already, can't register
			Alert alert = new Alert(AlertType.WARNING);
			alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

			alert.setTitle("Error");
			alert.setHeaderText("Issues with certain field(s)");
			alert.setContentText("Please use valid email!");

			alert.showAndWait();
			return false;
		}

		return true;
	}

	public void handleTermsAndAgreements() {
		Stage mediaStage = new Stage();
		
		String path = "src/main/java/images/rick.mp4";

		Media media = new Media(new File(path).toURI().toString());

		MediaPlayer mediaPlayer = new MediaPlayer(media);

		MediaView mediaView = new MediaView(mediaPlayer);

		mediaPlayer.setAutoPlay(true);
		
		Group root = new Group();  
        root.getChildren().add(mediaView);  
        Scene scene = new Scene(root,500,400);  
        mediaStage.setScene(scene);  
        mediaStage.show();
        mediaStage.setOnCloseRequest(new EventHandler<WindowEvent>() { // saves bags on exit
			public void handle(WindowEvent we) {
				mediaPlayer.stop();
			}
        	
        });


	}
}
