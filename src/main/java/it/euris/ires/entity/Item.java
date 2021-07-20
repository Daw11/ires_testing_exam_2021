package it.euris.ires.entity;

import java.util.UUID;
import lombok.Data;

@Data
public class Item extends AbstractEntity {

	private UUID paySessionId;

	private String name;

	private String description;

	private String productId;

	private String variantId;

	private String price;

	public Item() {
		super();
	}

}
