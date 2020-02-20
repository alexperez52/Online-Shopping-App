package shopping.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import shopping.app.App;
import shopping.model.Invoice;

public class InvoiceDialogController {
	Invoice currentInvoice;
	private App app;
	private Stage dialogStage;
	@FXML
	private Label nameLabel;
	@FXML
	private Label addressLabel;
	@FXML
	private Label invoiceId;
	@FXML
	private Label invoiceDateCreatedLabel;
	@FXML
	private Label subtotalLabel;
	@FXML
	private Label taxLabel;
	@FXML
	private Label totalLabel;
	@FXML
	private Label purchaseSummaryLabel;

	public InvoiceDialogController() {

	}

	@FXML
	private void initialize() {

	}

	public void setApp(App app) { // gives controller access to databases
		this.app = app;

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public Invoice getCurrentInvoice() {
		return currentInvoice;
	}

	/**
	 * Takes a Invoice object as the argument. Sets all the of the Invoice object's
	 * fields onto the InvocieDialog for the user to read.
	 * 
	 * @param currentInvoice The Invoice object that the user wants to read.
	 * @see InvoiceDialogController
	 */
	public void setCurrentInvoice(Invoice currentInvoice) { // gets user's purchase information and displays onto
															// invoice
		this.currentInvoice = currentInvoice;

		nameLabel.setText(currentInvoice.getUser().getName().getFullName());
		addressLabel.setText(currentInvoice.getUser().getAddress().getFullAddress());
		invoiceId.setText("Invoice ID: x0000" + currentInvoice.getId().toString());
		invoiceDateCreatedLabel.setText("Date of purchase: " + currentInvoice.getDateStamp());
		subtotalLabel
				.setText("Subtotal: $" + shopping.utils.DataFormatter.formatAmount(currentInvoice.getBill() / 1.04));
		taxLabel.setText("Tax: $" + shopping.utils.DataFormatter.formatAmount(currentInvoice.getBill() * .04));
		totalLabel.setText("Total: $" + shopping.utils.DataFormatter.formatAmount(currentInvoice.getBill()));
		purchaseSummaryLabel
				.setText(currentInvoice.getInformation()); // generate
																												// purchase
																												// summary
																												// based
																												// on
																												// purchases
																												// and
																												// inventory
																												// item
																												// info
	}

}
