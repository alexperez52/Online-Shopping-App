package shopping.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import shopping.app.App;
import shopping.model.Item;

public class ItemDialogController {
	Item currentItem;
	private App app;
	private Stage dialogStage;
	private boolean isEdit;
	@FXML
	private TextField nameField;
	@FXML
	private TextField priceField;
	@FXML
	private TextField descriptionField;
	@FXML
	private TextField quantityField;

	public ItemDialogController() {

	}

	@FXML
	private void initialize() {

	}

	@FXML
	private void handleOk() {
		if (fieldCheck()) {
			if (currentItem == null) {
				Item newItem = new Item(nameField.getText(), Double.parseDouble(priceField.getText()),
						descriptionField.getText(), Integer.parseInt(quantityField.getText()));
				app.getLiveInventory().getInventory().put(newItem.getId(), newItem);
				

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Item");
				alert.setHeaderText("New item added to inventory!");
				alert.setContentText("");

				alert.showAndWait();
				
				dialogStage.close();

				
				app.showCatalogPage();
			} else {
				if (!(nameField.getText().isEmpty())) {
					currentItem.setName(nameField.getText());
				}
				if (!(priceField.getText().isEmpty()) && (priceField.getText().replaceAll("(\\d+\\.{1}\\d+)", "").matches("") // checks if price field has int
																						// or
																						// double to use
						|| priceField.getText().replaceAll("(\\d+\\.{1}\\d+)", "").matches("[\\d]+"))) {
					currentItem.setPrice(Double.parseDouble(priceField.getText()));
				}
				if (!(quantityField.getText().isEmpty()) && quantityField.getText().replaceAll("[\\d]+", "").matches("")) { // takes int only
					currentItem.setQuantity(Integer.parseInt(quantityField.getText()));
				}
				if (!(descriptionField.getText().isEmpty())) {
					currentItem.setDescription(descriptionField.getText());
				}
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Item");
				alert.setHeaderText("Item Updated!");
				alert.setContentText("");

				alert.showAndWait();
				
				dialogStage.close();

				
				app.showCatalogPage();

			}
		}
	}
	
	@FXML
	private void handleCancel() {
		nameField.clear();
		priceField.clear();
		quantityField.clear();
		descriptionField.clear();

	}


	private boolean fieldCheck() { // checks if fields have appropriate information
		if (currentItem == null) {// assumes new item
			if (nameField.getText().isEmpty() || priceField.getText().isEmpty() || descriptionField.getText().isEmpty()
					|| quantityField.getText().isEmpty()) { // any field empty, false
				Alert alert = new Alert(AlertType.WARNING);
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
				alert.setTitle("Error");
				alert.setHeaderText("Issues with certain field(s)");
				alert.setContentText("Please provide only digits for price and/ or quantity!");

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