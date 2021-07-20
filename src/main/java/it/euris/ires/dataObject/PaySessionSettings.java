package it.euris.ires.dataObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaySessionSettings {

	private int sessionTimeToLive;

	private Boolean collectShippingAddress;

	private Boolean collectBillingAddress;

	private String currency;

	private String language;

}
