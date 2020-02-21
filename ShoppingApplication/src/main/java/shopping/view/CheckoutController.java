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

	/**
	 * Looks up user's ShoppingCartItems' id's in inventory to get the Item object's
	 * price and multiplies it by the amount they're purchasing. Each Item object
	 * total price is added to a sum to be returned as the bill
	 * 
	 * @return totalPrice Sum of each ShoppingCartItem's (price * quantity) for
	 *         checkout to display
	 * @see CheckoutController
	 */
	private double calculateItemTotal() { // totals user's bill
		double totalPrice = 0;
		for (ShoppingCartItem item : app.getCurrentUser().getCart().getCartItems()) {
			totalPrice += app.getLiveInventory().getInventory().get(item.getId()).getPrice() * item.getItemQuantity();
		}

		return totalPrice;
	}

	/**
	 * Performs action on Checkout button click. Calls fieldCheck() to validate user
	 * input before taking all Checkout page text field texts for creating/update
	 * payment information. Asks user if they want to use previous payment
	 * information, if it exists. Completes purchase by calling completePurchase().
	 * 
	 * @see CheckoutController
	 */
	@FXML
	private void handleCheckoutButton() { // will confirm purchase, need to add invoice generation
		if (fieldCheck()) {
			if (!(app.getCurrentUser().getPayment() == null)) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

				alert.setTitle("Payment Confirmation");
				alert.setHeaderText("You have existing payment information");
				alert.setContentText("Use current payment method?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					completePurchase();
					return;
				} else {
					if (fieldCheckNewPayment()) {
						Payment userPayment = new Payment(isPaypal, isCard, extractPayment(isPaypal, isCard));
						app.getCurrentUser().setPayment(userPayment);
						completePurchase();
					}
				}
			} else {
				Payment userPayment = new Payment(isPaypal, isCard, extractPayment(isPaypal, isCard));
				app.getCurrentUser().setPayment(userPayment);
				completePurchase();
			}
		}

	}

	/**
	 * Called by the handleCheckoutButton(). Calls updateInventory(ShoppingCartItem
	 * cart), generateInvoice(), and user's cart.clearCart().
	 * 
	 * @see CheckoutController
	 */
	private void completePurchase() {
		updateInventory(app.getCurrentUser().getCart()); // updates inventory after purchase
		generateInvoice();
		app.getCurrentUser().getCart().clearCart();

	}

	/**
	 * Creates a new Invoice object based on user's checkout information. It puts
	 * the Invoice object in the user's invoiceLog and then displays it with
	 * app.showInvoiceDialog().
	 * 
	 * @see CheckoutController
	 */
	private void generateInvoice() {
		Invoice newInvoice = new Invoice(app.getCurrentUser(),
				(Math.round(((calculateItemTotal() * 1.04) * 100.0) / 100.0f)), app.getLiveInventory());
		app.getCurrentUser().getInvoiceLog().put(newInvoice.getId(), newInvoice);
		app.showInvoiceDialog(newInvoice);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

		alert.setTitle("Checkout");
		alert.setHeaderText("Items purchased!");
		alert.setContentText("");

		alert.showAndWait();

		app.showCatalogPage();
	}

	/**
	 * Subtracts the quantity of each Item object purchased by user from their
	 * respective Item object in inventory to update the stock. Takes user's
	 * shoppingCart as an argument to search through it.
	 * 
	 * @param cart user's ShoppingCart object to search through
	 * @see CheckoutController
	 */
	private void updateInventory(ShoppingCart cart) { // adjust stock after purchase is made
		for (ShoppingCartItem item : app.getCurrentUser().getCart().getCartItems()) {
			Item inventoryItem = app.getLiveInventory().getInventory().get(item.getId());
			inventoryItem.setQuantity(inventoryItem.getQuantity() - item.getItemQuantity());
		}
	}

	/**
	 * Performs action on Cancel button click. Cancels the checkout and takes user
	 * back to CatalogPage.
	 * 
	 * @see CheckoutController
	 */
	@FXML
	private void handleCancelButton() { // cancels checkout, takes user to catalog
		app.showCatalogPage();
	}

	/**
	 * Takes the boolean status of isPayapl and isCard as arguments. If isPaypal is
	 * true, the paymentInfo String formats the user's payment information field to
	 * store the information from the payPal text fields on the checkoutPage; if
	 * isCard, does the same process, but for the card information text fields.
	 * 
	 * @param isPaypal, isCard boolean statuses for payment type; they will always
	 *        be of opposite values.
	 * @return paymentInfo A String object formatted for the boolean value that's
	 *         true.
	 * @see CheckoutController
	 */
	private String extractPayment(boolean isPaypal, boolean isCard) { // payment gets stored differently for paypal/card
		String paymentInfo;
		if (isPaypal) {
			paymentInfo = "Paypal Email: " + paypalEmailField.getText() + "\nPayPal Password: "
					+ paypalPasswordField.getText();
		} else {
			paymentInfo = "Card Number: " + cardNumberField.getText() + "\nExpires: "
					+ (monthBox.getSelectionModel().getSelectedIndex() + 1) + "/"
					+ yearBox.getSelectionModel().getSelectedItem() + "\nCVV: " + cardSecurityField.getText()
					+ "\nCard Holder: " + cardHolderField.getText();
		}

		return paymentInfo;
	}

	/**
	 * Performs action on payPal RadioButton click. Updates user's payment isPaypal
	 * to true, and isCard to false. Also enables the corresponding text fields on
	 * the CheckoutPage.
	 * 
	 * @see CheckoutController
	 */
	@FXML
	private void handlePaypalRadio() {// paypal button will only allow payapl info
		paypalVbox.setDisable(false);
		cardVbox.setDisable(true);
		isPaypal = true;
		isCard = false;
	}

	/**
	 * Performs action on card RadioButton click. Updates user's payment isPaypal to
	 * false, and isCard to true. Also enables the corresponding text fields on the
	 * CheckoutPage.
	 * 
	 * @see CheckoutController
	 */
	@FXML
	private void handleCardRadio() { // card button will only allow card info
		paypalVbox.setDisable(true);
		cardVbox.setDisable(false);
		isPaypal = false;
		isCard = true;
	}

	/**
	 * Checks all available CheckoutPage fields and checks if improper user input
	 * exists. Only returns true if user information is correct.
	 * 
	 * @return isProperInformation boolean value returning true if fields are
	 *         acceptable, false if user input exception is caught
	 * @see CheckoutController
	 */
	public boolean fieldCheck() {// checks inefficient field data
		if (app.getCurrentUser().getPayment() != null) {
			return true;
		}

		if (!(paypalEmailField.getText().isEmpty()) && !(paypalPasswordField.getText().isEmpty())) {
			if (paypalEmailField.getText().matches("([\\w]+@[\\w]+[.][\\w]+)+")) { // checks if email is invalid
				return true;
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

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
				alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

				alert.setTitle("Warning");
				alert.setHeaderText("Please enter a valid input for field(s)");
				alert.setContentText("");

				alert.showAndWait();

				return false;
			}
		}

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

		alert.setTitle("Warning");
		alert.setHeaderText("Certain Field(s) incomplete!");
		alert.setContentText("");

		alert.showAndWait();

		return false;
	}

	public void setApp(App app) { // gives controller access to databases
		this.app = app;
		double bill = calculateItemTotal();
		totalLabel.setText("Total: $" + shopping.utils.DataFormatter.formatAmount(bill / 1.04));
		afterTaxLabel.setText("After Tax: $" + shopping.utils.DataFormatter.formatAmount(bill));
	}
	
	/**
	 * Checks all available CheckoutPage fields and checks if improper user input
	 * exists, except for updating user payment. Only returns true if user information is correct.
	 * 
	 * @return isProperInformation boolean value returning true if fields are
	 *         acceptable, false if user input exception is caught
	 * @see CheckoutController
	 */
	public boolean fieldCheckNewPayment() {// checks inefficient field data

		if (!(paypalEmailField.getText().isEmpty()) && !(paypalPasswordField.getText().isEmpty())) {
			if (paypalEmailField.getText().matches("([\\w]+@[\\w]+[.][\\w]+)+")) { // checks if email is invalid
				return true;
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

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
				alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

				alert.setTitle("Warning");
				alert.setHeaderText("Please enter a valid input for field(s)");
				alert.setContentText("");

				alert.showAndWait();

				return false;
			}
		}

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.dialogPaneProperty().get().getStylesheets().add("/shopping/view/dialog.css");

		alert.setTitle("Warning");
		alert.setHeaderText("Certain Field(s) incomplete!");
		alert.setContentText("");

		alert.showAndWait();

		return false;
	}

}
