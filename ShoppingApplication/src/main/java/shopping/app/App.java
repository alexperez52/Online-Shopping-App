package shopping.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import shopping.model.Inventory;
import shopping.model.Invoice;
import shopping.model.User;
import shopping.model.UserBag;
import shopping.view.CatalogController;
import shopping.view.CheckoutController;
import shopping.view.InvoiceDialogController;
import shopping.view.LoginController;
import shopping.view.RegisterController;
import shopping.view.RootController;

public class App extends Application {
	
	/*
	 * This project was created with inspiration from current shopping apps (amazon, etc.) along with help from scene builder tutorials
	 * by author Marco Jakob (https://code.makery.ch/library/javafx-tutorial/)
	 */
	
	private Stage primaryStage = new Stage();
	private BorderPane rootLayout = new BorderPane();
	private Inventory liveInventory = new Inventory();
	private UserBag liveUserBag = new UserBag();
	private User currentUser;

	@Override
	public void start(Stage primaryStage) {
		shopping.utils.DataSaver.restore(liveUserBag, liveInventory); // restores data on start up
		//regenerateData();
		liveUserBag.getUsers().entrySet().forEach(entry -> {
			System.out.println(entry.getValue());
		});
		
		initRootLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void initRootLayout() {
		try {
			FXMLLoader loaderRoot = new FXMLLoader();
			loaderRoot.setLocation(App.class.getClass().getResource("/shopping/view/RootLayout.fxml")); // loads root //
																										// pane
			rootLayout = (BorderPane) loaderRoot.load();

			RootController rootController = loaderRoot.getController();
			rootController.setApp(this);

			Scene scene = new Scene(rootLayout);
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
	
	private void regenerateData() {
		liveInventory = shopping.utils.ItemFactory.importItemData(liveInventory);
		liveUserBag = shopping.utils.UserFactory.importUserData(liveUserBag);
	}

}
