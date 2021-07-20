package it.euris.ires.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public abstract class AbstractEntity {

	@Getter
	UUID uuid;

	@Getter
	LocalDateTime createdDate;

	@Setter
	@Getter
	LocalDateTime modifiedDate;

	@Getter
	String version = "1.0.0";

	public AbstractEntity() {
		uuid = UUID.randomUUID();
		createdDate = LocalDateTime.now();
	}

}
