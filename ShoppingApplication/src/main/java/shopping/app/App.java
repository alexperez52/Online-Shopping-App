package shopping.app;

import java.io.IOException;

import org.aerofx.AeroFX;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import shopping.model.Inventory;
import shopping.model.Invoice;
import shopping.model.Item;
import shopping.model.Name;
import shopping.model.User;
import shopping.model.UserBag;
import shopping.view.AccountController;
import shopping.view.AdminController;
import shopping.view.CatalogController;
import shopping.view.CheckoutController;
import shopping.view.InvoiceDialogController;
import shopping.view.ItemDialogController;
import shopping.view.LoginController;
import shopping.view.RegisterController;
import shopping.view.RootController;
import shopping.view.ShoppingCartController;

public class App extends Application {

	/*
	 * This project was created with inspiration from current shopping apps (amazon,
	 * etc.) along with help from scene builder tutorials by author Marco Jakob
	 * (https://code.makery.ch/library/javafx-tutorial/)
	 */

	private Stage primaryStage = new Stage();
	private BorderPane rootLayout = new BorderPane();
	private Inventory liveInventory = new Inventory();
	private UserBag liveUserBag = new UserBag();
	private User currentUser;
	private User temporaryUser;

	@Override
	public void start(Stage primaryStage) {
		
		shopping.utils.DataSaver.restore(liveUserBag, liveInventory); // restores data on start up
		// regenerateData();
		liveUserBag.getUsers().entrySet().forEach(entry -> {
			System.out.println(entry.getValue());
		});

		System.out.println(liveUserBag.getUsers().get("Alayah83").getPassword());

		initRootLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Starts the GUI. Sets root node to the root fxml and boots up the scene,
	 * starting with the log in page. Changes Main stage's close request to include
	 * backing up all data upon exit. Creates RootController object for root node.
	 * 
	 * @see App
	 */
	public void initRootLayout() {
		try {
			FXMLLoader loaderRoot = new FXMLLoader();
			loaderRoot.setLocation(App.class.getClass().getResource("/shopping/view/RootLayout.fxml")); // loads root //
																										// pane
			rootLayout = (BorderPane) loaderRoot.load();

			RootController rootController = loaderRoot.getController();
			rootController.setApp(this);

			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add("/shopping/app/app.css");
			showLoginPage();

			primaryStage.setScene(scene);
			primaryStage.show();

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() { // saves bags on exit
				public void handle(WindowEvent we) {
					shopping.utils.DataSaver.backup(liveUserBag, liveInventory);
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets Login fxml. Sets root node's center to the login fxml and creates LoginController object for
	 * logIn page.
	 * 
	 * @see App
	 */
	public void showLoginPage() {// pulls main page
		try {
			FXMLLoader loaderLogin = new FXMLLoader();
			loaderLogin.setLocation(App.class.getClass().getResource("/shopping/view/LoginPage.fxml")); // loads login
			Pane loginPage = (Pane) loaderLogin.load();
			rootLayout.setCenter(loginPage);

			LoginController loginController = loaderLogin.getController();
			loginController.setApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets User Edit fxml. Sets a dialog window which takes input to modify
	 * existing user.
	 * 
	 * @see App
	 */
	public void showInfoEdit() {
		Stage dialogStage;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/shopping/view/UserDetailEditDialog.fxml"));
			Pane page = (Pane) loader.load();

			dialogStage = new Stage();
			dialogStage.setTitle("User Edit");
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AccountController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			controller.setApp(this);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets catalog fxml. Sets root node's center to the catalog fxml and creates CatalogController object for
	 * catalog page. 
	 * 
	 * @see App
	 */
	public void showCatalogPage() {// pulls main page
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/shopping/view/CatalogPage.fxml"));
			Pane page = (Pane) loader.load();
			rootLayout.setCenter(page);

			CatalogController catalogController = loader.getController();
			catalogController.setApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets Shopping Cart fxml. Sets root node's center to Shopping Cart fxml and creates ShoppingCartController object
	 * for shopping cart page.
	 * 
	 * @see App
	 */
	public void showShoppingCartPage() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/shopping/view/ShoppingCart.fxml"));
			Pane page = (Pane) loader.load();
			rootLayout.setCenter(page);

			ShoppingCartController shoppingCartController = loader.getController();
			shoppingCartController.setApp(this);
			shoppingCartController.showItems();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets register fxml. Sets root node's center to the register fxml and creates RegisterController object for
	 * register page.
	 * 
	 * @see App
	 */
	public void showRegisterPage() {// pulls register page
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/shopping/view/RegisterPage.fxml"));
			Pane page = (Pane) loader.load();
			rootLayout.setCenter(page);

			RegisterController registerController = loader.getController();
			registerController.setApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets checkout fxml. Sets root node's center to the checkout fxml and creates CheckoutController object for
	 * checkout page.
	 * 
	 * @see App
	 */
	public void showCheckoutPage() {// pulls checkout page
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/shopping/view/CheckoutPage.fxml"));
			Pane page = (Pane) loader.load();
			rootLayout.setCenter(page);

			CheckoutController checkoutController = loader.getController();
			checkoutController.setApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets invoice fxml. Displays a dialog with the invoice fxml and creates InvoiceDialogController object for
	 * invoice dialog.
	 * 
	 * @param invoice AN Invoice object to be displayed on the dialog
	 * @see App
	 */
	public void showInvoiceDialog(Invoice invoice) {// pulls invoice dialog
		Stage dialogStage;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/shopping/view/Invoice.fxml"));
			Pane page = (Pane) loader.load();

			dialogStage = new Stage();
			dialogStage.setTitle("Invoice");
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			InvoiceDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setApp(this);
			controller.setCurrentInvoice(invoice);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Gets item fxml. Displays a dialog with the item fxml and creates ItemDialogController object for
	 * item dialog.
	 * 
	 * @param item An item to be edited, or null for creating a new item
	 * @see App
	 */
	public void showItemDialog(Item item) {// pulls invoice dialog
		Stage dialogStage;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/shopping/view/ItemDialog.fxml"));
			Pane page = (Pane) loader.load();

			dialogStage = new Stage();
			dialogStage.setTitle("Item");
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ItemDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setApp(this);
			controller.setCurrentItem(item);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Gets User Profile fxml. Sets root node's center to the User Profile fxml and creates AccountController object for
	 * User Profile page.
	 * 
	 * @see App
	 */
	public void showAccountPage() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/shopping/view/UserProfile.fxml"));
			Pane page = (Pane) loader.load();
			rootLayout.setCenter(page);

			AccountController accountController = loader.getController();

			accountController.setApp(this);
			accountController.showInfo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets UserProfile fxml. Sets root node's center to the user profile fxml and creates AccountController object for
	 * User Profile page.
	 * @param user A user to be passed for viewing user profile from admin account
	 * @see App
	 */
	public void showAccountPageAdmin(User user) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/shopping/view/UserProfile.fxml"));
			Pane page = (Pane) loader.load();
			rootLayout.setCenter(page);

			AccountController accountController = loader.getController();

			accountController.setApp(this);
			accountController.setUser(user);
			accountController.showInfo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Gets EditPayment fxml. Sets a dialog to input payment information which is updated
	 * to the user account currently logged in. Creates an AccountController for the EditPayment
	 * page 
	 * 
	 * @see App
	 */
	public void showCardEdit() {
		Stage dialogStage;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/shopping/view/EditPaymentPage.fxml"));
			Pane page = (Pane) loader.load();

			dialogStage = new Stage();
			dialogStage.setTitle("Payment");
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AccountController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.initComboBoxes();
			controller.getCardVBox().setDisable(false);
			controller.setApp(this);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Gets EditPayment fxml. Sets a dialog to input payment informtion which is updated
	 * to the user account currently logged in. Creates an AccountController for the EditPayment
	 * page
	 * 
	 * @see App
	 */
	public void showPaypalEdit() {
		Stage dialogStage;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/shopping/view/EditPaymentPage.fxml"));
			Pane page = (Pane) loader.load();

			dialogStage = new Stage();
			dialogStage.setTitle("Payment");
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AccountController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.getPaypalPane().setDisable(false);
			controller.setApp(this);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Gets AdminControlPanel fxml. Sets root node's center to the Admin Control Panel fxml and creates AdminController object for
	 * AdminControlPanel page.
	 * 
	 * @see App
	 */
	public void showAdminPage() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/shopping/view/AdminControlPanel.fxml"));
			Pane page = (Pane) loader.load();
			rootLayout.setCenter(page);

			AdminController adminController = loader.getController();
			adminController.setApp(this);
			adminController.updateTable();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public BorderPane getRootLayout() {
		return rootLayout;
	}

	public void setRootLayout(BorderPane rootLayout) {
		this.rootLayout = rootLayout;
	}

	public Inventory getLiveInventory() {
		return liveInventory;
	}

	public void setLiveInventory(Inventory liveInventory) {
		this.liveInventory = liveInventory;
	}

	public UserBag getLiveUserBag() {
		return liveUserBag;
	}

	public void setLiveUserBag(UserBag liveUserBag) {
		this.liveUserBag = liveUserBag;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User user) {
		this.currentUser = user;
	}
	
	/**
	 * Calls both data factories with this.App's inventory and userBag to populate both with respective data.
	 * 
	 * @see App
	 */
	private void regenerateData() {
		liveInventory = shopping.utils.ItemFactory.importItemData(liveInventory);
		liveUserBag = shopping.utils.UserFactory.importUserData(liveUserBag);
	}

	public User getTemporaryUser() {
		return temporaryUser;
	}

	public void setTemporaryUser(User temporaryUser) {
		this.temporaryUser = temporaryUser;
	}

}
