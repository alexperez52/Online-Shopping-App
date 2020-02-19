
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
	private Item clickedItem;
	private ObservableList<Item> inventoryItemsList;
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
			clickedItem = newValue;
			// System.out.println(clickedItem.getElectronic());
		});
		category.setItems(FXCollections.observableArrayList(Electronics.values())); // sets electronics types
		category.setOnAction((e) -> {
			// TODO: REWORK COMBOBOX SEARCH!
			if (category.getSelectionModel().getSelectedItem().equals(Electronics.ALL)) {// shows all products...
				displaySubList(Electronics.ALL);
			} else if (category.getSelectionModel().getSelectedItem().equals(Electronics.CPU)) {// only shows cpus, etc.
				displaySubList(Electronics.CPU);
			} else if (category.getSelectionModel().getSelectedItem().equals(Electronics.GPU)) {
				displaySubList(Electronics.GPU);
			} else if (category.getSelectionModel().getSelectedItem().equals(Electronics.MEMORY)) {
				displaySubList(Electronics.MEMORY);
			} else {
				displaySubList(Electronics.MOTHERBOARD);
			}
		});
	}

	public void setApp(App app) { // gives controller access to databases
		this.app = app;
		inventoryItemsList = FXCollections.observableArrayList(app.getLiveInventory().getInventory().values()); // loads
																												// inventory
																												// into
																												// page
		itemsView.getItems().setAll(inventoryItemsList);
		if (app.getCurrentUser().isAdmin()) {// checks if user is admin to show certain controls
			newItemBtn.setVisible(true);
			editItemBtn.setVisible(true);
			removeItemBtn.setVisible(true);
		} else {
			newItemBtn.setVisible(false);
			editItemBtn.setVisible(false);
			removeItemBtn.setVisible(false);
		}
	}

	/**
	 * Takes an Electronics value to display a sublist of only Item objects
	 * containing that value, onto the catalog listView.
	 * 
	 * @param electronicType An Electronics value to sort catalog listView with
	 * @see CatalogController
	 */
	private void displaySubList(Electronics electronicType) { // shortens display list to specific sections
		ArrayList<Item> items = new ArrayList<Item>(app.getLiveInventory().getInventory().values());
		ArrayList<Item> subList = new ArrayList<Item>();
		if (electronicType.compareTo(Electronics.ALL) == 0) {
			itemsView.getItems().setAll(FXCollections.observableArrayList(items));
			return;
		} else {
			for (Item item : items) {
				if (item.getElectronic() != null && item.getElectronic().compareTo(electronicType) == 0) {
					subList.add(item);
				}
			}
		}
		itemsView.getItems().setAll(FXCollections.observableArrayList(subList));
	}

	/**
	 * Performs action on AddToCart button click. Creates a ShoppingCartItem with id
	 * of clickedItem in catalog listView and quantity of 1.
	 * <p>
	 * If the clickedItem is in stock, the item's id is searched for in the user's
	 * ShoppingCart; if the id is found, that ShoppingCartItem's quantity is
	 * increased by 1, if not, the created ShoppingCartItem is placed in the
	 * ShoppingCart
	 * 
	 * @see CatalogController
	 */
	@FXML
	private void handleAddToCart() {// gives cart adding function. Takes item's unique info for checkout to search
									// in inventory
		ShoppingCart cart = new ShoppingCart();
		cart.setCart(app.getCurrentUser().getCart().getCartItems()); // current user's cart
		int selectedIndex = itemsView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {// if user clicks on an item
			if (clickedItem.getQuantity() > 0) { // checks if item is in stock
				ShoppingCartItem cartItem = new ShoppingCartItem(clickedItem.getId(), 1);// makes shopping cart item
				int index = cart.getCartItems().indexOf(cartItem); // finds item in cart already
				ShoppingCartItem searchedItem;
				try {
					searchedItem = cart.getCartItems().get(index);
				} catch (Exception e) {
					searchedItem = null; // if not found, makes searched item null
				}
				if (searchedItem != null && ((searchedItem.getItemQuantity() + 1) <= clickedItem.getQuantity())) {// if
																													// cart
																													// item
																													// exists
																													// already
																													// and
																													// (current
																													// amount
																													// +
																													// new)
																													// <
																													// stock
					searchedItem.setItemQuantity(searchedItem.getItemQuantity() + 1);// just increase quantity
				} else if (searchedItem == null) {
					cart.getCartItems().add(cartItem);
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Item not in stock anymore!");
					alert.setContentText("Can't add to cart");

					alert.showAndWait();
				}
			}
		}

		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Item not in stock!");
			alert.setContentText("Can't add to cart");

			alert.showAndWait();
		}
	}

	/**
	 * Performs action on New Item button click. Calls showItemDialog(Item item)
	 * with null argument. Admin Action. Allows a new item to be created and added
	 * to inventory
	 * 
	 * @see App
	 */
	@FXML
	private void handleNewItemBtn() {// gives admin ability to create new item
		app.showItemDialog(null);
	}

	/**
	 * Performs action on Remove button click. Removes clickedItem from catalog
	 * listView from the inventory. Admin Action.
	 * 
	 * @see CatalogController
	 */
	@FXML
	private void handleRemoveBtn() {// removes item from inventory
		int selectedIndex = itemsView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {

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

	/**
	 * Performs action on Edit Item button click. Calls showItemDialog(Item item).
	 * Admin Action. Allows adjustment of clickedItem's fields.
	 * 
	 * @see App
	 */
	@FXML
	private void handleEditItemBtn() {// gives admin ability to edit clickedItem
		int selectedIndex = itemsView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Item clickedItem = itemsView.getSelectionModel().getSelectedItem();
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

	/**
	 * Performs action on search textField keystroke. Searches catalog listView for
	 * item with same name as text in the search textField, ignoring case. Once
	 * found, the item is the only object displayed in the catalog listView
	 * 
	 * @see App
	 */
	@FXML
	private void handleSearchField() { // finds specific item in list by name
		if (searchField.getText().isEmpty()) {
			itemsView.getItems().setAll(inventoryItemsList);
		}
		for (Item item : ((ListView<Item>) inventoryItemsList).getItems()) {
			if (item.getName() != null && item.getName().equalsIgnoreCase(searchField.getText())) {
				itemsView.getItems().clear();
				itemsView.getItems().add(item);
				break;
			}
		}
	}
	
	/**
	 * Performs action on catalog listView Item click. Retrieves clickedItem's fields to display below the catalog listView.
	 * 
	 * @param item An Item object clicked in the catalog lsitView
	 * @see App
	 */
	private void displayItemInformation(Item item) { // displays clicked item info
		if (item != null) {
			name.setText("Name: " + item.getName());
			price.setText("Price: $" + shopping.utils.DataFormatter.formatAmount(item.getPrice())); // #.00
			quantity.setText("Quantity: " + String.valueOf(item.getQuantity()));
			description.setText("Description: " + item.getDescription());
		}
	}

}