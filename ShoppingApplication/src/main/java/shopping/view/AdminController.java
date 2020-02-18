package shopping.view;

import java.util.ArrayList;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import shopping.app.App;
import shopping.model.Invoice;
import shopping.model.User;

public class AdminController {

	private App app;
	private User user;
	@FXML 
	ListView<User> listView;
	
	@FXML 
	private Label fullName, address, username,password, email,payment;
	
	
	@FXML
	private void initialize() {

		listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			
			displayUserInformation(newValue);
		});
	}
	
	public void setApp(App app) {
		
	
		this.app = app;
		
		
	}
	
	@FXML 
	private void handleNew() {
		
		app.showRegisterPage();
		
	}
	
	@FXML
	private void handleInfo() {
		
		user = listView.getSelectionModel().getSelectedItem();
		
		
		app.showAccountPageAdmin(user);
		
		
		
	}
	
	@FXML 
	private void handleDelete() {
		user = listView.getSelectionModel().getSelectedItem();
		app.getLiveUserBag().getUsers().remove(user.getUsername());
		app.showAdminPage();

	}
	
	public void updateTable() {
		ObservableList<User> items = FXCollections.observableArrayList(app.getLiveUserBag().getUsers().values());
		
		listView.getItems().removeAll(items);
		listView.getItems().setAll(items);
		
	}
	
	private void displayUserInformation(User user) {
		fullName.setText(user.getName().getFullName());
		address.setText(user.getAddress().getFullAddress());
		username.setText(user.getUsername());
		password.setText(user.getPassword());
		email.setText(user.getEmail());
		
		
		
		
		if(user.getPayment() ==null) {
			payment.setText("No payment Method");
		}
		else {
		payment.setText(user.getPayment().getInformation());
		}

		
		
		
	}

}
