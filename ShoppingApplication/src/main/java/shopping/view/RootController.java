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

	public void setApp(App app) { // gives controller access to databases
		this.app = app;
		this.rootLayout = app.getRootLayout();
		
		rootLayout.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override //updates navigation once user logs in
            public void handle(ActionEvent event) {
            	if(app.getCurrentUser() != null) { //only allows checkout if they have items
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

	@FXML
	private void handleAccount() {// pulls account info page
		

	}
	
	@FXML
	private void handleCatalog() {// pulls main catalog page
			app.showCatalogPage();
	}
	
	@FXML
	private void handleCheckout() {// pulls checkout page
		app.showCheckoutPage();

	}
	
	@FXML
	private void handleAdminLog() {// pulls user log

	}
	
	@FXML
	private void handleSignOut() {// signs out current user and pulls login page
		app.setCurrentUser(null);
		app.showLoginPage();

	}
}
