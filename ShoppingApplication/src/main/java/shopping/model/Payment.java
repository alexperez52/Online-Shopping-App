package shopping.model;

public class Payment {
	Boolean isPaypal;
	Boolean isCard;
	String information;

	public Payment(Boolean isPaypal, Boolean isCard, String information) {
		this.isPaypal = isPaypal; //payment will either be paypal or card at any given time
		this.isCard = isCard;
		this.information = information;
	}

	public Boolean getIsPaypal() {
		return isPaypal;
	}

	public void setIsPaypal(Boolean isPaypal) {
		this.isPaypal = isPaypal;
	}

	public Boolean getIsCard() {
		return isCard;
	}

	public void setIsCard(Boolean isCard) {
		this.isCard = isCard;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

}
