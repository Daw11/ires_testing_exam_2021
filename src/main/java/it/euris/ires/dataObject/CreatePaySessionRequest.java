package it.euris.ires.dataObject;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class CreatePaySessionRequest implements Serializable {

	private BusinessData businessData;

	private PaySessionSettings paySessionSettings;

	private List<SaleItem> saleItems;

	private String taxAmount;

}
