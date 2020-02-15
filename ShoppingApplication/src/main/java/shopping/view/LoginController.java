package shopping.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import shopping.app.App;
import shopping.model.UserBag;

public class LoginController {
	private App app;
	@FXML
	private TextField username;
	@FXML
	private TextField password;

	public LoginController() {

	}

	public void setApp(App app) { // gives controller access to databases
		this.app = app;
	}

	@FXML
	private void handleLogin() {// gives login button function
		if (username.getText().isEmpty() || password.getText().isEmpty()) { // if fields are empty and clicked
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Invalid Input");
			alert.setHeaderText("Username/Password Field(s) empty");
			alert.setContentText("Please enter fields properly");

			alert.showAndWait();
		}

		else if (app.getLiveUserBag().getUsers().containsKey(username.getText()) && app.getLiveUserBag().getUsers()
				.get(username.getText().trim()).getPassword().contentEquals(password.getText().trim())) {
			// if username and password correct
			app.setCurrentUser(app.getLiveUserBag().getUsers().get(username.getText()));
			app.showCatalogPage();
		}

		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Account Not Found");
			alert.setHeaderText("Username/Password is incorrect");
			alert.setContentText("Please enter fields properly");

			alert.showAndWait();
		}

	}
	
	@FXML
	private void handleRegister() {
		app.showRegisterPage();
	}

}
