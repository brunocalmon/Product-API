package de.rakuten.cloud.service.productserver.datatransferobjects;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryRequest {

	@NotBlank
	private String categoryId;
	@NotBlank
	private String name;

}
