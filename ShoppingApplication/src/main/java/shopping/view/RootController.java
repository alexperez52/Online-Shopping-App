package shopping.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import shopping.app.App;

public class RootController {
	private App app;
	private BorderPane rootLayout;
	@FXML
	private HBox navBar;
	@FXML
	private Button adminLogBtn;
	@FXML
	private Button checkoutBtn;

	public RootController() {
		
	}
	
	/**
	 * Sets RootController object's App object to the App object argument. Also handles showing the navBar depending on currentUser's isAdmin status.
	 * 
	 * @param app An App object to give a navBar to.
	 * @see RootController
	 */
	public void setApp(App app) { // gives controller access to databases
		this.app = app;
		this.rootLayout = app.getRootLayout();
		
		rootLayout.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override //updates navigation once user logs in
            public void handle(ActionEvent event) {
            	if(app.getCurrentUser() != null) { //only allows checkout if they have items
        			if(app.getCurrentUser().isAdmin()) { //only allows admin to view the LOGS page
        				adminLogBtn.setVisible(true);
        			}
        			else {
        				adminLogBtn.setVisible(false);
        			}
            		
            		navBar.setVisible(true);
        			if(!(app.getCurrentUser().getCart().getCartItems().isEmpty())){
        				checkoutBtn.setDisable(false);
        			}
        			else {
        				checkoutBtn.setDisable(true);

        			}
        		}
        		else {
        			navBar.setVisible(false);
        		}
            }
        });
	}

	
	
	/**
	 * Performs action on Account Button click. Calls app.showAccountPage().
	 * 
	 * @see RootController
	 */
	@FXML
	private void handleAccount() {// pulls account info page
		app.showAccountPage();

	}
	
	/**
	 * Performs action on Catalog Button click. Calls app.showCatalogPage().
	 * 
	 * @see RootController
	 */
	@FXML
	private void handleCatalog() {// pulls main catalog page
			app.showCatalogPage();
	}
	
	/**
	 * Performs action on Checkout Button click. Calls app.showShoppingCartPage().
	 * 
	 * @see RootController
	 */
	@FXML
	private void handleCheckout() {// pulls Shopping cart page
		app.showShoppingCartPage();

	}
	
	/**
	 * Performs action on Logs Button click. Calls app.showAdminPage().
	 * 
	 * @see RootController
	 */
	@FXML
	private void handleAdminLog() {// pulls user log

		app.showAdminPage();
	}
	
	/**
	 * Performs action on Sign Out Button click. Calls app.showLoginPage() and sets program's currentUser to null
	 * 
	 * @see RootController
	 */
	@FXML
	private void handleSignOut() {// signs out current user and pulls login page
		app.setCurrentUser(null);
		app.showLoginPage();

	}
}
