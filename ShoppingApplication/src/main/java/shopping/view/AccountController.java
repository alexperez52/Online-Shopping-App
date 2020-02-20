package shopping.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import shopping.app.App;
import shopping.model.Invoice;
import shopping.model.Month;
import shopping.model.User;

public class AccountController {

	private App app;
	private Integer[] years = { 2021, 2022, 2023, 2024 };
	ObservableList<Month> monthList = FXCollections.observableArrayList(Month.values());
	ObservableList<Integer> yearList = FXCollections.observableArrayList(years);
	@FXML
	private ComboBox<Month> monthBox;
	@FXML
	private ComboBox<Integer> yearBox;
	@FXML
	private Label streetName, houseNumber, city, state, country, firstName, lastName, username, password, email;

	@FXML
	private ImageView settings, card, paypal;

	@FXML
	private ListView<Invoice> listView;

	@FXML
	private AnchorPane paypalPane;

	@FXML
	private VBox cardVBox;

	@FXML
	private TextField paypalTf, cardTf, holder, code, firstNameTf, lastNameTf, middleNameTf, houseNumberTf,
			streetNameTf, cityTf, stateTf, countryTf, usernameTf, passwordTf;
	@FXML
	private PasswordField paypalPasswordTf;

	public User user;

	private Stage dialogStage;

	public AccountController() {

	}

	public AnchorPane getPaypalPane() {
		return this.paypalPane;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public VBox getCardVBox() {
		return this.cardVBox;
	}

	@FXML
	public void initialize() {

	}

	/**
	 * Performs action on OK button click. Updates the current user with the
	 * information filled in the text fields if they have text in them.
	 * <p>
	 * If the text fields are empty, then the old information will remain. Only
	 * fields which are filled are updated with new information.
	 * 
	 * @see AccountController
	 */
	@FXML
	public void handleInfoUpdate() {

		if (!cityTf.getText().isEmpty()) {
			app.getCurrentUser().getAddress().setCity(cityTf.getText());
			;
		}
		if (!countryTf.getText().isEmpty()) {

			app.getCurrentUser().getAddress().setCountry(countryTf.getText());
		}
		if (!houseNumberTf.getText().isEmpty()) {
			app.getCurrentUser().getAddress().setHouseNumber(houseNumberTf.getText());

		}

		if (!stateTf.getText().isEmpty()) {
			app.getCurrentUser().getAddress().setState(stateTf.getText());

		}
		if (!streetNameTf.getText().isEmpty()) {
			app.getCurrentUser().getAddress().setStreetAddress(streetNameTf.getText());

		}
		if (!firstNameTf.getText().isEmpty()) {
			app.getCurrentUser().getName().setFirstName(firstNameTf.getText());

		}
		if (!middleNameTf.getText().isEmpty()) {
			app.getCurrentUser().getName().setMiddleName(middleNameTf.getText());

		}
		if (!lastNameTf.getText().isEmpty()) {
			app.getCurrentUser().getName().setLastName(lastNameTf.getText());

		}
		if (!usernameTf.getText().isEmpty()) {
			app.getCurrentUser().setUsername(usernameTf.getText());

		}
		if (!passwordTf.getText().isEmpty()) {
			app.getCurrentUser().setPassword(passwordTf.getText());

		}
		app.showAccountPage();
		dialogStage.close();
	}

	/**
	 * Performs action on the Cancel button. Closes the Dialog stage and does not
	 * update any user information.
	 * 
	 * @see AccountController
	 */
	@FXML
	public void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Performs action on two images, card and paypal. Updates information for
	 * payment on image clicked.
	 * <p>
	 * If the paypal logo is clicked, then the card text fields are disabled and
	 * payment method is set to paypal. If the card logo is clicked, then the paypal
	 * text fields are disabled and the payment method is set to card.
	 * 
	 * @see AccountController
	 */
	@FXML
	public void handleUpdate() {
		if (cardVBox.isDisabled()) {

			app.getCurrentUser().getPayment().setIsPaypal(true);
			app.getCurrentUser().getPayment().setIsCard(false);
			app.getCurrentUser().getPayment().setInformation(paypalTf.getText() + "\n" + paypalPasswordTf.getText());
		}
		if (paypalPane.isDisabled()) {
			app.getCurrentUser().getPayment().setIsPaypal(false);
			app.getCurrentUser().getPayment().setIsCard(true);
			app.getCurrentUser().getPayment()
					.setInformation("Card Number: " + cardTf.getText() + "\nExpires: "
							+ monthBox.getSelectionModel().getSelectedIndex() + "/"
							+ yearBox.getSelectionModel().getSelectedItem() + "\nCVV: " + code.getText()
							+ "\nCard Holder: " + holder.getText());

		}

		dialogStage.close();
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Performs action on gear image view. Throws a dialog stage which
	 * prompts entry of text fields.
	 * 
	 * 
	 * @see AccountController
	 */
	@FXML
	private void setUserInfo() {
		app.showInfoEdit();
	}
	
	/**
	 * Performs action on paypal image. If image is clicked then card
	 * options are disabled.
	 * 
	 * 
	 * @see AccountController
	 */
	@FXML
	private void setPaypalInfo() {
		app.showPaypalEdit();
	}
	
	/**
	 * Performs action on card image. If image is clicked then 
	 * paypal options are disabled.
	 * 
	 * 
	 * @see AccountController
	 */
	@FXML
	private void setCardInfo() {

		app.showCardEdit();
	}

	public void setApp(App app) {

		this.app = app;
		user = app.getCurrentUser();

	}

	/**
	 *	Initializes combo boxes with parameters months and years.
	 * 
	 * 
	 * @see AccountController
	 */
	public void initComboBoxes() {
		monthBox.setItems(monthList);
		yearBox.setItems(yearList);

	}
	
	/**
	 * Displays User's information on labels and user's invoice
	 * logs on table view.
	 * 
	 * 
	 * @see AccountController
	 */
	public void showInfo() {
		streetName.setText(user.getAddress().getStreetAddress());
		houseNumber.setText(user.getAddress().getHouseNumber());
		city.setText(user.getAddress().getCity());
		state.setText(user.getAddress().getState());
		country.setText(user.getAddress().getCountry());

		firstName.setText(user.getName().getFirstName());
		lastName.setText(user.getName().getLastName());
		username.setText(user.getUsername());
		password.setText(user.getPassword());
		email.setText(user.getEmail());

		ObservableList<Invoice> items = FXCollections.observableArrayList(user.getInvoiceLog().values());
		listView.getItems().setAll(items);
	}

}
