package shopping.view;

import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import shopping.app.App;
import shopping.model.Invoice;
import shopping.model.Item;

public class AccountController {

	private App app;

	@FXML
	private Label streetName, houseNumber, city, state, country, firstName, lastName, username, password, email;

	@FXML
	private ListView<Invoice> listView;
	
	
	

	
	public void setApp(App app) {
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
