package org.tmf.openapi.resource.domain;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.tmf.openapi.resource.domain.common.BaseRef;



import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Feature extends BaseRef {	

	private String version;

	@NotNull
	@Valid
	private TimePeriod validFor;
	
	private boolean isBundle;
	
	private boolean isEnabled;

	/*@JsonProperty("@type")
	private String type;

	@JsonProperty("@baseType")
	private String baseType;

	@JsonProperty("@schemaLocation")
	private String schemaLocation;*/

}
