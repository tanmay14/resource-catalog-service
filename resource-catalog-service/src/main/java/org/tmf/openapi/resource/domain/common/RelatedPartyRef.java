package org.tmf.openapi.resource.domain.common;

import javax.validation.Valid;

import org.tmf.openapi.resource.domain.TimePeriod;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RelatedPartyRef extends BaseRef {

	private String role;

	@Valid
	private TimePeriod validFor;

}
