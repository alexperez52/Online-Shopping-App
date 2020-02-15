package shopping.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import shopping.app.App;

public class RegisterController {
	private App app;
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

	public void setApp(App app) { // gives controller access to databases
		this.app = app;
	}

	
}
