package org.tmf.openapi.resource.domain;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResourceSpecCharRelationship {

	
	@JsonProperty("@type")
	private String type;
	@Valid
	private TimePeriod validFor;	
	private String id;
	private String name;
	private String href;
	

}
