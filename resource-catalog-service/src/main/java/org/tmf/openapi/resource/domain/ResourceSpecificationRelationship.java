package org.tmf.openapi.resource.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.tmf.openapi.resource.domain.common.BaseRef;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceSpecificationRelationship extends BaseRef{
	@NotEmpty
	@JsonProperty("@atype")
	private String atype;
	
	private String role;

	@Valid
	private TimePeriod validFor;

}
