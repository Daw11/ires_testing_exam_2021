package it.euris.ires.dataObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleItem {

	private String name;

	private String description;

	private String productId;

	private String variantId;

	private String price;

	private int version;

}
