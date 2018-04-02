package org.tmf.openapi.resource.domain.common;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "id")
@ToString(includeFieldNames = true)
public abstract class BaseRef {

	@NotEmpty
	private String id;
    
	@NotEmpty
	private String href;

	@NotEmpty
	private String name;

	@JsonProperty("@type")
	private String type;

	private String description;

}