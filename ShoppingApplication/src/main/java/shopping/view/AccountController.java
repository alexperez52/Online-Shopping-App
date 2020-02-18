package shopping.view;

import java.util.TreeMap;

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
import shopping.model.Item;
import shopping.model.Month;
import shopping.model.Payment;
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
	private TextField paypalTf, cardTf, holder, code,
			firstNameTf, lastNameTf, middleNameTf, houseNumberTf, streetNameTf, cityTf, stateTf, countryTf, usernameTf, passwordTf;
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
	
	
	@FXML
	public void handleInfoUpdate() {
		app.getCurrentUser().getAddress().setCity(cityTf.getText());;
		app.getCurrentUser().getAddress().setCountry(countryTf.getText());
		app.getCurrentUser().getAddress().setHouseNumber(houseNumberTf.getText());
		app.getCurrentUser().getAddress().setState(stateTf.getText());
		app.getCurrentUser().getAddress().setStreetAddress(streetNameTf.getText());
		app.getCurrentUser().getName().setFirstName(firstNameTf.getText());
		app.getCurrentUser().getName().setMiddleName(middleNameTf.getText());
		app.getCurrentUser().getName().setLastName(lastNameTf.getText());
		app.getCurrentUser().setUsername(usernameTf.getText());
		app.getCurrentUser().setPassword(passwordTf.getText());
		app.showAccountPage();
		dialogStage.close();
	}
	
	@FXML
	public void handleCancel() {
		dialogStage.close();
	}
	
	
	@FXML
	public void handleUpdate() {
		if(cardVBox.isDisabled()) {
			
			app.getCurrentUser().getPayment().setIsPaypal(true);
			app.getCurrentUser().getPayment().setIsCard(false);
			app.getCurrentUser().getPayment().setInformation(paypalTf.getText() + "\n" + paypalPasswordTf.getText());
			System.out.println("update paypal");
		}
		if(paypalPane.isDisabled()) {
			app.getCurrentUser().getPayment().setIsPaypal(false);
			app.getCurrentUser().getPayment().setIsCard(true);
			app.getCurrentUser().getPayment().setInformation("Card Number: " + cardTf.getText() + "\nExpires: "
					+ monthBox.getSelectionModel().getSelectedIndex() + "/"
					+ yearBox.getSelectionModel().getSelectedItem() + "\nCVV: " + code.getText()
					+ "\nCard Holder: " + holder.getText());
			
			System.out.println("update card");
		}
		
		dialogStage.close();
	}


	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	
	@FXML
	private void setUserInfo() {
		app.showInfoEdit();
	}
	
	@FXML
	private void setPaypalInfo() {
		app.showPaypalEdit();
	}
	

	@FXML
	private void setCardInfo() {

		app.showCardEdit();
	}
	

	
	public void setApp(App app) {
	
		this.app = app;
		user = app.getCurrentUser();
		
		
	}
	
	public void initComboBoxes() {
		monthBox.setItems(monthList);
		yearBox.setItems(yearList);

	}

	public void showInfo() {
		// TODO Auto-generated method stub
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
