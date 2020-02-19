package shopping.utils;

import java.text.DecimalFormat;

import shopping.app.App;

public class DataFormatter {
	private static DecimalFormat df = new DecimalFormat("#.00");

	/**
	 * Uses DecimalFormat class to return a specific format for displaying price
	 * information. Returns String object with double value formatted to two decimal
	 * places
	 * 
	 * @param double A double value to format to 2 decimal places for displaying price
	 * @return doublString A string of formatted double value
	 * @see DataFormatter, DecimalFormat 
	 */
	public static String formatAmount(double amount) {
		return df.format(amount);
	}
}
