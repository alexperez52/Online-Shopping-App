package shopping.view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import shopping.app.App;
import shopping.model.Month;
import shopping.model.Payment;
import shopping.model.ShoppingCart;
import shopping.model.ShoppingCartItem;

public class CheckoutController {
	private App app;
	private boolean isPaypal;
	private boolean isCard;
	private Integer[] years = { 2021, 2022, 2023, 2024 };
	ObservableList<Month> monthList = FXCollections.observableArrayList(Month.values());
	ObservableList<Integer> yearList = FXCollections.observableArrayList(years);
	@FXML
	private RadioButton paypalRadBtn;
	@FXML
	private RadioButton cardRadBtn;
	@FXML
	private TextField paypalEmailField;
	@FXML
	private TextField paypalPasswordField;
	@FXML
	private TextField cardNumberField;
	@FXML
	private ComboBox<Month> monthBox;
	@FXML
	private ComboBox<Integer> yearBox;
	@FXML
	private TextField cardSecurityField;
	@FXML
	private TextField cardHolderField;
	@FXML
	private Button checkoutBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private Label totalLabel;
	@FXML
	private Label afterTaxLabel;
	@FXML
	private VBox paypalVbox;
	@FXML
	private VBox cardVbox;
	@FXML
	private ToggleGroup toggGroup1;

	public CheckoutController() {

	}

	@FXML
	private void initialize() {
		monthBox.setItems(monthList);
		yearBox.setItems(yearList);

	}

	private double calculateItemTotal() { //totals user's bill
		double totalPrice = 0;
		for (ShoppingCartItem item : app.getCurrentUser().getCart().getCartItems()) {
				totalPrice = app.getLiveInventory().getInventory().get(item.getId()).getPrice() * item.getItemQuantity();
			}
		
		return totalPrice;
	}

	@FXML
	private void handleCheckoutButton() { //will confirm purchase, need to add invoice generation
		if (fieldCheck()) {
			Payment userPayment = new Payment(isPaypal, isCard, extractPayment(isPaypal, isCard));
			app.getCurrentUser().setPayment(userPayment);
			app.getCurrentUser().getCart().clearCart();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Checkout");
			alert.setHeaderText("Items purchased!");
			alert.setContentText("");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleCancelButton() { //cancels checkout, takes user to catalog
		app.showCatalogPage();
	}

	private String extractPayment(boolean isPaypal, boolean isCard) { //payment gets stored differently for paypal/card
		String paymentInfo;
		if (isPaypal) {
			paymentInfo = "Paypal Email: " + paypalEmailField.getText() + "\nPayPal Passwod: "
					+ paypalPasswordField.getText();
		} else {
			paymentInfo = "Card Number: " + cardNumberField.getText() + "\nExpires: "
					+ monthBox.getSelectionModel().getSelectedIndex() + "/"
					+ yearBox.getSelectionModel().getSelectedItem() + "\nCVV: " + cardSecurityField.getText()
					+ "\nCard Holder: " + cardHolderField.getText();
		}

		return paymentInfo;
	}

	@FXML
	private void handlePaypalRadio() {//paypal button will only allow payapl info
		paypalVbox.setDisable(false);
		cardVbox.setDisable(true);
		isPaypal = true;
		isCard = false;
	}

	@FXML
	private void handleCardRadio() { //card button will only allow card info
		paypalVbox.setDisable(true);
		cardVbox.setDisable(false);
		isPaypal = false;
		isCard = true;
	}

	public boolean fieldCheck() {// checks inefficient field data
		if (!(paypalEmailField.getText().isEmpty()) && !(paypalPasswordField.getText().isEmpty())) {
			return true;
		}
		if (!(cardNumberField.getText().isEmpty()) && !(cardSecurityField.getText().isEmpty())
				&& !(cardHolderField.getText().isEmpty())
				&& monthBox.getSelectionModel().getSelectedItem() != Month.MONTH
				&& yearBox.getSelectionModel().getSelectedItem() != null) {
			return true;
		}

		return false;
	}

	public void setApp(App app) { // gives controller access to databases
		this.app = app;
		double bill = calculateItemTotal();
		totalLabel.setText("Total: $"+ (Math.round(bill * 100.0) / 100.0f));
		afterTaxLabel.setText("After Tax: $"+ (Math.round((bill * 1.04) * 100.0) / 100.0f));
	}
}
