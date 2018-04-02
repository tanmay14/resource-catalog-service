package org.tmf.openapi.resource.domain;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.tmf.openapi.resource.domain.common.BaseRef;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Attachment extends BaseRef {

	@JsonProperty("type")
	private String attachmentType;

	private URI url;

	private String mimeType;

	@NotNull
	@Valid
	private TimePeriod validFor;

	/*@JsonProperty("@type")
	private String type;

	@JsonProperty("@baseType")
	private String baseType;

	@JsonProperty("@schemaLocation")
	private String schemaLocation;*/

}
