package shopping.view;

import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import shopping.app.App;
import shopping.model.Invoice;
import shopping.model.Item;
import shopping.model.Payment;
import shopping.model.User;

public class AccountController {

	private App app;

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

	private Stage dialogStage;
	
	public AccountController() {
		
	}
	public AnchorPane getPaypalPane() {
		return this.paypalPane;
	}
	
	
	public VBox getCardVBox() {
		return this.cardVBox;
	}
	
	@FXML
	  public void initialize() {
	
	}
	


	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
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
		
		
	}

	public void showInfo() {
		// TODO Auto-generated method stub
		streetName.setText(app.getCurrentUser().getAddress().getStreetAddress());
		houseNumber.setText(app.getCurrentUser().getAddress().getHouseNumber());
		city.setText(app.getCurrentUser().getAddress().getCity());
		state.setText(app.getCurrentUser().getAddress().getState());
		country.setText(app.getCurrentUser().getAddress().getCountry());
		
		firstName.setText(app.getCurrentUser().getName().getFirstName());
		lastName.setText(app.getCurrentUser().getName().getLastName());
		username.setText(app.getCurrentUser().getUsername());
		password.setText(app.getCurrentUser().getPassword());
		email.setText(app.getCurrentUser().getEmail());
		ObservableList<Invoice> items = FXCollections.observableArrayList(app.getCurrentUser().getInvoiceLog().values());
		listView.getItems().setAll(items);
	}




}
