package it.euris.ires.dataObject;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatePaySessionResponse {

	private boolean success;

	private String status;

	private String paySessionId;

}
