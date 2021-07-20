package it.euris.ires.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import org.javamoney.moneta.Money;

public class Amount {

	private CurrencyUnit currencyUnit;

	private Money amount;

	public void initAmount(String currencyUnit) {
		this.currencyUnit = Monetary.getCurrency(currencyUnit);
		this.amount = Money.zero(this.currencyUnit);
	}

	public String sum(List<String> prices) {
		this.amount = prices.stream().mapToDouble(Double::parseDouble)
				.mapToObj(price -> Money.of(price, currencyUnit)).reduce(amount, Money::add);
		NumberFormat numberFormatter = new DecimalFormat("#0.00");
		return numberFormatter.format(this.amount.getNumber());
	}

}
