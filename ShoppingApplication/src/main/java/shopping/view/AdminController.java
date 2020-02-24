package shopping.view;

import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import shopping.app.App;
import shopping.model.User;

public class AdminController {
	private App app;
	private ObservableList<User> items;
	private User user;
	@FXML 
	ListView<User> listView;
	
	@FXML 
	private TextField searchField;
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
	/**
	 * Performs action on the search Function.
	 * Updates users based on user names typed.
	 * 
	 * 
	 * @see AdminController
	 */
	
	@FXML
	private void handleSearch() {
		FilteredList<User> filteredData = new FilteredList<>(items, e -> true);
		
		searchField.setOnKeyTyped(e -> {
			searchField.textProperty().addListener((observableValue, oldValue, newValue) ->{
				filteredData.setPredicate((Predicate<? super User>) item ->{
					if(newValue == null || newValue.isEmpty()) {
						return true;
					}
					if(item.getUsername().contains(newValue)) {
						return true;
					}
					return false;
				});
			});
			SortedList<User> sortedData = new SortedList<>(filteredData);
			
			listView.setItems(sortedData);
		});
	}
	/**
	 * Performs action on new user button. Changes root scene to 
	 * register page.
	 * 
	 * 
	 * @see AdminController
	 */
	@FXML 
	private void handleNew() {
		
		app.showRegisterPage();
		
	}
	
	/**
	 * Performs action on list view cell selected.
	 * Displays user information of cell selected into labels.
	 * 
	 * @see AdminController
	 */
	@FXML
	private void handleInfo() {
		
		user = listView.getSelectionModel().getSelectedItem();
		
		
		app.showAccountPageAdmin(user);
		
		
		
	}
	/**
	 * Performs action on new delete button. Deletes user which
	 * corresponds to cell selected on table view. Removes user from 
	 * database.
	 * 
	 * 
	 * @see AdminController
	 */
	@FXML 
	private void handleDelete() {
		user = listView.getSelectionModel().getSelectedItem();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

		alert.setTitle("Deletion Confirmation");
		alert.setHeaderText("Remove user from list?");
		alert.setContentText("");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			
		app.getLiveUserBag().getUsers().remove(user.getUsername());
		app.showAdminPage();
		}
		
		else {
			alert.close();
		}
	}
	
	/**
	 * Updates table to keep live updates of users added or deleted.
	 * 
	 * 
	 * @see AdminController
	 */
	public void updateTable() {
		 items = FXCollections.observableArrayList(app.getLiveUserBag().getUsers().values());
		
		listView.getItems().removeAll(items);
		listView.getItems().setAll(items);
		
	}
	
	/**
	 * Displays user's information on labels corresponding to list view cells.
	 * 
	 * 
	 * @see AdminController
	 * @param user A user to be displayed
	 */
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
