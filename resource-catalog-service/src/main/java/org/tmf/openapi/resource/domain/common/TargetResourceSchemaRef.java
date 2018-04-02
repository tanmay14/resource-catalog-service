package org.tmf.openapi.resource.domain.common;

import org.tmf.openapi.resource.domain.Attachment;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class TargetResourceSchemaRef {

	@JsonProperty("@type")
	private String type;

	@JsonProperty("@schemaLocation")
	private String schemaLocation;

}
