package org.tmf.openapi.resource.domain;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.context.Lifecycle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tmf.openapi.resource.domain.common.CategoryRef;
import org.tmf.openapi.resource.domain.common.ResourceSpecificationRef;

import org.tmf.openapi.resource.validator.ValidResourceCandidate;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ValidResourceCandidate
@Document
@JsonFilter("resourceCandidateFilter")
@EqualsAndHashCode(of = "id")
@ToString(includeFieldNames = true)
@QueryEntity
public class ResourceCandidate {

	@Id
	private String id;

	@Transient
	private URI href;
	
	@NotEmpty
	private String name;

	private String description;

	private String version;

	private String lastUpdate;
	
	@JsonProperty("@type")
	private String type;

	@JsonProperty("@baseType")
	private String baseType;

	@JsonProperty("@schemaLocation")
	private String schemaLocation;

	@Valid
	private TimePeriod validFor;

	private String lifecycleStatus;

	@Valid
	private List<CategoryRef> category;	

	private ResourceSpecificationRef resourceSpecification;

	

}
