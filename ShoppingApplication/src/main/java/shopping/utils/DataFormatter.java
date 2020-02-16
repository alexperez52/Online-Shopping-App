package shopping.utils;

import java.text.DecimalFormat;

public class DataFormatter {
	private static DecimalFormat df = new DecimalFormat("#.00"); 
	
	public static String formatAmount(double amount) {
		return df.format(amount);
	}
}
