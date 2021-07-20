package it.euris.ires.entity;

import it.euris.ires.util.PaySessionStatus;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaySession extends AbstractEntity {

	private String accountId;

	private String paymentConfigId;

	private String consumerId;

	private int sessionTimeToLive;

	private Boolean collectShippingAddress;

	private Boolean collectBillingAddress;

	private String currency;

	private String language;

	private String taxAmount;

	private List<Item> items;

	private PaySessionStatus status;

	private String itemAmount;

	private String paySessionAmount;

	public PaySession() {
		super();
	}


}
