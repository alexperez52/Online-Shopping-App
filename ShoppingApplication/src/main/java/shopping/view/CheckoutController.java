package shopping.view;

import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import shopping.app.App;
import shopping.model.Invoice;
import shopping.model.Item;
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

	private double calculateItemTotal() { // totals user's bill
		double totalPrice = 0;
		for (ShoppingCartItem item : app.getCurrentUser().getCart().getCartItems()) {
			totalPrice += app.getLiveInventory().getInventory().get(item.getId()).getPrice() * item.getItemQuantity();
		}

		return totalPrice;
	}

	@FXML
	private void handleCheckoutButton() { // will confirm purchase, need to add invoice generation
		if (fieldCheck()) {
			if (app.getCurrentUser().getPayment() != null) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Payment Confirmation");
				alert.setHeaderText("You have existing payment information");
				alert.setContentText("Use current payment method?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					completePurchase();
					return;
				} else {
					return;
				}
			}
			Payment userPayment = new Payment(isPaypal, isCard, extractPayment(isPaypal, isCard));
			app.getCurrentUser().setPayment(userPayment);
			completePurchase();
		}

	}

	private void completePurchase() {
		updateInventory(app.getCurrentUser().getCart()); // updates inventory after purchase
		generateInvoice(app.getCurrentUser().getCart());
		app.getCurrentUser().getCart().clearCart();

		
	}

	private void generateInvoice(ShoppingCart cart) {
		Invoice newInvoice = new Invoice(app.getCurrentUser(),
				(Math.round(((calculateItemTotal() * 1.04) * 100.0) / 100.0f)), app.getLiveInventory());
		app.getCurrentUser().getInvoiceLog().put(newInvoice.getId(), newInvoice);
		app.showInvoiceDialog(newInvoice);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Checkout");
		alert.setHeaderText("Items purchased!");
		alert.setContentText("");

		alert.showAndWait();

		System.out.println(app.getCurrentUser().getPayment());

		app.showCatalogPage();
	}

	private void updateInventory(ShoppingCart cart) { // adjust stock after purchase is made
		for (ShoppingCartItem item : app.getCurrentUser().getCart().getCartItems()) {
			Item inventoryItem = app.getLiveInventory().getInventory().get(item.getId());
			inventoryItem.setQuantity(inventoryItem.getQuantity() - item.getItemQuantity());
		}
	}

	@FXML
	private void handleCancelButton() { // cancels checkout, takes user to catalog
		app.showCatalogPage();
	}

	private String extractPayment(boolean isPaypal, boolean isCard) { // payment gets stored differently for paypal/card
		String paymentInfo;
		if (isPaypal) {
			paymentInfo = "Paypal Email: " + paypalEmailField.getText() + "\nPayPal Password: "
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
	private void handlePaypalRadio() {// paypal button will only allow payapl info
		paypalVbox.setDisable(false);
		cardVbox.setDisable(true);
		isPaypal = true;
		isCard = false;
	}

	@FXML
	private void handleCardRadio() { // card button will only allow card info
		paypalVbox.setDisable(true);
		cardVbox.setDisable(false);
		isPaypal = false;
		isCard = true;
	}

	public boolean fieldCheck() {// checks inefficient field data
		if (app.getCurrentUser().getPayment() != null) {
			return true;
		}

		if (!(paypalEmailField.getText().isEmpty()) && !(paypalPasswordField.getText().isEmpty())) {
			if (paypalEmailField.getText().matches("([\\w]+@[\\w]+[.][\\w]+)+")) { // checks if email is invalid
				return true;
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Warning");
				alert.setHeaderText("Please enter a valid email!");
				alert.setContentText("");

				alert.showAndWait();

				return false;
			}
		}
		if (!(cardNumberField.getText().isEmpty()) && !(cardSecurityField.getText().isEmpty()) // first checks if fields
																								// aren't empty
				&& !(cardHolderField.getText().isEmpty()) && monthBox.getSelectionModel().getSelectedItem() != null
				&& yearBox.getSelectionModel().getSelectedItem() != null) {
			if (cardNumberField.getText().replaceAll("[^\\d]+", "").matches("[\\d]{16}")
					&& cardSecurityField.getText().matches("[\\d]{3}")
					&& cardHolderField.getText().matches("[a-zA-Z\\s]+")) {
				return true;
				// extracts digits
				// from card #, and cvv and extracts letters from card holder
				// checks if there's
				// proper amount of digits, or valid input for a name
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Warning");
				alert.setHeaderText("Please enter a valid input for field(s)");
				alert.setContentText("");

				alert.showAndWait();

				return false;
			}
		}

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Warning");
		alert.setHeaderText("Certain Field(s) incomplete!");
		alert.setContentText("");

		alert.showAndWait();

		return false;
	}

	public void setApp(App app) { // gives controller access to databases
		this.app = app;
		double bill = calculateItemTotal();
		totalLabel.setText("Total: $" + shopping.utils.DataFormatter.formatAmount(bill));
		afterTaxLabel.setText("After Tax: $" + shopping.utils.DataFormatter.formatAmount(bill));
	}
}
