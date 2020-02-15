package shopping.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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

	@FXML
	private void handleRegister() {  //will take info and make user
		if (fieldCheck()) {
			Name name = new Name(firstName.getText(), middleName.getText(), lastName.getText());
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
			alert.setTitle("Account Created");
			alert.setHeaderText("Success!");
			alert.setContentText("");

			alert.showAndWait();

		}
		
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("Issues with certain field(s)");
			alert.setContentText("Please correct");

			alert.showAndWait();
		}
	}

	public boolean fieldCheck() {//checks inefficient field data
		if (firstName.getText().isEmpty() ||lastName.getText().isEmpty()
				|| email.getText().isEmpty() || username.getText().isEmpty() || password.getText().isEmpty()
				|| confirmPassword.getText().isEmpty() || streetName.getText().isEmpty()
				|| houseNumber.getText().isEmpty() || city.getText().isEmpty() || state.getText().isEmpty()
				|| country.getText().isEmpty()) {
			return false;
		}
		if (!(city.getText().split("([\\d]+)")[0].contentEquals(city.getText()))
				|| !(state.getText().split("([\\d]+)")[0].contentEquals(state.getText()))
				|| !(country.getText().split("([\\d]+)")[0].contentEquals(country.getText()))
				|| !(firstName.getText().split("([\\d]+)")[0].contentEquals(firstName.getText()))
				|| !(middleName.getText().split("([\\d]+)")[0].contentEquals(middleName.getText()))
				|| !(lastName.getText().split("([\\d]+)")[0].contentEquals(lastName.getText()))) {
			return false;
		}

		if (!readTerms) {
			return false;
		}
		if (!Character.isDigit(houseNumber.getText().charAt(0))) {
			return false;
		}
		if (!(password.getText().contentEquals(confirmPassword.getText()))) {
			return false;
		}

		if (app.getLiveUserBag().getUsers().containsKey(username.getText())) {
			return false;
		}

		return true;
	}
}
