package shopping.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import shopping.app.App;
import shopping.model.ShoppingCartItem;

public class ShoppingCartController {

	private App app;

	@FXML
	private Button orderButton, plusButton, minusButton, cancelButton;
	@FXML
	private ListView<ShoppingCartItem> listView;

	public void setApp(App app) {
		this.app = app;
	}

	/**
	 * Shows items on a table view from current user's cart from data base.
	 * 
	 * 
	 * @see ShoppingCartController
	 */
	public void showItems() {
		ObservableList<ShoppingCartItem> items = FXCollections
				.observableArrayList(app.getCurrentUser().getCart().getCartItems());
		listView.getItems().setAll(items);
	}

	/**
	 * Performs action on new check out button. Checks if cart is empty. If the cart
	 * is empty, then throws an alert dialog. If the cart has items, then the
	 * checkout page is set to root scene.
	 * 
	 * 
	 * @see ShoppingCartController
	 */
	@FXML
	private void showCheckOut() {

		if (app.getCurrentUser().getCart().getCartItems().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

			alert.setTitle("Warning");
			alert.setHeaderText("Empty Cart");
			alert.setContentText("Cart is Empty!");

			alert.showAndWait();
		} else {
			app.showCheckoutPage();
		}
	}

	/**
	 * Performs action on new empty cart button. If pressed, the current user's
	 * shopping cart will be cleared.
	 * 
	 * 
	 * @see ShoppingCartController
	 */
	@FXML
	private void emptyCart() {
		app.getCurrentUser().getCart().clearCart();
		listView.getItems().clear();
		listView.refresh();
	}

	/**
	 * Performs action on add button. Adds to the quantity of an item in the user's
	 * cart.
	 * <p>
	 * Checks if the inventory database has enough items to be pulled from user.
	 * Increased quantity of user's item quantity for the selected list view cell
	 * corresponding to a Shopping Cart Item.
	 * 
	 * 
	 * @see ShoppingCartController
	 */
	@FXML
	private void add() {

		try {
			int item = listView.getSelectionModel().getSelectedIndex();
			int quantity = app.getLiveInventory().getInventory()
					.get(listView.getSelectionModel().getSelectedItem().getId()).getQuantity();
			int actual = app.getCurrentUser().getCart().getCartItems().get(item).getItemQuantity();

			if (quantity <= actual) {

				Alert alert = new Alert(AlertType.WARNING);
				alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

				alert.setTitle("Warning");
				alert.setHeaderText("Not enough Items");
				alert.setContentText("Can't add to cart");

				alert.showAndWait();
			} else {

				app.getCurrentUser().getCart().getCartItems().get(item)
						.setItemQuantity(app.getCurrentUser().getCart().getCartItems().get(item).getItemQuantity() + 1);
				listView.refresh();

			}
		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

			alert.setTitle("Warning");
			alert.setHeaderText("No item selected");
			alert.setContentText("Please select an item to modify !");

			alert.showAndWait();
		}

	}

	/**
	 * Performs action on minus button. Removes from the quantity of an item in the user's
	 * cart.
	 * <p>
	 * Checks if the user's cart database has at least 0 or more items to be removed.
	 * If count goes below 0, a warning dialog is thrown. 
	 * 
	 * 
	 * @see ShoppingCartController
	 */
	@FXML
	private void minus() {
		try {
			int item = listView.getSelectionModel().getSelectedIndex();
			int actual = app.getCurrentUser().getCart().getCartItems().get(item).getItemQuantity();

			if (actual < 1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

				alert.setTitle("Warning");
				alert.setHeaderText("Quantity below 0");
				alert.setContentText("Can't return if you don't own");

				alert.showAndWait();
			} else {

				app.getCurrentUser().getCart().getCartItems().get(item)
						.setItemQuantity(app.getCurrentUser().getCart().getCartItems().get(item).getItemQuantity() - 1);
				listView.refresh();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

			alert.setTitle("Warning");
			alert.setHeaderText("No item selected");
			alert.setContentText("Please select an item to modify!");

			alert.showAndWait();
		}
	}

}
