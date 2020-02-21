package shopping.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import shopping.app.App;
import shopping.model.Electronics;
import shopping.model.Item;

public class ItemDialogController {
	Item currentItem;
	private App app;
	private Stage dialogStage;
	private boolean isEdit;
	private Electronics[] itemTypes = { Electronics.CPU, Electronics.GPU, Electronics.MEMORY, Electronics.MOTHERBOARD };
	@FXML
	private TextField nameField;
	@FXML
	private TextField priceField;
	@FXML
	private TextField descriptionField;
	@FXML
	private TextField quantityField;
	@FXML
	private ComboBox<Electronics> itemTypeBox;

	public ItemDialogController() {

	}

	@FXML
	private void initialize() {
		itemTypeBox.setItems(FXCollections.observableArrayList(itemTypes)); // sets electronics types

	}

	/**
	 * Performs action on Ok Button click. Calls fieldCheck() to detect invalid user
	 * input on ItemDialog. If all text fields are filled and the currentItem ==
	 * null, a new Item object is created. If currentInvoice != null, any text field
	 * with user input is used to update that specific field in the currentItem.
	 * 
	 * @see CheckoutController
	 */
	@FXML
	private void handleOk() {
		if (fieldCheck()) {
			if (currentItem == null) {
				Item newItem = new Item(nameField.getText(), Double.parseDouble(priceField.getText()),
						descriptionField.getText(), Integer.parseInt(quantityField.getText()),
						itemTypeBox.getSelectionModel().getSelectedItem());
				app.getLiveInventory().getInventory().put(newItem.getId(), newItem);

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

				alert.setTitle("Item");
				alert.setHeaderText("New item added to inventory!");
				alert.setContentText("");

				alert.showAndWait();

				dialogStage.close();

				app.showCatalogPage();
			} else {
				boolean edited = false;
				if (!(nameField.getText().isEmpty())) {
					currentItem.setName(nameField.getText());
				}
				if (!(priceField.getText().isEmpty())
						&& (priceField.getText().replaceAll("(\\d+\\.{1}\\d+)", "").matches("") // checks if price field
																								// has int
								// or
								// double to use
								|| priceField.getText().replaceAll("(\\d+\\.{1}\\d+)", "").matches("[\\d]+"))) {
					currentItem.setPrice(Double.parseDouble(priceField.getText()));
					edited = true;
				}
				if (!(quantityField.getText().isEmpty())
						&& quantityField.getText().replaceAll("[\\d]+", "").matches("")) { // takes int only
					currentItem.setQuantity(Integer.parseInt(quantityField.getText()));
					edited = true;
				}
				if (!(descriptionField.getText().isEmpty())) {
					currentItem.setDescription(descriptionField.getText());
					edited = true;

				}

				if (itemTypeBox.getSelectionModel().getSelectedItem() != null) {
					currentItem.setElectronic(itemTypeBox.getSelectionModel().getSelectedItem());
					edited = true;
				}

				if (edited) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

					alert.setTitle("Item");
					alert.setHeaderText("Item Updated!");
					alert.setContentText("");

					alert.showAndWait();

					dialogStage.close();

					app.showCatalogPage();
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

					alert.setTitle("Item");
					alert.setHeaderText("Item not updated!");
					alert.setContentText("");

					alert.showAndWait();

					dialogStage.close();
				}
			}
		}
	}
	
	/**
	 * Performs action on Cancel Button click. Clears all text field data.
	 * 
	 * @see CheckoutController
	 */
	@FXML
	private void handleCancel() {
		nameField.clear();
		priceField.clear();
		quantityField.clear();
		descriptionField.clear();
		dialogStage.close();

	}
	
	/**
	 * Checks all available ItemDialog fields and checks if improper user input
	 * exists. Only returns true if user information is correct.
	 * 
	 * @return isProperInformation boolean value returning true if fields are
	 *         acceptable, false if user input exception is caught
	 * @see ItemDialogController
	 */
	private boolean fieldCheck() { // checks if fields have appropriate information
		if (currentItem == null) {// assumes new item
			if (nameField.getText().isEmpty() || priceField.getText().isEmpty() || descriptionField.getText().isEmpty()
					|| quantityField.getText().isEmpty()) { // any field empty, false
				Alert alert = new Alert(AlertType.WARNING);
				alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

				alert.setTitle("Error");
				alert.setHeaderText("Issues with certain field(s)");
				alert.setContentText("Please fill each field");

				alert.showAndWait();
				return false;
			}
			if (priceField.getText().replaceAll("(\\d+\\.{1}\\d+)", "").matches("[^\\d]+")
					|| quantityField.getText().replaceAll("[\\d]+", "").matches("[^\\d]+")) { // checks for non digits
																								// in price (allows
																								// decimal), and non
																								// ints in quant
				Alert alert = new Alert(AlertType.WARNING);
				alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

				alert.setTitle("Error");
				alert.setHeaderText("Issues with certain field(s)");
				alert.setContentText("Please provide only digits for price and/ or quantity!");

				alert.showAndWait();
				return false;
			}
			if (itemTypeBox.getSelectionModel().getSelectedItem() == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

				alert.setTitle("Error");
				alert.setHeaderText("Issues with certain field(s)");
				alert.setContentText("Please provide an item type!");

				alert.showAndWait();
				return false;
			}
			return true;
		} else // assumes editing item
			return true;

	}

	public void setApp(App app) { // gives controller access to databases
		this.app = app;

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public Item getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(Item currentItem) {
		this.currentItem = currentItem;
		isEdit = true; // allows fieldCheck() to decide if certain field information is ok depending on
						// if it's for edit or new
	}

}