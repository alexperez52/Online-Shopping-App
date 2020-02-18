package shopping.view;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import shopping.app.App;
import shopping.model.Electronics;
import shopping.model.Item;
import shopping.model.ShoppingCart;
import shopping.model.ShoppingCartItem;

public class CatalogController {
	private App app;
	private ListView<Item> fullItemsView = new ListView<Item>();
	@FXML
	private TextField searchField;
	@FXML
	private ListView<Item> itemsView;
	@FXML
	private Label name;
	@FXML
	private Label price;
	@FXML
	private Label quantity;
	@FXML
	private Label description;
	@FXML
	private Button editItemBtn;
	@FXML
	private Button newItemBtn;
	@FXML
	private Button removeItemBtn;
	@FXML
	private ComboBox<Electronics> category = new ComboBox<Electronics>();

	public CatalogController() {

	}

	@FXML
	private void initialize() {
		itemsView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			displayItemInformation(newValue);
		});
		category.setItems(FXCollections.observableArrayList(Electronics.values())); // sets electronics types
		category.setOnAction((e) -> {
			//TODO: REWORK COMBOBOX SEARCH!
			if (category.getSelectionModel().getSelectedItem().equals(Electronics.ALL)) {// shows all products...
				displaySubList(0, 45);
			} else if (category.getSelectionModel().getSelectedItem().equals(Electronics.CPU)) {// only shows cpus, etc.
				displaySubList(0, 14);
			} else if (category.getSelectionModel().getSelectedItem().equals(Electronics.GPU)) {
				displaySubList(14, 28);
			} else if (category.getSelectionModel().getSelectedItem().equals(Electronics.MEMORY)) {
				displaySubList(28, 35);
			} else {
				displaySubList(35, 45);
			}
		});
	}

	public void setApp(App app) { // gives controller access to databases
		this.app = app;
		ObservableList<Item> items = FXCollections.observableArrayList(app.getLiveInventory().getInventory().values());
		itemsView.getItems().setAll(items);
		fullItemsView.getItems().setAll(items);
		if (app.getCurrentUser().isAdmin()) {
			newItemBtn.setVisible(true);
			editItemBtn.setVisible(true);
			removeItemBtn.setVisible(true);
		} else {
			newItemBtn.setVisible(false);
			editItemBtn.setVisible(false);
			removeItemBtn.setVisible(false);
		}
	}

	private void displaySubList(int startIndex, int endIndex) { //shortens display list to specific sections
		ArrayList<Item> items = new ArrayList<Item>(app.getLiveInventory().getInventory().values());
		ObservableList<Item> subList = FXCollections.observableArrayList(items.subList(startIndex, endIndex));
		itemsView.getItems().setAll(subList);
	}

	@FXML
	private void handleAddToCart() {// gives cart adding function. Takes item's unique info for checkout to search
									// in inventory
		ShoppingCart cart = new ShoppingCart();
		cart.setCart(app.getCurrentUser().getCart().getCartItems()); // current user's cart
		int selectedIndex = itemsView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {// if user clicks on an item
			Item clickedItem = app.getLiveInventory().getInventory().get(selectedIndex + 1);
			if (clickedItem.getQuantity() != 0) { // checks if item is in stock
				Integer itemId = app.getLiveInventory().getInventory().get(selectedIndex + 1).getId(); // obtains item's
																										// id
				ShoppingCartItem cartItem = new ShoppingCartItem(itemId, 1);// makes shopping cart item
				if (cart.getCartItems().contains(cartItem)) {// if cart item exists already
					int index = cart.getCartItems().indexOf(cartItem);
					ShoppingCartItem existingCartItem = cart.getCartItems().get(index);
					existingCartItem.setItemQuantity(existingCartItem.getItemQuantity() + 1);// just increase quantity
				} else {
					cart.getCartItems().add(cartItem);
				}
			}

			else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Item not in stock!");
				alert.setContentText("Can't add to cart");

				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Item Selected");
			alert.setContentText("Please select an item in the catalog.");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleNewItemBtn() {// gives admin ability to create new item
		app.showItemDialog(null);
	}

	@FXML
	private void handleRemoveBtn() {// removes item from inventory
		int selectedIndex = itemsView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Item clickedItem = app.getLiveInventory().getInventory().get(selectedIndex + 1);

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Deletion Confirmation");
			alert.setHeaderText("Remove item from inventory?");
			alert.setContentText("");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				app.getLiveInventory().getInventory().remove(clickedItem.getId(), clickedItem);
				app.showCatalogPage();

			} else {
				return;
			}
		}

		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Item Selected");
			alert.setContentText("Please select an item in the catalog.");

			alert.showAndWait();
		}

	}

	@FXML
	private void handleEditItemBtn() {// gives admin ability to edit clicked item
		int selectedIndex = itemsView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Item clickedItem = app.getLiveInventory().getInventory().get(selectedIndex + 1);
			app.showItemDialog(clickedItem);
		}

		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Item Selected");
			alert.setContentText("Please select an item in the catalog.");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleSearchField() { // finds specific item in list by name
		int index = -1;
		if(searchField.getText().isEmpty()) {
			displaySubList(0, fullItemsView.getItems().size());
		}
		for (Item item : fullItemsView.getItems()) {
			index++;
			if (item.getName() != null && item.getName().equalsIgnoreCase(searchField.getText())) {
				displaySubList(index, index + 1);
				break;
			}
		}
	}

	private void displayItemInformation(Item item) { //displays clicked item info
		if (item != null) {
			name.setText("Name: " + item.getName());
			price.setText("Price: $" + shopping.utils.DataFormatter.formatAmount(item.getPrice())); // #.00
			quantity.setText("Quantity: " + String.valueOf(item.getQuantity()));
			description.setText("Description: " + item.getDescription());
		}
	}

}
